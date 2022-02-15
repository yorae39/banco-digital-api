CREATE TABLE barcode_register(
    id BIGINT(20)PRIMARY KEY AUTO_INCREMENT,
    external_key VARCHAR(255) NOT NULL,
    account_external_key VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    observation VARCHAR(255),
    value NUMERIC
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

