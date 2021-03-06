DROP TABLE IF EXISTS JPA_LOCAL_DATE_TIME;
CREATE TABLE JPA_LOCAL_DATE_TIME (
  ID INTEGER NOT NULL,
  LOCAL_DATE_TIME DATETIME(6),
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS JPA_LOCAL_TIME;
CREATE TABLE JPA_LOCAL_TIME (
  ID INTEGER NOT NULL,
  LOCAL_TIME TIME(6),
  PRIMARY KEY (ID)
);
