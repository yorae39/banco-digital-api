CREATE TABLE holder(
       id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       national_registration VARCHAR(11) NOT NULL UNIQUE,
       birth_date DATE NOT NULL,
       active BOOLEAN DEFAULT TRUE,
       date_creation DATE NOT NULL,
       info VARCHAR(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- --------------------------------------------
CREATE TABLE address(
        id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
        street VARCHAR(50) NOT NULL,
        number VARCHAR(10) NOT NULL,
        complement VARCHAR(20) NOT NULL,
        neighborhood VARCHAR(20) NOT NULL,
        zip_code VARCHAR(10) NOT NULL,
        city VARCHAR(30) NOT NULL,
        country VARCHAR(20)  NOT NULL,
        address_id BIGINT(20) NOT NULL,
        holder_id BIGINT(20) NOT NULL,
        FOREIGN KEY (address_id) REFERENCES address (id),
        FOREIGN KEY (holder_id) REFERENCES holder (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ---------------------------------------------
CREATE TABLE account(
       id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
       balance DOUBLE DEFAULT 0.0,
       active BOOLEAN DEFAULT TRUE,
       account_number BIGINT NOT NULL,
       date_creation DATE NOT NULL,
       account_type VARCHAR(255) NOT NULL,
       account_id BIGINT(20) NOT NULL,
       holder_id BIGINT(20) NOT NULL,
       FOREIGN KEY (account_id) REFERENCES account (id),
       FOREIGN KEY (holder_id) REFERENCES holder (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------------------------------
CREATE TABLE transaction(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    observation VARCHAR(255) NOT NULL,
    value NUMERIC,
    date_transaction DATE NOT NULL,
    transaction_type VARCHAR(255) NOT NULL,
    account_id BIGINT(20) NOT NULL,
    transaction_id BIGINT(20) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (transaction_id) REFERENCES transaction (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------------------------------------
INSERT INTO holder(name, national_registration, birth_date, active, date_creation, info)
VALUES('Luiz Paulo Aureliano', '72102792021', '1974-07-13', true, '2022-02-05', null);

INSERT INTO address(street, number, complement, neighborhood, zip_code, city, country, address_id, holder_id)
VALUES('Rua Bruce Wayne', '2131', 'casa01', 'Boa Vista', '99999-999', 'São Gonçalo', 'Rio de Janeiro', 1, 1);

INSERT INTO account(balance, active, account_number, date_creation, account_type, account_id, holder_id)
VALUES(100.0, true, 1, '2022-02-05', 'NORMAL', 1, 1);

-- -------------------------------------------