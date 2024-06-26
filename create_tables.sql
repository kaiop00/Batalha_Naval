PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS
    player (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        ia BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS
    match_history (
        id SERIAL PRIMARY KEY,
        winner_id INT NOT NULL,
        match_id INT NOT NULL,
        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (winner_id) REFERENCES player(id)
    );

CREATE TABLE IF NOT EXISTS
    match_players (
        match_id INT NOT NULL,
        player_id INT NOT NULL,
        FOREIGN KEY (match_id) REFERENCES match_history(id),
        FOREIGN KEY (player_id) REFERENCES player(id)
    );