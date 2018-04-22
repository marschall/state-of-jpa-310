package com.github.marschall.stateofjpa310;

import static com.github.marschall.stateofjpa310.Constants.PERSISTENCE_UNIT_NAME;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.sql.Connection;
import java.sql.SQLException;
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
import javax.sql.DataSource;

import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
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

    this.runDatabasePopulators();
  }

  private void runDatabasePopulators() {
    this.txTemplate.execute(status -> {
      Map<String, DatabasePopulator> beans = this.applicationContext.getBeansOfType(DatabasePopulator.class);
      DataSource dataSource = this.applicationContext.getBean(DataSource.class);
      try (Connection connection = dataSource.getConnection()) {
        for (DatabasePopulator populator : beans.values()) {
          populator.populate(connection);
        }
      } catch (SQLException e) {
        throw new RuntimeException("could initialize database", e);
      }
      return null;
    });
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

    JpaLocalDateTime originalEntity = this.newJpaLocalDateTime();
    this.persistEntity(originalEntity);
    JpaLocalDateTime readBack = this.readEntity(JpaLocalDateTime.class, originalEntity.getId());

    assertNotSame(originalEntity, readBack);

    assertNotSame(originalEntity.getLocalDateTime(), readBack.getLocalDateTime(), "value is from cache");
    assertEquals(originalEntity.getLocalDateTime(), readBack.getLocalDateTime(), "LocalDateTime");
  }

  @Test
  void stateOfJpaJsr310SupportOffsetDateTime() {
    assumeTrue(this.offsetDateTimeSupported());

    JpaOffsetDateTime originalEntity = this.newJpaOffsetDateTime();
    this.persistEntity(originalEntity);
    JpaOffsetDateTime readBack = this.readEntity(JpaOffsetDateTime.class, originalEntity.getId());

    assertNotSame(originalEntity, readBack);

    assertNotSame(originalEntity.getOffsetDateTime(), readBack.getOffsetDateTime(), "value is from cache");
    assertEquals(originalEntity.getOffsetDateTime(), readBack.getOffsetDateTime(), "OffsetDateTime");
  }

  protected boolean offsetDateTimeSupported() {
    return true;
  }

  @Test
  void stateOfJpaJsr310SupportLocalTime() {

    JpaLocalTime originalEntity = this.newJpaLocalTime();
    this.persistEntity(originalEntity);
    JpaLocalTime readBack = this.readEntity(JpaLocalTime.class, originalEntity.getId());

    assertNotSame(originalEntity, readBack);

    assertNotSame(originalEntity.getLocalTime(), readBack.getLocalTime(), "value is from cache");
    assertEquals(originalEntity.getLocalTime(), readBack.getLocalTime(), "LocalTime");
  }

  private void persistEntity(Object entity) {
    this.txTemplate.execute((status) -> {
      EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(this.factory);
      entityManager.persist(entity);
      status.flush();
      return null;
    });
  }

  private <T> T readEntity(Class<T> entityClass, Object primaryKey) {
    return this.txTemplate.execute((status) ->  {
      EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(this.factory);
      T entity = entityManager.find(entityClass, primaryKey);
      assertNotNull(entity);
      return entity;
    });
  }

  @Test
  void testUnstorableValue() {
    LocalDateTime originalValue = this.getUnstorableValue();
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

  private JpaOffsetDateTime newJpaOffsetDateTime() {
    JpaOffsetDateTime entity = new JpaOffsetDateTime();
    entity.setId(1);
    entity.setOffsetDateTime(this.getCurrentDateTimeInDifferentZone());
    return entity;
  }

  private JpaLocalDateTime newJpaLocalDateTime() {
    JpaLocalDateTime entity = new JpaLocalDateTime();
    entity.setId(1);
    entity.setLocalDateTime(this.getUnstorableValue());
    return entity;
  }

  private JpaLocalTime newJpaLocalTime() {
    JpaLocalTime entity = new JpaLocalTime();
    entity.setId(1);
    entity.setLocalTime(this.getCurrentTime());
    return entity;
  }

  private LocalTime getCurrentTime() {
    LocalTime now = LocalTime.now();
    // make sure none of the nano seconds are 0 so that we can detect truncation
    now = now.withNano(this.getDefaultNanoSecond());
    return now.truncatedTo(this.getTimeResolution());
  }

  protected int getDefaultNanoSecond() {
    return 123_456_789;
  }

  protected OffsetDateTime getCurrentDateTimeInDifferentZone() {
    OffsetDateTime now = OffsetDateTime.now();
    // make sure none of the nano seconds are 0 so that we can detect truncation
    now = now.withNano(this.getDefaultNanoSecond());
    return this.withDifferentOffsetSameInstant(now).truncatedTo(this.getTimeResolution());
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
    return OffsetDateTime.now(ZoneOffset.UTC).withNano(this.getDefaultNanoSecond()).truncatedTo(this.getTimeResolution());
  }

  protected TemporalUnit getTimeResolution() {
    return ChronoUnit.NANOS;
  }

  private LocalDateTime getUnstorableValue() {
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
    LocalDateTime unstorable = transition.getDateTimeBefore().plus(gap.dividedBy(2L));
    // make sure none of the nano seconds are 0 so that we can detect truncation
    return unstorable.withNano(this.getDefaultNanoSecond()).truncatedTo(this.getTimeResolution());
  }

}
