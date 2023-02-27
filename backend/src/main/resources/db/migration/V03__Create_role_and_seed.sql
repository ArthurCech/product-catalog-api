CREATE TABLE tb_role (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    authority VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

INSERT INTO tb_role (authority, created_at, updated_at) VALUES ('ROLE_OPERATOR', NOW(), NOW());
INSERT INTO tb_role (authority, created_at, updated_at) VALUES ('ROLE_ADMIN', NOW(), NOW());