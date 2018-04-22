package com.github.marschall.stateofjpa310;

import static com.github.marschall.stateofjpa310.Constants.PERSISTENCE_UNIT_NAME;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import com.github.marschall.stateofjpa310.configuration.TransactionManagerConfiguration;

abstract class AbstractStateOfJpa310Test {

  private AnnotationConfigApplicationContext applicationContext;

  @BeforeEach
  void setUp() {
    this.applicationContext = new AnnotationConfigApplicationContext();
    this.applicationContext.register(this.getDataSourceConfiguration(), TransactionManagerConfiguration.class, this.getJapConfiguration());
    ConfigurableEnvironment environment = this.applicationContext.getEnvironment();
    MutablePropertySources propertySources = environment.getPropertySources();
    Map<String, Object> source = singletonMap(PERSISTENCE_UNIT_NAME, this.getPersistenceUnitName());
    propertySources.addFirst(new MapPropertySource("persistence unit name", source));
    this.applicationContext.refresh();

//    PlatformTransactionManager txManager = this.applicationContext.getBean(PlatformTransactionManager.class);
//    this.template = new TransactionTemplate(txManager);
//
//    this.template.execute(status -> {
//      Map<String, DatabasePopulator> beans = this.applicationContext.getBeansOfType(DatabasePopulator.class);
//      DataSource dataSource = this.applicationContext.getBean(DataSource.class);
//      try (Connection connection = dataSource.getConnection()) {
//        for (DatabasePopulator populator : beans.values()) {
//          populator.populate(connection);
//        }
//      } catch (SQLException e) {
//        throw new RuntimeException("could initialize database", e);
//      }
//      return null;
//    });
  }

  @AfterEach
  void tearDown() {
    this.applicationContext.close();
  }

  protected abstract Class<?> getDataSourceConfiguration();

  protected abstract Class<?> getJapConfiguration();


  protected abstract String getPersistenceUnitName();

  @Test
  @Disabled
  void stateOfJpaJsr310Support() {

    JpaSupport originalEntity = this.newEntity();
    this.storeEntity(originalEntity);
    JpaSupport readBack = this.readEntity(originalEntity.getId());

    assertEquals(originalEntity.getLocalDateTime(), readBack.getLocalDateTime(), "LocalDateTime");
    assertEquals(originalEntity.getOffsetDateTime(), readBack.getOffsetDateTime(), "LocalDateTime");
    assertEquals(originalEntity.getLocalTime(), readBack.getLocalTime(), "LocalTime");
  }

  private void storeEntity(JpaSupport originalEntity) {
    // TODO Auto-generated method stub

  }

  private JpaSupport readEntity(Integer id) {
    // TODO Auto-generated method stub
    return null;
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
    return LocalTime.now().truncatedTo(this.getTimeResolution());
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