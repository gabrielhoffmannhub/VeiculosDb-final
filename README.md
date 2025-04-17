# 🚗 Veículos DB
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

# 📦 Instalação
Para rodar o projeto:
#### 1. Clone o repositório: git clone https://github.com/seu-usuario/veiculosdb.git

#### 2. Entre no diretório do projeto 

#### 3. Gere a chave JWT para a autenticação

- você pode gerar uma chave com o seguinte comando: openssl rand -base64 32

- Essa chave será usada para assinar o token.

#### 6. Ao rodar o projeto, defina a chave JWT no terminal como uma variável de ambiente
   
- No Linux/macOS: export JWT_SECRET=<sua_chave_gerada>

- No Windows (cmd): set JWT_SECRET=<sua_chave_gerada>

#### 5. Instale as dependências e inicie o projeto:
   
- mvn clean install

- mvn spring-boot:run

- A API estará disponível em: http://localhost:8080

# 🧪Testes
### Para rodar os testes (não é necessário gerar a chave JWT para os testes)
#### Execute os testes com o comando: 

- mvn test

# 🔐 Autenticação
#### A autenticação é feita via JWT. 
#### Para obter o token:

POST /auth

Body: 

{

  "username": "admin",
  
  "password": "admin"
  
}
#### O token JWT será retornado e deve ser utilizado no header das requisições autenticadas:

#### Header: 

Authorization:Bearer <seu_token>

## 🚘 Endpoints

Método | Endpoint | Descrição

GET | /carros | Lista todos os carros

POST | /carros | Cadastra um novo carro

GET | /carros/{id} | Busca um carro por ID

PUT | /carros/{id} | Atualiza um carro
DELETE | /carros/{id} | Remove um carro



