# Catálogo de Produtos

## O que é?

API para catálogo de produtos gerais desenvolvida em **Spring Boot** 2.4.4.

Toda a parte de segurança da API foi feita com **Spring Security** e **OAuth 2**.

Para a geração de access token e refresh token, foi desenvolvido um **Authorization Server** e o **Resource Server**.

Se tratando de qualidade de código e prevenção de erros, **testes unitários** e de **integração** dos pontos mais cruciais foram desenvolvidos.

## UML

Em desenvolvimento.

## Usuários e Permissões

### Usuários:

- *ROLE_OPERATOR*
  - username: alex@gmail.com
  - password: 123456
- *ROLE_ADMIN*
  - username: maria@gmail.com
  - password: 123456

### Recursos:

- **`/api/products/**`**
  - ROLE_OPERATOR
    - find all, find by id
  - ROLE_ADMIN
    - find all, find by id, create, update e delete
- **`/api/categories/**`**
  - ROLE_OPERATOR
    - find all, find by id
  - ROLE_ADMIN
    - find all, find by id, create, update e delete
- **`/api/users/**`**
  - ROLE_ADMIN
    - find all, find by id, create, update e delete

## Como rodar o sistema

Requisitos básicos:

- Java 11
- Maven
- Git

1. Clone o projeto

```
https://github.com/ArthurCech/product-catalog-api.git
```

2. Acesse a pasta backend que se encontra dentro da pasta raiz

```
cd product-catalog-api/backend
```

3. Execute o projeto

```
./mvnw spring-boot:run
```

## Endpoints

Em construção.
