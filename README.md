# JWT-AUTH

A aplicação utiliza **JWT (JSON Web Token)** para autenticação e autorização dos usuários.

###  Fluxo de autenticação

```text
Cliente
   │
   │ POST /auth/login
   │ email + senha
   ▼
AuthController
   │
   ▼
AuthService
   │
   ├── Busca usuário
   │
   ▼
UserRepository
   │
   ├── Usuário encontrado
   │
   ▼
PasswordEncoder
   │
   ├── Verifica senha
   │
   ▼
JwtService
   │
   ├── Gera JWT
   │
   ▼
Cliente
   │
   └── Recebe JWT

### JWT.io

Para visualizar e analisar os tokens JWT, pode ser utilizado o [JWT.io](https://jwt.io/).

O JWT.io permite visualizar o **Header**, **Payload** e **Signature** de um JWT.
