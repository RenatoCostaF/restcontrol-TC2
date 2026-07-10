# RestControl-TC2

Backend para gestão compartilhada de restaurantes, desenvolvido para o Tech Challenge — Fase 2 (Pós-Tech Arquitetura e Desenvolvimento Java).

Permite cadastrar usuários (clientes e donos de restaurante), restaurantes e itens de cardápio através de uma API REST em Spring Boot, com persistência em MongoDB.

**Equipe:** Gabriel Reis Santos (RM374090) · Rafael Bompadre Lima (RM372049) · Renato da Costa Furtado (RM372171)

## Sumário
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Modelo de domínio](#modelo-de-domínio)
- [Regras de negócio](#regras-de-negócio)
- [Endpoints da API](#endpoints-da-api)
- [Tratamento de erros](#tratamento-de-erros)
- [Como executar](#como-executar)
- [Testes](#testes)
- [Documentação interativa](#documentação-interativa)

## Arquitetura

O projeto segue os princípios de **Clean Architecture**, com separação entre `domain` (regras de negócio, independentes de framework) e `infra` (detalhes técnicos: HTTP, persistência, configuração).

```
Cliente HTTP
     │
     ▼
Infra / Controllers REST   (recebe HTTP, valida com Bean Validation, retorna DTOs)
     │
     ▼
Domain / Controllers       (orquestram os casos de uso, DTO → entidade)
     │
     ▼
Domain / Use Cases         (regras de negócio de cada agregado)
     │
     ▼
Domain / Gateways          (portas de saída — interfaces)
     │
     ▼
Infra / Persistence        (implementação dos gateways com Spring Data MongoDB)
     │
     ▼
MongoDB
```

Principais decisões de design:
- **Entidades ricas**: validações de negócio (`isValidName`, `isValidPrice` etc.) ficam dentro das entidades de domínio, não nos controllers.
- **Injeção por construtor** em todas as classes (sem `@Autowired` em campo), seguindo SOLID.
- **DTOs por camada**: DTOs de request/response na infra; DTOs de input próprios entre domain controller e use case. Conversões centralizadas via **MapStruct**.
- **Tratamento global de exceções** (`GlobalExceptionHandler`) padronizando respostas de erro como `ProblemDetail` (RFC 7807).
- **Sem autenticação**: a API não implementa login/JWT — está fora do escopo desta fase. Qualquer cliente pode chamar os endpoints diretamente.

## Tecnologias

| Categoria | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot (Web MVC, Validation, Data MongoDB) |
| Banco de dados | MongoDB 8.0 |
| Mapeamento objeto-objeto | MapStruct 1.6.3 |
| Documentação de API | SpringDoc OpenAPI 3.0.2 (Swagger UI) |
| Testes | JUnit 5, Mockito, Testcontainers (MongoDB) |
| Build | Maven 3.9+ |
| Containerização | Docker, Docker Compose |

## Modelo de domínio

| Entidade | Campos | Coleção MongoDB |
|---|---|---|
| **UserType** | `id`, `name`, `code` (único; `RESTAURANT_OWNER` ou `CUSTOMER`) | `user_types` |
| **User** | `id`, `name`, `email`, `password` (nunca retornado nas respostas), `userTypeId` | `users` |
| **Restaurant** | `id`, `name`, `address`, `cuisineType`, `openingHours`, `ownerId` | `restaurants` |
| **MenuItem** | `id`, `name`, `description`, `price`, `availableOnlyInRestaurant`, `imageUrl`, `restaurantId`, `active` | `menu_items` |

Ao subir a aplicação, os tipos `Cliente` (`CUSTOMER`) e `Dono de Restaurante` (`RESTAURANT_OWNER`) são criados automaticamente (`UserTypeSeedConfig`), caso ainda não existam.

## Regras de negócio

- **User**: `email` deve ter formato válido; `password` obrigatória com no mínimo 8 caracteres; `userTypeId` deve referenciar um `UserType` existente.
- **Restaurant**: `ownerId` deve referenciar um `User` existente **e** esse usuário precisa ter `UserType` com `code = RESTAURANT_OWNER`. Apenas o próprio dono pode excluir o restaurante (validado via parâmetro `runningUserId` na exclusão).
- **MenuItem**: `restaurantId` deve referenciar um `Restaurant` existente; `price` deve ser maior que zero; todos os campos são obrigatórios (incluindo os booleanos `active` e `availableOnlyInRestaurant`).
- **UserType**: `code` é único e indexado no MongoDB.

## Endpoints da API

Base path: `/v1`

| Recurso | Método | Rota | Descrição |
|---|---|---|---|
| User Types | `POST` | `/user-types` | Cria um tipo de usuário |
| | `PUT` | `/user-types/{id}` | Atualiza um tipo de usuário |
| | `GET` | `/user-types` | Lista todos |
| | `GET` | `/user-types/{id}` | Busca por ID |
| | `DELETE` | `/user-types/{id}` | Remove por ID |
| Users | `POST` | `/users` | Cria um usuário |
| | `PUT` | `/users/{id}` | Atualiza um usuário |
| | `GET` | `/users/{id}` | Busca por ID |
| | `DELETE` | `/users/{id}` | Remove por ID |
| Restaurants | `POST` | `/restaurants` | Cria um restaurante |
| | `PUT` | `/restaurants/{id}` | Atualiza um restaurante |
| | `GET` | `/restaurants` | Lista todos |
| | `GET` | `/restaurants/search?name=` | Busca por nome |
| | `GET` | `/restaurants/{id}` | Busca por ID |
| | `DELETE` | `/restaurants/{id}?runningUserId=` | Remove (somente o dono) |
| Menu Items | `POST` | `/menuitems` | Cria um item de cardápio |
| | `PUT` | `/menuitems/{id}` | Atualiza um item |
| | `GET` | `/menuitems` | Lista todos |
| | `GET` | `/menuitems/{id}` | Busca por ID |
| | `DELETE` | `/menuitems/{id}` | Remove um item |

## Tratamento de erros

Todas as respostas de erro seguem o padrão `ProblemDetail` (RFC 7807), com `title`, `status`, `detail`, `timestamp` e `path`.

| Status | Quando ocorre |
|---|---|
| `400 Bad Request` | Falha de validação (Bean Validation) ou regra de domínio violada (ex.: senha curta, preço ≤ 0) |
| `403 Forbidden` | Usuário tenta excluir um restaurante do qual não é dono |
| `404 Not Found` | Recurso ou referência (`userTypeId`, `ownerId`, `restaurantId`) inexistente |
| `409 Conflict` | Conflito de identificador duplicado |

## Como executar

### Com Docker

```bash
docker compose up --build
```

Sobe dois containers: `restcontrol-TC2-app` (porta 8080) e `restcontrol-TC2-db` (MongoDB, porta 27017).

### Localmente, sem Docker

Pré-requisitos: JDK 21, Maven 3.9+ (ou `./mvnw`) e MongoDB rodando em `localhost:27017`.

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`, conectando por padrão em `mongodb://localhost:27017/restcontrol-tc2` (sobrescrevível pela variável `SPRING_DATA_MONGODB_URI`).

## Testes

O projeto possui testes unitários (entidades, use cases, mappers e controllers com JUnit + Mockito) e testes de integração com **Testcontainers**, subindo uma instância real de MongoDB em container para validar os gateways e os fluxos completos de cada recurso.

```bash
./mvnw test
```

## Documentação interativa

Com a aplicação em execução:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Uma collection do Postman também está disponível em [`restcontrol-TC2.postman_collection.json`](./restcontrol-TC2.postman_collection.json).
