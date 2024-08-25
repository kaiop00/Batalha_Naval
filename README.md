# Batalha Naval

Implementação do jogo Batalha Naval com Java utilizando Swing. Projeto realizado para disciplina de Verificação e Validação 2024.1 - UFC Campus Quixadá.

## Executando

Antes de executar a aplicação, é necessário adicionar as tabelas utilizadas pelo banco de dados, com o seguinte comando:

`sqlite3 database.db -init create_tables.sql`

Depois, você pode executar a aplicação com:

```sh
mvn javafx:run
```

### Debug

Para executar a aplicação em modo de debug é necessário configurar um debugger para conectar-se à porta 8000, no vscode você pode incluir a seguinte configuração no `launch.json`:

```json
{
            "type": "java",
            "name": "Debug (Attach) - Remote",
            "request": "attach",
            "hostName": "localhost",
            "port": 8000
        }
```

E executar a aplicação com

```sh
mvn javafx:run@debug
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

## Documentação

- [Documentação do TestFX com Junit5](https://testfx.github.io/TestFX/docs/javadoc/testfx-junit5/javadoc/org.testfx.junit5/module-summary.html)

- [Documentação Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

- [Inspetor de Componentes JavaFX](https://github.com/TangoraBox/ComponentInspector)
