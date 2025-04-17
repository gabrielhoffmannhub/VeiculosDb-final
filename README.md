## 🚗 Veículos DB
Uma API RESTful desenvolvida com Spring Boot para gerenciar registros de veículos, utilizando arquitetura hexagonal, autenticação via JWT e boas práticas de desenvolvimento.

## 🛠️ Tecnologias Utilizadas
Java 17+

Spring Boot

Spring Security

JWT (JSON Web Token)

Maven

JPA / Hibernate

H2 (para testes)

JUnit / Mockito

## 📦 Instalação
bash
Copiar código
git clone https://github.com/seu-usuario/veiculosdb.git
cd veiculosdb
./mvnw clean install
./mvnw spring-boot:run
A API estará disponível em: http://localhost:8080

## 🔐 Autenticação
A autenticação é feita via JWT. Para obter o token:

POST /auth
Body:

json
Copiar código
{
  "username": "admin",
  "password": "admin"
}
O token JWT será retornado e deve ser utilizado no header das requisições autenticadas:

makefile
Copiar código
Authorization: Bearer <seu_token>

## 🚘 Endpoints
Método	Endpoint	Descrição
GET	/carros	Lista todos os carros
POST	/carros	Cadastra um novo carro
GET	/carros/{id}	Busca um carro por ID
PUT	/carros/{id}	Atualiza um carro
DELETE	/carros/{id}	Remove um carro

## 🧪 Testes
Para rodar os testes:

bash
Copiar código
mvn test
