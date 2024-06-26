# Batalha Naval

Implementação do jogo Batalha Naval com Java utilizando Swing. Projeto realizado para disciplina de Verificação e Validação 2024.1 - UFC Campus Quixadá.

## Executando

Antes de executar a aplicação, é necessário adicionar as tabelas utilizadas pelo banco de dados, com o seguinte comando:

`sqlite3 database.db -init create_tables.sql`

Depois, você pode executar a aplicação com:

```sh
mvn javafx:run
```

E os testes com

```sh
mvn test
```

## Divisão de Tarefas

- Camada View: @Pedro-Emanuel
- Camada Controller: @Francisco-Paulino-Arruda-Filho e @kaiop00
- Camada Model: @erickgabrielfg
- Camada DAO: @Joao-Pedro-P-Holanda
