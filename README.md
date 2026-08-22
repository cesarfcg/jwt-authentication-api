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
