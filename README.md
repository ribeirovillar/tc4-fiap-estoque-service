# FIAP Estoque Service

API REST para gerenciamento de estoque de produtos, baseada em Quarkus e Clean Architecture.

---

## 🚀 Tecnologias

- **Java 21**
- **Quarkus 3.24.4** (REST, DI, DevMode)
- **PostgreSQL** (DB)
- **Flyway** (migrações SQL)
- **MapStruct** (DTO ↔ Domínio ↔ Persistência)
- **Jacoco** (cobertura de testes)
- **Hibernate ORM Panache** (persistência)
- **Arquitetura Limpa (Clean Architecture)**

---

## 📋 Endpoints REST

| Método | Endpoint                        | Descrição                      |
|--------|---------------------------------|--------------------------------|
| GET    | `/stocks`                       | Listar todos os estoques       |
| GET    | `/stocks/products/{productId}`  | Buscar estoque por ID produto  |
| POST   | `/stocks`                       | Criar novo registro de estoque |
| PUT    | `/stocks/products/{productId}`  | Atualizar quantidade estoque   |
| DELETE | `/stocks/products/{productId}`  | Remover registro de estoque    |

---

## 🏗️ Arquitetura

O projeto segue os princípios da **Clean Architecture**:

```
src/main/java/com/fiap/estoque/
├── controller/          # Camada de apresentação (REST API)
├── usecase/            # Casos de uso (regras de negócio)
├── domain/             # Entidades do domínio
├── gateway/            # Interfaces e implementações de persistência
├── mapper/             # Mapeamento DTO ↔ Domain ↔ Entity
├── exception/          # Exceções customizadas
└── infra/              # Infraestrutura (handlers, etc.)
```

---

## 🗄️ Modelo de Dados

### Stock (Estoque)
- `productId` (UUID): Identificador único do produto
- `quantity` (Integer): Quantidade em estoque (≥ 0)

---

## ⚙️ Configuração

### Variáveis de Ambiente

| Variável                       | Padrão                                    | Descrição           |
|--------------------------------|-------------------------------------------|---------------------|
| `QUARKUS_HTTP_PORT`           | `8082`                                    | Porta da aplicação  |
| `QUARKUS_DATASOURCE_USERNAME` | `postgres`                                | Usuário do banco    |
| `QUARKUS_DATASOURCE_PASSWORD` | `postgres`                                | Senha do banco      |
| `QUARKUS_DATASOURCE_JDBC_URL` | `jdbc:postgresql://localhost:5434/stockdb` | URL do banco       |

---

## 🚀 Como Executar

### Desenvolvimento
```bash
./mvnw compile quarkus:dev
```

### Produção
```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

### Docker
```bash
docker build -t fiap-estoque-service .
docker run -p 8082:8082 fiap-estoque-service
```

---

## 🧪 Testes

### Executar testes
```bash
./mvnw test
```

### Relatório de cobertura (Jacoco)
```bash
./mvnw test jacoco:report
# Relatório disponível em: target/jacoco-report/index.html
```

---

## 📦 Docker Compose

O projeto inclui configuração Docker Compose para desenvolvimento local com PostgreSQL.

```bash
docker-compose up -d
```

---

## 🔧 Funcionalidades

### Validações de Negócio
- ✅ Quantidade de estoque não pode ser negativa
- ✅ ID do produto é obrigatório
- ✅ Produto não pode ter estoque duplicado

### Exceções Customizadas
- `InvalidProductIdException`: ID do produto inválido
- `InvalidStockQuantityException`: Quantidade inválida
- `StockProductAlreadyRegisteredException`: Produto já possui estoque
- `StockProductNotFoundException`: Estoque não encontrado
