# 🚗 Veículos DB - API RESTful

API RESTful desenvolvida com Spring Boot para o gerenciamento de veículos. O projeto segue a **arquitetura hexagonal (ports and adapters)**, utiliza autenticação via **JWT**, documentação interativa com **Swagger**, e boas práticas de testes com **H2** e **Mockito**.

---

## 🛠️ Tecnologias

- Java 17
- Spring Boot 3.4.4
- Spring Security 6.4.4
- JWT (JSON Web Token)
- Swagger (springdoc-openapi)
- JPA / Hibernate
- Banco de dados H2 (testes)
- JUnit 5.10 e Mockito

---

## ⚙️ Instalação
1. Para rodar o projeto:Clone o repositório:
   ```bash
    git clone https://github.com/gabrielhoffmannhub/VeiculosDb-final.git
2. Entre no diretório do projeto:
   ```bash
   cd VeiculosDb-final
4. Instale as dependências e inicie o projeto:
    ```bash
    mvn clean install
    mvn spring-boot:run
5. Acesse a aplicação:
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

---

## 🔐 Segurança
Este projeto restringe o acesso apenas à interface do Swagger UI, protegendo-a com autenticação via JWT.

A API em si não exige autenticação para os endpoints de negócio.

Apenas o Swagger está protegido para evitar acesso público à documentação interativa.
- login: admin
- senha: 1234

  ---

## 📁 Estrutura do Projeto
O projeto segue a arquitetura hexagonal, promovendo uma separação clara entre as camadas de domínio, aplicação e infraestrutura. Isso facilita a manutenção e a escalabilidade do sistema.

---

## 🧪 Testes
Os testes podem ser executados com:
  ```bash
  ./mvnw test
