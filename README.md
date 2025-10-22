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

docker-compose up -d
./mvnw spring-boot:run

A aplicação será iniciada em http://localhost:8080

---

## 🧩 Estrutura do Projeto

```bash
smart-delivery/
├── src/
│   ├── main/java/com/iago/smartdelivery/
│   │   ├── configs/            → Configs de Admin, RabbitMQ e Segurança (Spring Security)
│   │   ├── integrations/zipCode/ → Integração com a API ViaCEP
│   │   ├── modules/            → Módulos principais do sistema
│   │   │   ├── customers/      → Cadastro e gerenciamento de clientes
│   │   │   ├── deliveryman/    → Cadastro e controle de entregadores
│   │   │   ├── orders/         → Criação, listagem e atualização de pedidos
│   │   │   ├── products/       → Cadastro e gerenciamento de produtos
│   │   │   └── users/          → Autenticação, perfis e controle de acesso
│   └── main/resources/
│       ├── application.yml     → Configurações da aplicação
│       └── ...
│
├── docker-compose.yml          → Subida do PostgreSQL e RabbitMQ
└── pom.xml                     → Dependências do Maven
```
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
GET https://viacep.com.br/ws/{CEP}/json/
Os campos de endereço são automaticamente preenchidos com base na resposta.

---

## 🔐 Perfis de Usuário

ADMIN: Gerenciar produtos, entregadores e pedidos
CLIENTE: Criar pedidos e visualizar status

---

## 🧾 Status do Pedido

CRIADO → Pedido registrado pelo cliente
EM_ROTA → Entregador atribuído e entrega iniciada
ENTREGUE → Pedido finalizado

---

## 🌐 Rotas da API (Endpoints)

A API REST está disponível em `http://localhost:8080`.

| Funcionalidade | Método | Rota | Autenticação | Descrição |
| :--- | :--- | :--- | :--- | :--- |
| **Cadastro de Clientes** | `POST` | `/customers` | Basic Auth (ADMIN) | Cria um novo cliente. Necessita de `zipcode` para ViaCEP. |
| **Busca de Produtos** | `GET` | `/products` | Qualquer | Lista todos os produtos disponíveis. |
| **Cadastro de Produtos** | `POST` | `/products` | Basic Auth (ADMIN) | Adiciona um novo produto ao catálogo. |
| **Criação de Pedidos** | `POST` | `/orders` | Basic Auth (CLIENTE) | Cria um novo pedido. Dispara notificação via RabbitMQ. |
| **Cadastro de Entregador** | `POST` | `/deliveryman` | Basic Auth (ADMIN) | Registra um novo entregador no sistema. |
| **Alterar Status** | `PUT` | `/orders/delivered/{idPedido}` | Basic Auth (ADMIN) | Atualiza o status do pedido para `ENTREGUE`. |

### Exemplos de Requisição (JSON Body)

| Rota | Exemplo de Body |
| :--- | :--- |
| `POST /customers` | `{"name": "Isabela", "password": "customer", "phone": "(77) 98872-8483", "email": "isa2@gmail.com", "zipcode": "45051140"}` |
| `POST /products` | `{"code": 1, "description": "Cachorro Quente", "price": 10, "name": "Cachorro Quente"}` |
| `POST /orders` | `{"productsIds": [ 1, 2, 3 ]}` |
| `POST /deliveryman` | `{"name": "Eduardo", "phone": "7799999-9999", "document": "123456789"}` |

### Credenciais de Teste (Basic Auth)

Utilize as seguintes credenciais para acesso de administrador nos endpoints restritos:

| Usuário | Senha | Perfil |
| :--- | :--- | :--- |
| `admin@smartdelivery.com` | `admin123` | ADMIN |
| *Para CLIENTE, use as credenciais criadas no cadastro.* | | CLIENTE |

---
