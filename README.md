# 🍉 API de Processamento de Vendas e Pagamentos — Hortifruti Vale Verde

Este projeto tem como objetivo o desenvolvimento de uma API REST para registrar vendas e processar pagamentos de uma empresa fictícia  (**Hortifruti Vale Verde**), que atua em Belo Horizonte e região metropolitana, no estado de Minas Gerais.

A empresa possui múltiplas **sedes** e **fornecedores** espalhados pelo estado, com vendas realizadas tanto por **lojas físicas** quanto por **aplicativos mobile**.

A aplicação foi desenvolvida em **Java 17** utilizando **Spring Boot 3.4.5**, integrada ao banco de dados **PostgreSQL**. Todo o ecossistema é containerizado com **Docker** e orquestrado via **Docker Compose**, 
funcionando de forma consistente em ambientes de desenvolvimento e produção.

Por fim, um workflow com o **GitHub Actions** foi implementado para automatizar os testes unitários e de integração, além do processo de build e deploy da aplicação na instância **EC2** da AWS

---

## ☁️ Arquitetura na AWS

Diagrama dos serviços **AWS** utilizados.

<p align="center">
  <img src="images/diagram-aws.png" alt="Diagrama da Arquitetura na AWS" width="700">
</p>

---

## 🧠 Modelagem Lógica do Banco de Dados

<p align="center">
  <img src="images/logic-model.png" alt="Diagrama da Modelagem Lógica" width="700">
</p>

---

## ✅ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4.5
- PostgreSQL
- Docker & Docker Compose
- AWS (EC2 e RDS)
- Jakarta Bean Validation
- Lombok
