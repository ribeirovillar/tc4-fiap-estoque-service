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

| Método | Endpoint                        | Descrição                           |
|--------|---------------------------------|-------------------------------------|
| GET    | `/stocks`                       | Listar todos os estoques            |
| GET    | `/stocks/products/{productId}`  | Buscar estoque por ID produto       |
| POST   | `/stocks`                       | Criar novo registro de estoque      |
| PUT    | `/stocks/products/{productId}`  | Atualizar quantidade estoque        |
| DELETE | `/stocks/products/{productId}`  | Remover registro de estoque         |
| POST   | `/stocks/deduct`                | Baixa de estoque em lote            |
| POST   | `/stocks/reverse`               | Reverter baixa de estoque em lote   |

### Novos Endpoints de Operações em Lote

#### POST `/stocks/deduct`
Realiza baixa de estoque para múltiplos produtos. Recebe uma lista de `StockDTO` com `productId` e `quantity` para deduzir do estoque.

**Exemplo de Request Body:**
```json
[
  {
    "productId": "123e4567-e89b-12d3-a456-426614174000",
    "quantity": 5
  },
  {
    "productId": "123e4567-e89b-12d3-a456-426614174001",
    "quantity": 3
  }
]
```

#### POST `/stocks/reverse`
Reverte baixas de estoque previamente realizadas. Útil quando um pedido falha após a baixa de estoque ter sido processada.

**Exemplo de Request Body:**
```json
[
  {
    "productId": "123e4567-e89b-12d3-a456-426614174000",
    "quantity": 5
  },
  {
    "productId": "123e4567-e89b-12d3-a456-426614174001",
    "quantity": 3
  }
]
```

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

| Variável                       | Padrão                                      | Descrição           |
|--------------------------------|---------------------------------------------|---------------------|
| `QUARKUS_HTTP_PORT`           | `8082`                                      | Porta da aplicação  |
| `QUARKUS_DATASOURCE_USERNAME` | `postgres`                                  | Usuário do banco    |
| `QUARKUS_DATASOURCE_PASSWORD` | `postgres`                                  | Senha do banco      |
| `QUARKUS_DATASOURCE_JDBC_URL` | `jdbc:postgresql://localhost:5434/stockdb` | URL do banco       |

**Nota:** O banco de dados PostgreSQL está configurado para rodar na porta **5434** para evitar conflitos com outras instâncias.

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

O projeto inclui configuração Docker Compose para desenvolvimento local com PostgreSQL na porta 5434.

```bash
docker-compose up -d
```

---

## 🔧 Funcionalidades

### Validações de Negócio
- ✅ Quantidade de estoque não pode ser negativa
- ✅ ID do produto é obrigatório
- ✅ Produto não pode ter estoque duplicado
- ✅ Operações em lote validam estoque suficiente antes de processar
- ✅ Transações atômicas para operações em lote

### Exceções Customizadas
- `InvalidProductIdException`: ID do produto inválido
- `InvalidStockQuantityException`: Quantidade inválida
- `StockProductAlreadyRegisteredException`: Produto já possui estoque
- `StockProductNotFoundException`: Estoque não encontrado
- `InsufficientStockException`: Estoque insuficiente para operação

### Operações em Lote
- **Baixa de Estoque**: Processa múltiplos produtos em uma única transação
- **Reversão de Estoque**: Reverte baixas previamente realizadas
- **Validação Prévia**: Verifica disponibilidade antes de processar qualquer item
- **Atomicidade**: Se um item falhar, toda a operação é revertida
