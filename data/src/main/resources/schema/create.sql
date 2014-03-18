DROP SCHEMA IF EXISTS demo;
CREATE SCHEMA demo;
SET SCHEMA demo;

-- This table keeps track of the data packets.
DROP TABLE IF EXISTS data_packets;
CREATE TABLE data_packets (
  id IDENTITY PRIMARY KEY AUTO_INCREMENT NOT NULL,
  data_packet VARCHAR2(255) NOT NULL,
  type BIGINT NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- This table keeps track of the consumers of these data packets. The combination of URL and TYPE must be unique.
DROP TABLE IF EXISTS listeners;
CREATE TABLE listeners (
  id IDENTITY PRIMARY KEY AUTO_INCREMENT NOT NULL,
  url VARCHAR2(255) NOT NULL,
  type BIGINT NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (url, type)
);

-- This table keeps track of the types of data packets.
DROP TABLE IF EXISTS types;
CREATE TABLE types (
  id IDENTITY PRIMARY KEY AUTO_INCREMENT NOT NULL,
  type VARCHAR2(255) NOT NULL
);

COMMIT;
