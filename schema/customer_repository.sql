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
    BEGIN
            INSERT INTO customer_history SELECT * FROM customer;
            RETURN NEW;
    END;
$example_table$ LANGUAGE plpgsql;

CREATE TRIGGER tr_tblCustomer_ForUpdate BEFORE UPDATE ON customer FOR EACH ROW EXECUTE PROCEDURE logfunc();