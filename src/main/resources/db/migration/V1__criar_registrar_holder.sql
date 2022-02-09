CREATE TABLE holder(
       id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
       external_key VARCHAR(255) NOT NULL,
       name VARCHAR(100) NOT NULL,
       national_registration VARCHAR(11) NOT NULL UNIQUE,
       birth_date DATE NOT NULL,
       active BOOLEAN DEFAULT TRUE,
       date_creation DATE NOT NULL,
       info VARCHAR(255)
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
        date_creation DATE NOT NULL,
        info VARCHAR(255),
        holder_id BIGINT(20) NOT NULL,
        FOREIGN KEY (holder_id) REFERENCES holder (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ---------------------------------------------
CREATE TABLE account(
       id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
       external_key VARCHAR(255) NOT NULL,
       balance BIGINT DEFAULT 0,
       active BOOLEAN DEFAULT TRUE,
       account_number BIGINT NOT NULL UNIQUE,
       date_creation DATE NOT NULL,
       account_type VARCHAR(255) NOT NULL,
       holder_id BIGINT(20) NOT NULL,
       FOREIGN KEY (holder_id) REFERENCES holder (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------------------------------
CREATE TABLE transaction(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    external_key VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    observation VARCHAR(255) NOT NULL,
    value NUMERIC,
    date_transaction DATE,
    transaction_type VARCHAR(255) NOT NULL,
    account_id BIGINT(20) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------------------------------------
INSERT INTO holder(external_key, name, national_registration, birth_date, active, date_creation, info)
VALUES('654b18a4-e94f-4aa0-94bd-591da9312fea', 'Luiz Paulo Aureliano', '72102792021', '1974-07-13', true, '2022-02-05', null);

INSERT INTO address(street, number, complement, neighborhood, zip_code, city, country, date_creation, info,  holder_id)
VALUES('Rua Bruce Wayne', '2131', 'casa01', 'Boa Vista', '99999-999', 'São Gonçalo', 'Rio de Janeiro', '2022-02-05', null, 1);

-- SALDO INSERIDO COMO EXEMPLO
INSERT INTO account(external_key, balance, active, account_number, date_creation, account_type, holder_id)
VALUES('b3d06c51-a147-4038-87ca-39a5c2b05a45', 100, default, 1, '2022-02-05', 'NORMAL', 1);

-- -------------------------------------------