## Gerência de Pessoas

Este projeto foi criado com intuito de aplicar e aperfeiçoar meu conhecimento no ecossistema do Spring Framework, bem como estudar ainda mais sobre. Ademais realizar a comunicação desta API Rest com um [microservice de envio de email](https://github.com/luan-coelho/email-sending-microservice) que já está hospedado na Heroku, e desta forma fazer uma introdução a arquitetura de microservices.

## 🚀 Começando

### 📋 Pré-requisitos

* [JDK 17.0.4 LTS](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Kit de
  Desenvolvimento Java
* [Maven](https://maven.apache.org/) - Gerenciador de Dependências
* [Postgres](https://www.postgresql.org/) - SGDB utilizado
* [Intellij IDEA (Opcional)](https://www.jetbrains.com/pt-br/idea/) - Ambiente de Desenvolvimento Integrado (IDE)

### ⚙️ Executando a aplicação

Como mencionado acima, o postgres é o SGDB que está sendo utilizado. Desta forma no arquivo de configuração do spring `application.yml` e na propriedade `spring:datasource:url` está definido como `jdbc:postgresql://localhost:5432/people-management`.

O postgres não aceita parâmetro de criação de banco de dados caso ele não exista. Portanto você terá que criar um previamente com o mesmo nome informado na configuração, ou seja `jdbc:postgr../{nome-do-banco}`, bem o como usuário e senha `spring:datasource:username` e `spring:datasource:password` respectivamente. 

#### Depois de ter configurado o projeto de acordo com seu ambiente ou suas necessidades, execute um dos comandos abaixo de acordo com seu sistema operacional

Windows 
````
mvn spring-boot:run
````

Linux ou macOS
````
./mvnw spring-boot:run
````

## 🔎 Consultando os endpoints com o Swagger

Como o contexto da API foi alterado com a propriedade `server:servlet:context-path: /api`, para acessar o swagger você deve ir no endereço <http://localhost:8080/api/swagger-ui/index.html> 






