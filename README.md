# Spring Security JWT

API REST em Spring Boot com autenticação stateless via JWT (RS256), usando o próprio **OAuth2 Resource Server** do Spring Security para validar tokens e um endpoint próprio para emiti-los.

## Stack

- Java 17 · Spring Boot 3.5.6
- Spring Security + OAuth2 Resource Server (JWT)
- Spring Data JPA + MySQL
- Lombok

## Como funciona

```
Cliente
  │  POST /register (username, password, role)
  ▼
AuthController → AuthService → UserRepository (senha com BCrypt)

Cliente
  │  POST /authenticate (Basic Auth: username:password)
  ▼
AuthController → AuthService → JwtService
  │
  └── Gera um JWT assinado (RS256) com as authorities do usuário como "scope"

Cliente
  │  Requisições autenticadas: Authorization: Bearer <token>
  ▼
Spring valida o token com a chave pública (JwtDecoder)
```

- Autenticação em `/authenticate` é feita via **HTTP Basic** (usuário e senha), retornando um JWT.
- Esse JWT deve ser enviado como `Bearer token` nas requisições seguintes.
- O papel (`role`) do usuário vira uma authority (`SCOPE_ADMIN`, `SCOPE_USER`) usada no controle de acesso.

## Endpoints

| Método | Rota            | Acesso              | Descrição                         |
|--------|-----------------|---------------------|------------------------------------|
| POST   | `/register`     | Público             | Cria um usuário                    |
| POST   | `/authenticate` | Autenticado (Basic) | Retorna um JWT                     |
| GET    | `/admin`        | `SCOPE_ADMIN`       | Rota de exemplo restrita a admins  |

## Documentação da API (Swagger)
 
A documentação interativa fica disponível em:
 
```
http://localhost:8080/swagger-ui.html
```
A documentação fica acessível sem login para facilitar testes rápidos e avaliações.
 
## Configuração

### Banco de dados

Variáveis de ambiente esperadas em produção (`application.properties`):

```
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Para desenvolvimento local, o profile `local` (`application-local.properties`) já traz valores padrão de conexão. Ajuste conforme seu ambiente.

### Chaves RSA (JWT)

Os tokens são assinados com RS256, exigindo um par de chaves RSA:

```properties
jwt.public.key=classpath:app.pub
jwt.private.key=classpath:app.key
```

>`app.key`/`app.pub` versionados em `src/main/resources` apenas para facilitar rodar o projeto localmente. Gere seu próprio par.

Para gerar um novo par:

```bash
# Chave privada (PKCS#8, sem senha)
openssl genpkey -algorithm RSA -out app.key -pkeyopt rsa_keygen_bits:2048

# Chave pública correspondente
openssl rsa -pubout -in app.key -out app.pub
```

Substitua os arquivos em `src/main/resources/` (ou aponte `jwt.public.key`/`jwt.private.key` para outro local, ex. `file:/caminho/app.key`) e mantenha a chave privada fora do controle de versão.

## Executando

```bash
# Suba um MySQL local com o schema configurado, então:
./mvnw spring-boot:run
```

A aplicação inicia em `http://localhost:8080`.

### Exemplo de uso

```bash
# 1. Criar usuário
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{"username":"fernando","password":"123456","role":"ADMIN"}'

# 2. Autenticar e obter o token
curl -X POST http://localhost:8080/authenticate -u fernando:123456

# 3. Acessar rota protegida
curl http://localhost:8080/admin -H "Authorization: Bearer <token>"
```

Para inspecionar o conteúdo de um token gerado, use [jwt.io](https://jwt.io/).

## Referências
 
- [Spring Security – OAuth2 Resource Server (JWT)](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/index.html)
- [jwt.io](https://jwt.io/)
 
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/cesarfcg)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)](https://github.com/cesarfcg)
