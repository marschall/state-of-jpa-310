DROP TABLE IF EXISTS JPA_SUPPORT;
CREATE TABLE JPA_SUPPORT (
  ID INTEGER NOT NULL,
  LOCAL_DATE_TIME TIMESTAMP(9) WITHOUT TIME ZONE,
  OFFSET_DATE_TIME TIMESTAMP(9) WITH TIME ZONE,
  LOCAL_TIME TIME(9),
  PRIMARY KEY (ID)
);
