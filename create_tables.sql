PRAGMA foreign_keys = ON;

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