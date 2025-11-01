# 🚀 StatusHub: API de Monitoramento de Uptime

![Java](https://img.shields.io/badge/Java-17+-blue?logo=apachemaven&logoColor=white)
![Quarkus](https://img.shields.io/badge/Quarkus-3.12.x-blueviolet?logo=quarkus)
![Build](https://img.shields.io/badge/build-passing-brightgreen)
![License](https://img.shields.io/badge/license-GNU-lightgrey)

O `StatusHub` é uma API de monitoramento de disponibilidade (Uptime Monitoring) leve e performática. Este projeto foi desenvolvido como um estudo aprofundado do ecossistema **Java Quarkus**, com foco em consolidar habilidades de nível Júnior para Pleno.

---

## 📋 Tabela de Conteúdos

- [🚀 StatusHub: API de Monitoramento de Uptime](#-statushub-api-de-monitoramento-de-uptime)
  - [📋 Tabela de Conteúdos](#-tabela-de-conteúdos)
    - [1. O Problema que Resolve](#1-o-problema-que-resolve)
    - [2. Funcionalidades Principais](#2-funcionalidades-principais)
    - [3. Arquitetura do Sistema](#3-arquitetura-do-sistema)
      - [💡 Comunicação entre Módulos](#-comunicação-entre-módulos)
    - [4. Stack de Tecnologias](#4-stack-de-tecnologias)
    - [5. Como Executar (Ambiente de Dev)](#5-como-executar-ambiente-de-dev)
      - [🪄 A Mágica do Quarkus Dev Services](#-a-mágica-do-quarkus-dev-services)
    - [6. Documentação da API (Swagger)](#6-documentação-da-api-swagger)
    - [7. Objetivos de Estudo](#7-objetivos-de-estudo)
    - [8. Próximos Passos (Expansão)](#8-próximos-passos-expansão)

---

### 1. O Problema que Resolve

Para desenvolvedores, freelancers e pequenos times, saber imediatamente quando um site, blog ou API crítica fica indisponível é crucial. O `StatusHub` permite que usuários cadastrem URLs para serem monitoradas, executando verificações periódicas em background e disparando alertas (via e-mail) assim que uma falha é detectada.

### 2. Funcionalidades Principais

* **🔐 Autenticação:** Sistema completo de cadastro e login de usuários baseado em **JWT**.
* **⚙️ Gerenciamento de Monitores:** CRUD completo para que usuários logados possam criar, listar, editar, pausar e deletar seus "Monitores" (URLs a serem verificadas).
* **🤖 Motor de Verificação:** Um serviço agendado (`quarkus-scheduler`) que roda em background, verificando de forma assíncrona as URLs cadastradas nas frequências definidas (ex: a cada 5 min).
* **⚡ Alertas Reativos:** Envio de notificações (via `quarkus-mailer`) quando um monitor muda de estado (ex: `UP` -> `DOWN` ou `DOWN` -> `UP`).
* **📊 Histórico de Status:** Armazenamento e consulta do histórico de verificações, incluindo tempo de resposta e código HTTP.

### 3. Arquitetura do Sistema

Este projeto **não é um conjunto de microsserviços**, mas também não é um "monólito" tradicional bagunçado.

Adotamos a abordagem de **Monólito Modular (Modular Monolith)**.

Isto significa que o sistema é um **único deploy** (um único `.jar`), mas internamente o código é estritamente separado por domínios (módulos), com baixo acoplamento.

```

src/main/java/br/com/statushub/
│
├── 1️⃣ auth/         (Cuida de Usuários, Login, JWT)
├── 2️⃣ monitoring/   (Cuida do CRUD de Monitores)
├── 3️⃣ verification/ (Cuida do Scheduler e da lógica de verificação HTTP)
└── 4️⃣ notification/ (Cuida do envio de alertas por e-mail)

````

#### 💡 Comunicação entre Módulos

A comunicação entre os módulos é feita de forma **desacoplada e assíncrona** usando **Eventos CDI Assíncronos** (`@ObservesAsync`).

**Exemplo de fluxo:**
1.  O módulo `verification` detecta que um site caiu.
2.  Ele **não** chama o `NotificationService` diretamente.
3.  Ele dispara um evento: `MonitorFailedEvent`.
4.  O módulo `notification` "escuta" esse evento e, de forma independente e em outra thread, dispara o e-mail de alerta.

### 4. Stack de Tecnologias

A stack foi escolhida para maximizar a produtividade e a performance, seguindo o paradigma "Supersonic Subatomic Java".

* **Core:** Java 17+ e Quarkus
* **API:** RESTEasy Reactive com Jackson (Não-Bloqueante)
* **Persistência:** Hibernate ORM com Panache
* **Banco de Dados:** PostgreSQL (com driver `quarkus-jdbc-postgresql`)
* **Segurança:** Quarkus Security (com `quarkus-smallrye-jwt` e `quarkus-security-bcrypt`)
* **Jobs Agendados:** `quarkus-scheduler`
* **Cliente HTTP:** `quarkus-rest-client-reactive` (para verificar as URLs)
* **Notificações:** `quarkus-mailer`
* **Documentação:** `quarkus-smallrye-openapi` (Gera Swagger UI)
* **Testes:** JUnit 5, REST Assured e `quarkus-test-common`

### 5. Como Executar (Ambiente de Dev)

O Quarkus simplifica drasticamente o ambiente de desenvolvimento.

**Pré-requisitos:**
* JDK 17+
* Maven 3.8+
* Docker (recomendado, mas não obrigatório - veja abaixo)

**Passos:**

1.  Clone o repositório:
    ```bash
    git clone [https://github.com/b4yerl/statushub.git](https://github.com/b4yerl/statushub.git)
    cd statushub
    ```

2.  Execute em Modo de Desenvolvimento:
    ```bash
    ./mvnw quarkus:dev
    ```

#### 🪄 A Mágica do Quarkus Dev Services

**Você não precisa instalar ou configurar o PostgreSQL!**

Ao rodar o comando `quarkus:dev`, o Quarkus detecta a dependência `quarkus-jdbc-postgresql`, entende que você precisa de um banco e **sobe automaticamente um container Docker com o PostgreSQL** para você. Ele também configura a aplicação para se conectar a este banco temporário.

### 6. Documentação da API (Swagger)

Com a aplicação rodando em modo `dev`, o Quarkus gera automaticamente a documentação OpenAPI (Swagger UI).

Acesse em seu navegador:
* **Swagger UI:** [http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)

### 7. Objetivos de Estudo

Este projeto foi desenhado para praticar e consolidar os seguintes conceitos, que são a ponte entre o nível Júnior e Pleno:

* ✅ **Arquitetura Limpa:** Construir um Monólito Modular com fronteiras claras entre os domínios.
* ✅ **Processamento Assíncrono:** Usar `quarkus-scheduler` para jobs em background e Eventos CDI (`@ObservesAsync`) para desacoplamento.
* ✅ **Programação Reativa:** Utilizar o cliente HTTP reativo para chamadas de I/O não-bloqueantes.
* ✅ **Segurança de API:** Implementar autenticação e autorização do zero usando JWT.
* ✅ **Boas Práticas de Teste:** Escrever testes de integração (`@QuarkusTest`) que cobrem a lógica de negócios e os endpoints da API.
* ✅ **Persistência Eficiente:** Usar o padrão "Active Record" do Panache de forma correta.

### 8. Próximos Passos (Expansão)

Após o MVP, o aprendizado pode continuar com os seguintes desafios:

* [ ] **CI/CD:** Criar um pipeline no GitHub Actions para rodar os testes (`./mvnw test`) e fazer o build a cada push.
* [ ] **Notificações por Webhook:** Permitir que o usuário cadastre um Webhook (ex: Slack, Discord) para ser notificado além do e-mail.
* [ ] **Containerização:** Escrever um `Dockerfile` e um `docker-compose.yml` para rodar a aplicação em "produção" com o PostgreSQL.
* [ ] **Observabilidade:** Adicionar as extensões `quarkus-micrometer` (métricas) e `quarkus-smallrye-health` (Health Checks).

---

Licença: GNU
````