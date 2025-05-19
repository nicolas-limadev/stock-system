# Sistema de Controle de Estoque de Produtos

## Descrição
API desenvolvida com Spring Framework para gerenciamento e controle de estoque de produtos. O sistema permite uma gestão flexível de produtos, categorias e estoques.

## Funcionalidades Principais
- Gerenciamento de produtos
- Controle de múltiplos estoques
- Categorização de produtos
- Rastreamento de inventário

## Regras de Negócio
- Um produto pode estar presente em múltiplos estoques
- Um produto deve obrigatoriamente pertencer a uma categoria
- Uma categoria pode conter múltiplos produtos
- Produtos podem existir no sistema sem estarem alocados em um estoque

## Tecnologias Utilizadas
- Spring Framework
- Spring Boot
- Spring Data JPA
- Lombok
- Banco de dados em memória H2 o ambiente de testes e desenvolvimento
- Banco de dados postgres para o ambiente de produção
- Docker e docker compose

# Configuração do Projeto

## Remover a pasta build, buildar e testar o projeto
```shell script
./gradlew clean build
```
## Buildar e executar o projeto
```shell script
./gradlew bootRun
```
## Desenvolvimento (Local)
```shell script
./gradlew bootRun --args='--spring.profiles.active=dev'
```
## Produção (Local)
```shell script
./gradlew bootRun --args='--spring.profiles.active=prod'
```
## Executar com o Docker
```shell script
docker compose up --build -d
```
## Acessar a documentação com todos os endpoints
```
http://localhost:8080
```
## Acessar relatório de testes (Somente localmente)
```
http://localhost:8080/test-reports
```

