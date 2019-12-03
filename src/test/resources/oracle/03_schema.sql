CREATE TABLE JPA_LOCAL_DATE_TIME (
  ID NUMBER(8) NOT NULL,
  LOCAL_DATE_TIME TIMESTAMP(9),
  PRIMARY KEY (ID)
);

CREATE TABLE JPA_OFFSET_DATE_TIME (
  ID NUMBER(8) NOT NULL,
  OFFSET_DATE_TIME TIMESTAMP(9) WITH TIME ZONE,
  PRIMARY KEY (ID)
);

