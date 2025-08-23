# Desafio Backend da Uber


## Microsserviço de E-mail


![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) 
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) 
[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE) 
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white) 
![Microservices](https://img.shields.io/badge/Microservices-2470C0?style=for-the-badge&logo=microservices&logoColor=white)


Este projeto é um microsserviço de envio de emails desenvolvido em **Java, Spring e AWS Simple Email Service**. Foi criado para resolver o ***desafio tecnico backend da Uber***, que está disponível [neste repositório](https://github.com/uber-archive/coding-challenge-tools/blob/master/coding_challenge.md).


## Arquitetura e Estrutura  de Diretórios

O projeto segue uma arquitetura que incorpora princípios de ***Clean Architecture*** e
***Domain-Driven Design***.

* **Clean Architecture:** Optei por essa abordagem para separar as regras de negócio da aplicação das dependências de framework e infraestrutura. Isso torna o sistema mais testável, manutenível e flexível a mudanças. Exemplo: trocar o AWS SES pelo SendGrid exigiria apenas uma nova implementação no adaptador, sem tocar no core da aplicação.


* **AWS SES:** Escolhido pela sua escalabilidade, baixo custo e integração nativa com o ecossistema da AWS, que é amplamente utilizado no mercado.
```
com.felipe.emailservice/
├── EmailServiceApplication.java      # Ponto de entrada da aplicação
├── controllers/                      # Camada de interface (REST)
├── core/                             # Núcleo da aplicação (domínio)
│   ├── dto/                          # Objetos de transferência de dados
│   ├── exceptions/                   # Exceções específicas do domínio
│   └── usecases/                     # Casos de uso (regras de negócio)
├── application/                      # Camada de aplicação (orquestração)
├── adapters/                         # Interfaces de adaptadores
└── infrastructure/                   # Implementações concretas
     ├── configuration/               # Configurações de infraestrutura
     └── ses/                         # Implementação específica do AWS SES
```



## Sumário

- [Pré-requisitos](#pré-requisitos)
- [Fluxo de execução](#fluxo-de-execução)
- [Instalação](#instalação)
- [Uso](#uso)
- [Endpoints da API](#endpoints-da-api)
- [Contribuindo](#contribuindo)
- [Licença](#licença)



## Pré-requisitos

```
Java Development Kit (JDK) 17 ou superior
Apache Maven 3.6+
Conta na Amazon Web Services (AWS)
Credenciais AWS válidas
Email verificado no Amazon SES (para possibilitar envio de email de teste)
```


## Fluxo de execução

**Requisição HTTP: Cliente faz POST /api/email/send com JSON:**
```
Controller: EmailSenderController recebe e valida a requisição
DTO: EmailRequest é criado a partir do JSON
Serviço: EmailSenderService orquestra o caso de uso
Gateway: Interface EmailSenderGateway é chamada
Infraestrutura: SesEmailSender executa o envio real via AWS SES
Resposta: Controller retorna status HTTP apropriado
```



## Instalação

1. Clone o repositorio:

```bash
git clone https://github.com/filipeluisgg/desafio-backend-uber.git
```


2. Instale as dependencias com Maven:

```bash
mvn clean install
```


3. Atualize o `application.properties` colocando suas credenciais da AWS:

```yaml
aws.region=us-east-1
aws.accessKeyId=0123456
aws.secretKey=0123456
```


## Uso

1. Inicie a aplicação com Maven:
```bash
mvn spring-boot:run
```

<br>
2. A API ficará accessível em http://localhost:8080



## Endpoints da API

**Enviar Email**

POST `/api/email/send` — Envia um email para o destino especificado na propriedade `destiny`, no corpo em JSON da requisição.

**Exemplo de Corpo da Requisição**

```json
{
  "destiny": "email.de.destino@email.com",
  "subject": "assunto-do-email",
  "body": "corpo-do-email"
}
```


## Contribuindo

Contribuições são bem vindas. Se você achar problemas ou tiver sugestões de melhoria, por favor, abra uma `issue` ou um
`pull request` no repositório.

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b branch-name`)
3. Commit suas mudanças (`git commit -m 'feat: add some feature '`)
4. Push para a branch (`git push origin branch-name`)
5. Abra um Pull Request

Obs: Se optar por fazer uma contribuição, por favor siga o estilo de código já existente no projeto e [convenções de commit](https://www.conventionalcommits.org/en/v1.0.0/).



## Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.



## Autores

* **Luis Felipe** - *Trabalho inicial*
