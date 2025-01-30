<center>
<img src='https://github.com/KauanMO/Bucket/blob/069f6e65d94cd4d0fc3aeaa91d40d63380dd8ac1/DuckMail-fonte-escura.png?raw=true' width='50%'>
</center>

🇧🇷 Portugues

## Sobre
DuckMail é um serviço de envio de emails em massa, facilmente integrável a qualquer sistema.

⚠️ Importante: Este serviço NÃO possui camada de segurança, nem autenticação. Ele foi projetado para ser utilizado em sistemas gerenciados por um API Gateway, onde a validação de usuários ocorre externamente.

## Principais Funcionalidades
- Agendamento de Campanhas: Associadas a modelos de email com destinatários específicos.
- Pixel Rastreador: Inserido automaticamente no corpo do email para capturar informações, como data de abertura, permitindo a construção de dashboards analíticos.
- Redirecionamento Monitorado: Links personalizados no corpo do email permitem capturar dados de interação e redirecionar os usuários para páginas associadas à campanha.

## Bibliotecas
- Spring Data JPA
- Validation
- Sql Server
- Lombok
- Spring RabbitMQ
- Quartz Scheduler
- AWS Java SDK Lambda

## Implantação
Ao clonar o repositório, temos alguns passos para implantação da aplicação.

### Requisitos
- Conta AWS
- AWS CLI Configurado
- Java JDK 21
- Maven Configurado

### Lambda
O DuckMail utiliza uma função AWS Lambda para envio de email. Essa função foi escrita em Node.JS e necessita ser implantada para que o envio dos emails seja executado.

Para isso, configure seu AWS CLI, vá até o diretório /lambda e execute o passo a passo:

Compacte os arquivos desse diretório:

`zip -r lambda-deployment.zip .`

Crie a função Lambda com o pacote compactado:

`aws lambda create-function --function-name DuckMailSenderLambda --runtime nodejs18.x --role arn:aws:iam::{ID_SUA_CONTA}:role/lambda-role  --handler mailSenderLambda.handler --zip-file fileb://lambda-deployme.zip --profile {SEU_PERFIL}`

⚠️ Importante: Atente-se ao nome da função, já que esse nome é passado nas variáveis de ambiente da aplicação.

### Aplicação
O serviço usa variáveis de ambiente padrões do Spring, porém temos também algumas variáveis que devem ser passadas para pleno funcionamento da aplicação, que podem ser encontradas em:

`/src/main/resources/META-INF/additional-spring-configuration-metadata.json`

Por fim, no diretório raiz, podemos rodar nossa aplicação via Maven com o comando:

`mvnw spring-boot:run`

--------------------------------

🇺🇸 English

## About
DuckMail is a mass email sending service, easily integrable into any system.

⚠️ Important: This service DOES NOT have a security layer or authentication. It is designed to be used in systems managed by an API Gateway, where user validation occurs externally.

## Main Features
- Campaign Scheduling: Associated with email templates and specific recipients.
- Tracking Pixel: Automatically inserted in the email body to capture information like open date, enabling the creation of analytical dashboards.
- Monitored Redirects: Custom links in the email body allow capturing interaction data and redirecting users to campaign-associated pages.

## Libraries
- Spring Data JPA
- Validation
- Sql Server
- Lombok
- Spring RabbitMQ
- Quartz Scheduler
- AWS Java SDK Lambda

## Deployment
After cloning the repository, there are a few steps to deploy the application.

### Requirements
- AWS Account
- AWS CLI Configured
- Java JDK 21
- Maven Configured

### Lambda
DuckMail uses an AWS Lambda function to send emails. This function is written in Node.js and needs to be deployed in order for the email sending to work.

To do this, configure your AWS CLI, navigate to the /lambda directory, and follow these steps:

Zip the files in this directory:

`zip -r lambda-deployment.zip .`

Create the Lambda function with the ziped package:

`aws lambda create-function --function-name DuckMailSenderLambda --runtime nodejs18.x --role arn:aws:iam::{ID_SUA_CONTA}:role/lambda-role  --handler mailSenderLambda.handler --zip-file fileb://lambda-deployme.zip --profile {SEU_PERFIL}`

⚠⚠️ Important: Pay attention to the function name, as it is passed in the application's environment variables.

### Application
The service uses default Spring environment variables, but there are also some variables that must be provided for the application to work properly. These can be found in:

`/src/main/resources/META-INF/additional-spring-configuration-metadata.json`

Finally, in the root directory, you can run the application via Maven with the following command:

`mvnw spring-boot:run`
