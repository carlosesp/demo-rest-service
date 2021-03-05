DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
    id INT(10) NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);