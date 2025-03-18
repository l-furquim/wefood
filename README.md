# wefood 🍔 

# Sistema de Delivery com Microserviços

![Spring Boot](https://img.shields.io/badge/Spring-Boot-green?logo=spring) ![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-6DB33F?logo=springcloud) ![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white) ![Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?logo=apache-kafka&logoColor=white)

## Visão Geral

Este projeto é um sistema de delivery que permite aos usuários realizar pedidos em restaurantes cadastrados. A aplicação é composta por diversos microserviços que se comunicam de forma assíncrona através do **Apache Kafka**, garantindo um sistema escalável, resiliente e desacoplado.

## Arquitetura do Projeto

A arquitetura é baseada em microserviços, permitindo que cada serviço seja desenvolvido, implantado e escalado de forma independente. Os principais microserviços disponíveis são:

- **ms de pagamento:** Gerencia o processamento de pagamentos e o gerenciamento de contas bancárias.
- **ms de pedidos:** Realiza o processamento dos pedidos, integrando-se com os microserviços de pagamento, email e notificações.
- **ms de profile:** Responsável pelo cadastro dos usuários, fornecendo um token JWT para autenticação e validação.

## Microserviços

### 1. ![Pagamento](https://img.shields.io/badge/Microserviço%20Pagamento-blue)
- **Descrição:** Processa pagamentos e gerencia as contas bancárias dos usuários e restaurantes.
- **Tecnologias:** Spring Boot, Spring Cloud, Apache Kafka,Redis.
- **Comunicação:** Interage com o microserviço de pedidos via Apache Kafka para sincronização dos processos de pagamento.

---

### 2.![Pedidos](https://img.shields.io/badge/Microserviço%20Pedidos-orange)
- **Descrição:** Gerencia a criação e o processamento dos pedidos realizados pelos usuários.
- **Integrações:** 
  - Se comunica com o **ms de pagamento** para a confirmação e processamento dos pagamentos.
  - Integra com o **ms de email** para envio de confirmações e atualizações dos pedidos.
  - Se conecta com o **ms de notificações** para enviar alertas e atualizações em tempo real.
- **Tecnologias:** Spring Boot, Apache Kafka,Redis.
- **Comunicação:** Utiliza o **Apache Kafka** para a comunicação assíncrona entre os microserviços.

---

### 3.![Profile](https://img.shields.io/badge/Microserviço%20Profile-green)
- **Descrição:** Gerencia o cadastro dos usuários e realiza a autenticação, emitindo tokens JWT para acesso seguro ao sistema.
- **Tecnologias:** Spring Boot, Spring Security, JWT,Redis, Apache Kafka.
- **Funcionalidades:** 
  - Cadastro de novos usuários.
  - Emissão e validação de tokens JWT para autenticação.

---

### 4. ![Profile](https://img.shields.io/badge/Microserviço%20Mail-red)
- **Descrição:** Gerencia o pedido de envios de email e cancelamento dos mesmos.
- **Tecnologias:** Spring Boot, Spring mailer,Redis, Apache kafka.
- **Funcionalidades:** 
  - Cadastro de emails.
  - Envio dos emails
  - Cancelamento dos emails
  - Listener disponibilizado para o envio dos emails assincronos.

---

### 5. ![Profile](https://img.shields.io/badge/Microserviço%20Notification-purple)
- **Descrição:** Gerencia o envio de notificações utilizando o protocolo SSE (Server sent event).
- **Tecnologias:** Spring Boot, Spring mailer,Redis, Apache kafka.
- **Funcionalidades:** 
  - Conexão SSE baseada no email do usuário
  - Gerenciamento das notificações enviadas
  - Listener disponibilizado para o envio das notificações assincronas.

## Tecnologias Utilizadas

- **Spring Web**: ![Spring Web](https://img.shields.io/badge/Spring%20Web-green?logo=spring)
- **Spring Cloud**: ![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-6DB33F?logo=springcloud)
- **Eureka Server**: ![Eureka](https://img.shields.io/badge/Eureka-Spring%20Cloud-6DB33F?logo=springcloud)
- **Open Feign**: ![Open Feign](https://img.shields.io/badge/Eureka-Spring%20Cloud-6DB33F?logo=springcloud)
- **Redis**: ![Redis](https://img.shields.io/badge/Redis-DC382D?logo=redis)
- **Postgres**: ![Postgres](https://img.shields.io/badge/PostgreSQL-336791?logo=postgresql)
- **Apache Kafka**: ![Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?logo=apache-kafka&logoColor=white)
- **OpenAPI**: ![OpenAPI](https://img.shields.io/badge/OpenAPI-2.0-1A1A1A?logo=swagger)
- **Docker**: ![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)

---

## Pré-requisitos

- **Java 11** ou superior
- **Maven** e **Gradle**
- **Docker** e **Docker Compose** (se aplicável)
- **Apache Kafka**, **Redis** e **Postgres** configurados (caso não utilize containers Docker)

---
