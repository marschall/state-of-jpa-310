State of JPA JSR-310
====================

This project contains of tests for the state of JSR-310 support in JPA 2.2 providers.

Currently the tests focus on three areas:

 * avoid DST transition issues for LocalDateTime
 * support for keeping the offset in OffsetDateTime
 * nanosecond resolution for LocalTime

The 2nd level cache has been disabled to make sure entity are actually read from the database.

Accommodations have been made for the following database limitations:

 * PostgreS, MySQL, and MariaDB only supports microsecond resolution.
 * MySQL, MariaDB and Derby do not support TIMESTAMP WITH TIME ZONE.
 * SQL Server only supports 100 nanosecond resolution.


 microsoft.sql.DateTimeOffset

org.h2.api.TimestampWithTimeZone

https://bugs.eclipse.org/bugs/show_bug.cgi?id=533912