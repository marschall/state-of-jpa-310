State of JSR-310 Support in JPA
===============================

This project contains of tests for the state of JSR-310 support in JPA 2.2 providers.

The following databases are tested:

 * DB2
 * Derby
 * Firebird
 * H2
 * HSQLDB
 * MariaDB
 * MySQL
 * Oracle
 * PostgreS
 * SQL Server

The following JPA providers are tested:

 * EclipseLink
 * Hibernate

Currently none combinations pass.

Currently the tests focus on three areas:

 * avoid DST transition issues for `LocalDateTime`
 * support for keeping the offset in `OffsetDateTime`
 * nanosecond resolution for `LocalTime`

The 2nd level cache has been disabled to make sure entity are actually read from the database.

Accommodations have been made for the following database limitations:

 * Firebird only supports 100 microsecond resolution.
 * MySQL, MariaDB, Derby and Firebird do not support `TIMESTAMP WITH TIME ZONE`.
 * Oralce does not support `TIME`.
 * PostgreS, MySQL, and MariaDB only supports microsecond resolution.
 * PostgreS converts time zones to UTC when storing.
 * SQL Server only supports 100 nanosecond resolution.


