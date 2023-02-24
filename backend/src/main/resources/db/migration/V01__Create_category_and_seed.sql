CREATE TABLE tb_category (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

INSERT INTO tb_category (name, created_at, updated_at) VALUES ('Livros', NOW(), NOW());
INSERT INTO tb_category (name, created_at, updated_at) VALUES ('Eletrônicos', NOW(), NOW());
INSERT INTO tb_category (name, created_at, updated_at) VALUES ('Computadores', NOW(), NOW());