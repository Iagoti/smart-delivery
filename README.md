# 🚚 SmartDelivery

SmartDelivery é um sistema de gestão de entregas desenvolvido em Java 21 com Spring Boot, que utiliza RabbitMQ para processar a atribuição de entregadores aos pedidos e integra com a API ViaCEP para preenchimento automático de endereços.

---

## 🧠 Visão Geral

O SmartDelivery tem como objetivo simplificar o processo de gestão de pedidos e entregas, automatizando etapas importantes como:
- Cadastro de clientes, produtos e entregadores;
- Criação e acompanhamento de pedidos;
- Atribuição automática de entregadores via fila RabbitMQ;
- Preenchimento de endereço via API ViaCEP;
- Controle de acesso baseado em papéis (ADMIN e CLIENTE).

---

## 🏗️ Tecnologias Utilizadas

| Categoria | Tecnologias |
|------------|--------------|
| Linguagem | Java 21 |
| Framework | Spring Boot |
| Mensageria | RabbitMQ |
| Banco de Dados | PostgreSQL |
| Integração Externa | ViaCEP API |
| Containerização | Docker & Docker Compose |
| Build Tool | Maven |

---

## ⚙️ Funcionalidades

1. Cadastro de Cliente
   - Nome
   - E-mail
   - Telefone
   - Endereço (preenchido automaticamente via API ViaCEP)

2. Cadastro de Produto
   - Nome
   - Descrição
   - Preço
   - Somente usuários ADMIN podem cadastrar produtos.

3. Criação de Pedido
   - Cliente seleciona produtos e finaliza o pedido.
   - Status inicial: CRIADO.

4. Cadastro de Entregador
   - Nome
   - CPF
   - Telefone
   - Somente usuários ADMIN podem cadastrar entregadores.

5. Gerenciamento de Entrega
   - Admin atribui um entregador ao pedido.
   - Status evolui: EM_ROTA → ENTREGUE.

6. Notificações
   - Quando o pedido for criado, uma mensagem é enviada para uma fila RabbitMQ.

---

## 🐳 Execução com Docker

O projeto possui um Docker Compose para facilitar a criação do banco PostgreSQL e do RabbitMQ.

Comandos:

```
docker-compose up -d
./mvnw spring-boot:run
```

A aplicação será iniciada em [http://localhost:8080](http://localhost:8080)

---

## 🧩 Estrutura do Projeto

smart-delivery/
│
├── src/
│   ├── main/java/com/iago/smartdelivery/
│   │   ├── configs/                → Criação de usuário administrador, configuração do RabbitMQ e segurança (Spring Security)
│   │   ├── integrations/zipCode/   → Integração com a API ViaCEP
│   │   ├── modules/                → Módulos principais do sistema
│   │   │   ├── customers/          → Cadastro e gerenciamento de clientes
│   │   │   ├── deliveryman/        → Cadastro e controle de entregadores
│   │   │   ├── orders/             → Criação, listagem e atualização de pedidos
│   │   │   ├── products/           → Cadastro e gerenciamento de produtos
│   │   │   └── users/              → Autenticação, perfis e controle de acesso
│   └── main/resources/
│       ├── application.yml         → Configurações da aplicação
│       └── ...
│
├── docker-compose.yml              → Subida do PostgreSQL e RabbitMQ
├── pom.xml                         → Dependências do Maven
└── README.md

---

## 🧠 Fluxo da Mensageria (RabbitMQ)

1. O cliente cria um pedido via API REST.
2. A aplicação publica uma mensagem na fila RabbitMQ com os dados do pedido.
3. Um serviço consumidor processa a mensagem e atribui um entregador disponível ao pedido.
4. O status do pedido é atualizado para EM_ROTA.
5. Após a confirmação da entrega, o status muda para ENTREGUE.

---

## 🌐 Integração com ViaCEP

Durante o cadastro de cliente, o sistema consome a API pública do ViaCEP:
```
GET https://viacep.com.br/ws/{CEP}/json/
```
Os campos de endereço são automaticamente preenchidos com base na resposta.

---

## 🔐 Perfis de Usuário

**ADMIN:** Gerenciar produtos, entregadores e pedidos  
**CLIENTE:** Criar pedidos e visualizar status

---

## 🧾 Status do Pedido

| Status | Descrição |
|---------|------------|
| CRIADO | Pedido registrado pelo cliente |
| EM_ROTA | Entregador atribuído e entrega iniciada |
| ENTREGUE | Pedido finalizado |

---

## 🚀 Rotas

### Cadastro de Clientes (POST)
**URL:** `http://localhost:8080/customers`  
**Body:**
```json
{
  "name": "Isabela2",
  "password": "customer",
  "phone": "(77) 98872-8483",
  "email": "isa2@gmail.com",
  "zipcode": "45051140"
}
```
**Autenticação (Basic Auth):**  
`admin@smartdelivery.com / admin123`

---

### Cadastro de Produtos (POST)
**URL:** `http://localhost:8080/products`  
**Body:**
```json
{
  "code": 1,
  "description": "Cachorro Quente",
  "price": 10,
  "name": "Cachorro Quente"
}
```
**Autenticação (Basic Auth):**  
`admin@smartdelivery.com / admin123`

---

### Cadastro de Pedidos (POST)
**URL:** `http://localhost:8080/orders`  
**Body:**
```json
{
  "productsIds": []
}
```
**Autenticação (Basic Auth):**  
`admin@smartdelivery.com / admin123`

---

### Cadastro de Entregador (POST)
**URL:** `http://localhost:8080/deliveryman`  
**Body:**
```json
{
  "name": "Eduardo",
  "phone": "7799999-9999",
  "document": "123456789"
}
```
**Autenticação (Basic Auth):**  
`admin@smartdelivery.com / admin123`

---

### Alterar Status do Pedido (PUT)
**URL:** `http://localhost:8080/orders/delivered/{idPedido}`  
**Autenticação (Basic Auth):**  
`admin@smartdelivery.com / admin123`

---

### Buscar Todos os Produtos (GET)
**URL:** `http://localhost:8080/products`
