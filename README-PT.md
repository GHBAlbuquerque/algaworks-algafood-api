# algaworks-algafood-api

AlgaFood API é um projeto de um serviço de delivery de comida inspirado no IFood criado pela Algaworks. 

Esta API permite que você crie e gerencie tudo relacionado aos restaurantes para o modelo de negócio de entregas. Crie uma base de parceiros, cadastre e atualize produtos, métodos de pagamento, responsáveis e acompanhe todo o fluxo de pedidos - tudo isso seguindo as melhores práticas para a criação de APIs Rest. 

## Tecnologia

Estas são as tecnologias usadas no projeto:

* Java 11
* Spring Framework
* MySql Database
* Maven

## Serviços

* Github
* Heroku
* Postman
* Amazon S3
* SendGrid

## Diagrama de Classes
![Diagrama de Classes](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/auxiliar/diagrama-de-classes-de-dominio.jpg)

*propriedade da Algaworks.

## Como usar

Foi feito o deploy desta aplicação no Heroku e requisições podem ser feitas através da seguinte url:
> https://algaworks-algafood-api.herokuapp.com/

Você encontra a documentação **OpenApi 3.0** aqui:
> [Documentation](https://algaworks-algafood-api.herokuapp.com/swagger-ui/index.html)

A coleção **Postman** completa está disponível caso deseje testar a API facilmente:

> [Postman Collection - heroku](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/postman/%5BAlgaWorks%5D%20AlgaFood%20-%20ESR%20Heroku.postman_collection.json)

------------------

Utilizando a coleção:

Primeiramente, importe o arquivo json no Postman.

A coleção está pronta para uso, porém, esta API é protegida pelo protocolo OAuth 2.0. Para ser autenticado, siga estes passos:

* Clique na pasta mãe ([AlgaWorks] AlgaFood - ESR Heroku)
* Vá para a aba "Authorization"
* OAuth 2.0 já estará selecionado e preenchido. Role para baixo e clique em **Get New Access Token**
* Clique em "Proceed" e "Use Token"
* Todos os endpoints foram configurados para herdar a autenticação da pasta mãe.

As credenciais sáo obtidas a partir de usuários cadastrados no banco de dados. Para autenticar novos usuário, use a API para persistir novos dados.


- Necessário atribuir Grupos e Permissões para uso dos endpoints.



## Começando - rodando localmente

Faça o download ou clone do repositório e rode em uma IDE (preferenciamente IntelliJ).

Rodá-lo localmente requer a JDK 11 ou mais instalada e um banco local do MySQL na porta 3306.

1. Prepare a sua IDE selecionando a JDK após abrir o projeto. (Java 11)
2. Aguarde o download de todas as dependências
3. Coloque o profile 'dev' no application.properties
4. Clique no botão de play na classe AlgaworksAlgafoodApiApplication

A coleção completa do Postman está disponível aqui caso deseje testar a API facilmente:

> [Postman Collection - local](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/postman/%5BAlgaWorks%5D%20AlgaFood%20-%20ESR.postman_collection.json)

### Usando docker

É necessário possui o Docker instalado no seu computador. Se não o possuí, vá até https://docs.docker.com/desktop/windows/install/ e baixe a versão desktop.

1. Baixe a imagem do [Docker Hub](https://hub.docker.com/layers/ghbalbuquerque/algafood-api-ghba/latest/images/sha256:d700da54759acd6a91e99899e8c76343d78bfa4a86dc4f4786b7baa67c5fb7c4)
2. Na raiz do projeto, na IDE, use o terminal para rodar o comando 'docker-compose up'
3. Aguarde até que as imagens sejam baixadas e os containers inicializem
4. Para parar os contaianers, use o terminal para rodar o comando 'docker-compose down'

Você pode usar a coleção local do Postman para testar a API. Apenas remova a porta (:8080) das urls configuradas nas variáveis.

> [Postman Collection - local](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/postman/%5BAlgaWorks%5D%20AlgaFood%20-%20ESR.postman_collection.json)

Se preferir gerar sua própria imagem docker do projeto, este arquivo pode ajudá-lo com alguns comandos básicos:
> [Docker Aux file](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/auxiliar/docker_commands.txt)


## Features

- Princípios de design de APIs Rest
- Hateoas
- SOLID
- DDD
- Tratamento de exceções
- Upload e download de arquivos utilizando AWS SDK e bucket S3
- Envio automatico de e-mails com Spring Email e SendGrid
- Documentação (OpenApi)
- Authenticação e Segurança com OAuth 2.0
- Dockerização


## Links

- Repositório: https://github.com/GHBAlbuquerque/algaworks-algafood-api
- Repositório do projeto do curso: https://github.com/algaworks/curso-especialista-spring-rest
- Site da Algaworks: https://www.algaworks.com/
- Curso Especialista Spring Rest: https://cafe.algaworks.com/esr-lista-de-espera/
- Em caso de bugs ou vulnerabilidades, escreva para ghb.albuquerque@gmail.com.


## Versioning

1.0.0.0


## Authors

* **Giovanna Albuquerque**: @GHBAlbuquerque (https://github.com/GHBAlbuquerque)
* **Algaworks**: @AlgaWorks (https://github.com/algaworks)

Obrigada por visitar!
