CREATE TABLE bank_statement(
    id BIGINT(20)PRIMARY KEY AUTO_INCREMENT,
    external_key VARCHAR(255) NOT NULL,
    account_external_key VARCHAR(255) NOT NULL,
    initial_date DATE NOT NULL,
    final_date DATE NOT NULL,
    transaction_type VARCHAR(255) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE bank_statement_transaction(
      id BIGINT(20)PRIMARY KEY AUTO_INCREMENT,
      transaction_id BIGINT,
      bank_statement_id BIGINT,
      CONSTRAINT fk_transaction FOREIGN KEY (transaction_id)
          REFERENCES transaction (id),
      CONSTRAINT fk_bank_statement FOREIGN KEY (bank_statement_id)
          REFERENCES bank_statement (id)
);
