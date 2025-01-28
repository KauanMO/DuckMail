🇧🇷 Portugues - Brasil

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
