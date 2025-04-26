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
- Banco de dados em memória H2

## Configuração do Projeto
## Buildar e Executar o projeto
```shell script
./gradlew bootRun
```

## Acessar a documentação
```
http://localhost:8080/api-docs
```

