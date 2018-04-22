package com.github.marschall.stateofjpa310;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;

import org.junit.jupiter.api.Test;

abstract class AbstractStateOfJpa310Test {

  protected abstract String getPersistenceUnitName();

  @Test
  void testUnstorableValue() {
    LocalDateTime originalValue = getUnstorableValue();
    java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(originalValue);
    LocalDateTime convertedValue = timestamp.toLocalDateTime();
    assertNotEquals(originalValue, convertedValue);
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
