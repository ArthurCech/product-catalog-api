CREATE TABLE tb_category (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

INSERT INTO tb_category (name, created_at, updated_at) VALUES ('Books', NOW(), NOW());
INSERT INTO tb_category (name, created_at, updated_at) VALUES ('Electronics', NOW(), NOW());
INSERT INTO tb_category (name, created_at, updated_at) VALUES ('Computers', NOW(), NOW());