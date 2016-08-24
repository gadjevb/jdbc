CREATE TABLE USERS(ID SMALLINT PRIMARY KEY NOT NULL, Name VARCHAR(50) NOT NULL);
CREATE TABLE CONTACT(ID SMALLINT REFERENCES USERS(ID), GSM BIGINT NOT NULL);
CREATE TABLE ADDRESS(ID SMALLINT REFERENCES USERS(ID), Address VARCHAR(150) NOT NULL);