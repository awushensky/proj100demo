SET SCHEMA demo;

-- Set up the available user types
INSERT INTO types (type) VALUES ('CUSTOMER');
INSERT INTO types (type) VALUES ('VEHICLE');
INSERT INTO types (type) VALUES ('SUPPORT_TECHNICIANS');

COMMIT;
