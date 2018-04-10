CREATE TABLE idt_service_data
(
  id                      VARCHAR(10)                         NOT NULL PRIMARY KEY,
  call_time               VARCHAR(20)                         NOT NULL,
  location_ccg            VARCHAR(40)                         NOT NULL,
  sg_description          VARCHAR(40)                         NOT NULL,
  disposition_code        VARCHAR(10)                         NOT NULL,
  disposition_text        VARCHAR(100)                        NULL,
  disposition_group       VARCHAR(30)                         NOT NULL,
  disposition_broad_group VARCHAR(30)                         NOT NULL,
  organisation_name       VARCHAR(30)                         NOT NULL,
  dos_service_id          VARCHAR(20)                         NOT NULL,
  timestamp               TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
)
  COMMENT 'Stores Service data from IDT';