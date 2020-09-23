INSERT INTO AUTHORITY VALUES ('1', 'ROLE_PATIENT');
INSERT INTO AUTHORITY VALUES ('2', 'ROLE_DENTIST');

INSERT INTO USER_TABLE (username, password, first_name, last_name ) VALUES ('zubar', '$2a$10$E.6cGDFQPa64wU8dQPcsSuzq37GwRpg3uY2K/mwti9vNL4d4qNdbG', 'Marko', 'Zubar');
INSERT INTO USER_AUTHORITY VALUES ('1', '2');

INSERT INTO USER_TABLE (username, password, first_name, last_name ) VALUES ('pacijent', '$2a$10$E.6cGDFQPa64wU8dQPcsSuzq37GwRpg3uY2K/mwti9vNL4d4qNdbG', 'Pera', 'Pacijent');
INSERT INTO USER_AUTHORITY VALUES ('2', '1');

INSERT INTO APPOINTMENT (start_date, duration, user_id) VALUES ('2020-09-24 10:00:00', '30', '2');
INSERT INTO APPOINTMENT (start_date, duration, user_id) VALUES ('2020-09-24 12:30:00', '60', '2');