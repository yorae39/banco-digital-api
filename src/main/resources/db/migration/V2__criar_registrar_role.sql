CREATE TABLE role(
     id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
     role_type VARCHAR(50)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO role(role_type) VALUES('ROLE_USER');
INSERT INTO role(role_type) VALUES('ROLE_ADMIN');
-- -----------------------------------------
CREATE TABLE user(
    id BIGINT(20)PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(255)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO user(username, password)
VALUES('user', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');
INSERT INTO user(username, password)
VALUES('admin', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');
-- -----------------------------------------------------------------------------
CREATE TABLE user_role(
     id BIGINT(20)PRIMARY KEY AUTO_INCREMENT,
     user_id BIGINT,
     role_id BIGINT,
     CONSTRAINT fk_usuario FOREIGN KEY (user_id)
         REFERENCES user (id),
     CONSTRAINT fk_role FOREIGN KEY (role_id)
         REFERENCES role (id)
);

INSERT INTO user_role(user_id, role_id)
VALUES(1, 1);
INSERT INTO user_role(user_id, role_id)
VALUES(1, 2);

