State of JSR-310 Support in JPA
===============================

This project contains of tests for the state of JSR-310 support in JPA 2.2 providers.

The following databases are tested:

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
 * OpenJPA

Currently the following combinations pass:

 * H2 on OpenJPA
 * SQL Server on OpenJPA if you add `sendTimeAsDatetime=false` to the driver URL to disable data trunction, see [#1007](https://github.com/microsoft/mssql-jdbc/pull/1007)

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


