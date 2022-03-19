DROP DATABASE IF EXISTS CHESS_CHALLENGE;
CREATE DATABASE CHESS_CHALLENGE;

CREATE TABLE IF NOT EXISTS RESULTS (
    id SERIAL CONSTRAINT results_pk PRIMARY KEY,
    winner CHAR(9) NOT NULL CHECK (winner IN ('white', 'black', 'no_winner')),
    piece CHAR(6) NOT NULL CHECK (piece IN ('bishop', 'king', 'knight', 'pawn', 'queen', 'rook', 'none'))
);

INSERT INTO RESULTS (winner, piece) VALUES ('no_winner', 'none');
INSERT INTO RESULTS (winner, piece) VALUES ('white', 'bishop');
INSERT INTO RESULTS (winner, piece) VALUES ('black', 'queen');