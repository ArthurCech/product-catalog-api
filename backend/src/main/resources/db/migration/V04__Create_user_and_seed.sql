CREATE TABLE tb_user (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

INSERT INTO tb_user (first_name, last_name, email, password, created_at, updated_at) VALUES ('Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', NOW(), NOW());
INSERT INTO tb_user (first_name, last_name, email, password, created_at, updated_at) VALUES ('Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', NOW(), NOW());