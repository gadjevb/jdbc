CREATE SEQUENCE customer_id;

CREATE TABLE customer
(
    ID INT DEFAULT NEXTVAL('customer_id') PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Age SMALLINT NOT NULL
);

CREATE TABLE customer_history
(
    ID INT NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Age SMALLINT NOT NULL
);

CREATE OR REPLACE FUNCTION logfunc() RETURNS TRIGGER AS $example_table$
    DECLARE
            old_ID INT;
            old_Name VARCHAR(50);
            old_Age SMALLINT;
    BEGIN
            old_ID = OLD.ID;
            old_Name = OLD.Name;
            old_Age = OLD.Age;
            INSERT INTO customer_history(ID, Name, Age) VALUES(old_ID, old_Name, old_Age);
            RETURN NEW;
    END;
$example_table$ LANGUAGE plpgsql;

CREATE TRIGGER tr_tblCustomer_ForUpdate AFTER UPDATE ON customer FOR EACH ROW EXECUTE PROCEDURE logfunc();