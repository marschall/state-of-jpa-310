package com.github.marschall.stateofjpa310;

import static com.github.marschall.stateofjpa310.Constants.PERSISTENCE_UNIT_NAME;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.marschall.stateofjpa310.configuration.TransactionManagerConfiguration;

abstract class AbstractStateOfJpa310Test {

  private AnnotationConfigApplicationContext applicationContext;
  private EntityManagerFactory factory;
  private TransactionOperations txTemplate;

  @BeforeEach
  void setUp() {
    this.applicationContext = new AnnotationConfigApplicationContext();
    this.applicationContext.register(this.getDataSourceConfiguration(), TransactionManagerConfiguration.class, this.getJapConfiguration());
    ConfigurableEnvironment environment = this.applicationContext.getEnvironment();
    MutablePropertySources propertySources = environment.getPropertySources();
    Map<String, Object> source = singletonMap(PERSISTENCE_UNIT_NAME, this.getPersistenceUnitName());
    propertySources.addFirst(new MapPropertySource("persistence unit name", source));
    this.applicationContext.refresh();


    this.factory = this.applicationContext.getBean(EntityManagerFactory.class);
    PlatformTransactionManager txManager = this.applicationContext.getBean(PlatformTransactionManager.class);
    TransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    this.txTemplate = new TransactionTemplate(txManager, transactionDefinition);

  }

  @AfterEach
  void tearDown() {
    this.applicationContext.close();
  }

  protected abstract Class<?> getDataSourceConfiguration();

  protected abstract Class<?> getJapConfiguration();


  protected abstract String getPersistenceUnitName();

  @Test
  void stateOfJpaJsr310SupportLocalDateTime() {

    JpaSupport originalEntity = this.newEntity();
    this.persistEntity(originalEntity);
    JpaSupport readBack = this.readEntity(originalEntity.getId());

    assertNotSame(originalEntity, readBack);

    assertNotSame(originalEntity.getLocalDateTime(), readBack.getLocalDateTime());
    assertEquals(originalEntity.getLocalDateTime(), readBack.getLocalDateTime(), "LocalDateTime");
  }

  @Test
  void stateOfJpaJsr310SupportOffsetDateTime() {

    JpaSupport originalEntity = this.newEntity();
    this.persistEntity(originalEntity);
    JpaSupport readBack = this.readEntity(originalEntity.getId());

    assertNotSame(originalEntity, readBack);

    assertNotSame(originalEntity.getOffsetDateTime(), readBack.getOffsetDateTime());
    assertEquals(originalEntity.getOffsetDateTime(), readBack.getOffsetDateTime(), "OffsetDateTime");
  }

  @Test
  void stateOfJpaJsr310SupportLocalTime() {

    JpaSupport originalEntity = this.newEntity();
    this.persistEntity(originalEntity);
    JpaSupport readBack = this.readEntity(originalEntity.getId());

    assertNotSame(originalEntity, readBack);

    assertNotSame(originalEntity.getLocalTime(), readBack.getLocalTime());
    assertEquals(originalEntity.getLocalTime(), readBack.getLocalTime(), "LocalTime");
  }

  private void persistEntity(JpaSupport entity) {
    this.txTemplate.execute((status) -> {
      EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(this.factory);
      entityManager.persist(entity);
      status.flush();
      return null;
    });
  }

  private JpaSupport readEntity(Integer id) {
    return this.txTemplate.execute((status) ->  {
      EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(this.factory);
      JpaSupport entity = entityManager.find(JpaSupport.class, id);
      assertNotNull(entity);
      return entity;
    });
  }

  @Test
  void testUnstorableValue() {
    LocalDateTime originalValue = getUnstorableValue();
    java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(originalValue);
    LocalDateTime convertedValue = timestamp.toLocalDateTime();
    assertNotEquals(originalValue, convertedValue);
  }

  @Test
  void testGetCurrentDateTimeInDifferentZone() {
    OffsetDateTime now = OffsetDateTime.now();
    OffsetDateTime differentOffsetSameInstant = this.withDifferentOffsetSameInstant(now);
    assertEquals(now.toInstant(), differentOffsetSameInstant.toInstant());
    assertNotEquals(now.getOffset(), differentOffsetSameInstant.getOffset());

    AssertionsForInterfaceTypes.assertThat(differentOffsetSameInstant.getOffset()).isGreaterThan(ZoneOffset.MAX);
    AssertionsForInterfaceTypes.assertThat(differentOffsetSameInstant.getOffset()).isLessThan(ZoneOffset.MIN);

    assertTrue(ZoneOffset.MIN.compareTo(ZoneOffset.MAX) > 0);
  }

  private JpaSupport newEntity() {
    JpaSupport entity = new JpaSupport();
    entity.setId(1);
    entity.setLocalDateTime(getUnstorableValue());
    entity.setOffsetDateTime(this.getCurrentDateTimeInDifferentZone());
    entity.setLocalTime(this.getCurrentTime());
    return entity;
  }

  private LocalTime getCurrentTime() {
    LocalTime now = LocalTime.now();
    // make sure none of the nano seconds are 0 so that we can detect truncation
    now = now.withNano(123_456_789);
    return now.truncatedTo(this.getTimeResolution());
  }

  protected OffsetDateTime getCurrentDateTimeInDifferentZone() {
    OffsetDateTime now = OffsetDateTime.now();
    return this.withDifferentOffsetSameInstant(now);
  }


  private OffsetDateTime withDifferentOffsetSameInstant(OffsetDateTime now) {
    ZoneOffset localOffset = now.getOffset();
    ZoneOffset newOffset;
    if (localOffset.compareTo(ZoneOffset.UTC) <= 0) {
      Duration.ofSeconds(localOffset.getTotalSeconds());
      newOffset = toOffset(toDuration(localOffset).minusMinutes(91));
    } else {
      newOffset = toOffset(toDuration(localOffset).plusMinutes(91));
    }
    return now.withOffsetSameInstant(newOffset);
  }

  private static Duration toDuration(ZoneOffset offset) {
    return Duration.ofSeconds(offset.getTotalSeconds());
  }

  private static ZoneOffset toOffset(Duration duration) {
    return ZoneOffset.ofTotalSeconds(Math.toIntExact(duration.getSeconds()));
  }

  protected OffsetDateTime getCurrentDateTimeInUtc() {
    return OffsetDateTime.now(ZoneOffset.UTC);
  }

  protected TemporalUnit getTimeResolution() {
    return ChronoUnit.NANOS;
  }


  private static LocalDateTime getUnstorableValue() {
    ZoneId systemTimezone = ZoneId.systemDefault();
    Instant now = Instant.now();

    ZoneRules rules = systemTimezone.getRules();
    ZoneOffsetTransition transition = rules.nextTransition(now);
    assertNotNull(transition, "test has to be run in a time zone with DST");
    if (!transition.getDateTimeBefore().isBefore(transition.getDateTimeAfter())) {
      transition = rules.nextTransition(transition.getInstant().plusSeconds(1L));
      assertNotNull(transition, "test has to be run in a time zone with DST");
    }

    Duration gap = Duration.between(transition.getDateTimeBefore(), transition.getDateTimeAfter());
    return transition.getDateTimeBefore().plus(gap.dividedBy(2L));
  }

}
