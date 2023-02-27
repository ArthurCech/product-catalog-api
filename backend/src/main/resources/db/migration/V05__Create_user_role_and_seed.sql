CREATE TABLE tb_user_role (
    fk_user BIGINT NOT NULL,
    fk_role BIGINT NOT NULL,
    PRIMARY KEY (fk_user, fk_role),
    CONSTRAINT fk_user FOREIGN KEY (fk_user) REFERENCES tb_user(id),
    CONSTRAINT fk_role FOREIGN KEY (fk_role) REFERENCES tb_role(id)
);

INSERT INTO tb_user_role (fk_user, fk_role) VALUES (1, 1);
INSERT INTO tb_user_role (fk_user, fk_role) VALUES (2, 1);
INSERT INTO tb_user_role (fk_user, fk_role) VALUES (2, 2);