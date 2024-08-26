-- Dropar tabelas existentes
DROP TABLE IF EXISTS match_history;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS match_players;

-- Criar tabelas novamente
CREATE TABLE IF NOT EXISTS
    player (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name VARCHAR(255) NOT NULL,
        ia BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS
    match_history (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        winner_id INTEGER NOT NULL,
        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (winner_id) REFERENCES player(id)
    );

CREATE TABLE IF NOT EXISTS
    match_players (
        match_id INTEGER NOT NULL,
        player_id INTEGER NOT NULL,
        FOREIGN KEY (match_id) REFERENCES match_history(id),
        FOREIGN KEY (player_id) REFERENCES player(id),
        UNIQUE(match_id, player_id)
    );

-- Inserir jogadores
INSERT INTO player (name, ia) VALUES ('Player 1', FALSE);
INSERT INTO player (name, ia) VALUES ('Player 2', TRUE);
INSERT INTO player (name, ia) VALUES ('Player 3', FALSE);
INSERT INTO player (name, ia) VALUES ('Player 4', TRUE);

-- Inserir partidas
INSERT INTO match_history (winner_id) VALUES (1);
INSERT INTO match_history (winner_id) VALUES (2);

-- Verificar os dados
SELECT * FROM player;
SELECT * FROM match_history;
