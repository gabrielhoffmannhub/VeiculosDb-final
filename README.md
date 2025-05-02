# 🚀 Tecnologias Utilizadas
#### Java 17+

#### Spring Boot

#### Spring Security

#### JWT (JSON Web Token)

#### Maven

#### JPA / Hibernate

#### H2 (para testes)

#### JUnit / Mockito

# ⚙️ Instalação
Para rodar o projeto:Clone o repositório: bash''' git clone https://github.com/gabrielhoffmannhub/VeiculosDb-final.git
Entre no diretório do projeto:

bash
Copiar
Editar
cd VeiculosDb-final
Gere a chave JWT para a autenticação.

Instale as dependências e inicie o projeto:

bash
Copiar
Editar
mvn clean install
mvn spring-boot:run
A API estará disponível em: http://localhost:8080

✅ Testes
Para rodar os testes (não é necessário gerar a chave JWT para os testes):

Execute os testes com o comando:

bash
Copiar
Editar
mvn test
🔐 Autenticação
A autenticação é realizada via JWT (JSON Web Token). Para acessar as rotas protegidas, é necessário incluir o token JWT no cabeçalho das requisições.

📁 Estrutura do Projeto
O projeto segue a arquitetura hexagonal, promovendo uma separação clara entre as camadas de domínio, aplicação e infraestrutura. Isso facilita a manutenção e a escalabilidade do sistema.

