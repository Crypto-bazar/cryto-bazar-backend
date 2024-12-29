CREATE SEQUENCE IF NOT EXISTS product_entity_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS user_entity_id_gen START WITH 1 INCREMENT BY 50;

CREATE TABLE advertisement_status
(
    id          SERIAL PRIMARY KEY,
    name        varchar(50) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP DEFAULT NOW()
);

INSERT INTO advertisement_status(name, description)
VALUES ('На рассмотрении', 'Объявление ожидает проверки модератором'),
       ('Одобрено', 'Объявление прошло проверку и опубликовано'),
       ('Отклонено', 'Объявление отклонено модератором'),
       ('В архиве', 'Объявление помещено в архив и будет удалено через 2 недели');

CREATE TABLE role
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMP DEFAULT NOW(),
    updated_at  TIMESTAMP DEFAULT NOW()
);

INSERT INTO role(name, description)
VALUES ('User', 'Пользователь системы'),
       ('Moderator', 'Модератор системы'),
       ('Admin', 'Администратор системы')

CREATE TABLE user
(
    id                SERIAL PRIMARY KEY,
    login             VARCHAR(255) NOT NULL UNIQUE,
    email             VARCHAR(255) NOT NULL UNIQUE,
    name              VARCHAR(255),
    password          VARCHAR(255) NOT NULL,
    phone_number      VARCHAR(30)  NOT NULL,
    account_verified  BOOLEAN   DEFAULT FALSE,
    document_verified BOOLEAN   DEFAULT FALSE,
    role              VARCHAR(255),
    created_at        TIMESTAMP DEFAULT NOW(),
    updated_at        TIMESTAMP DEFAULT NOW(),
);

CREATE TABLE advertisement
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    owner_id     INTEGER      NOT NULL,
    description  TEXT,
    amount       INTEGER DEFAULT 0,
    photo        VARCHAR(255),
    ad_status_id INT,
    owner_id     INT,
    CONSTRAINT fk_ad_status FOREIGN KEY (ad_status_id) REFERENCES advertisement_status (id),
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES user (id)
);