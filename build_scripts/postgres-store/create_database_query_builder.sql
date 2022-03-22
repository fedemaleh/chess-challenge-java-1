DROP DATABASE IF EXISTS CHESS_CHALLENGE_QUERY_BUILDER;
CREATE DATABASE CHESS_CHALLENGE_QUERY_BUILDER;

DROP TABLE WINNERS_QUERY_BUILDER;
DROP TABLE PIECES_QUERY_BUILDER;
CREATE TABLE IF NOT EXISTS WINNERS_QUERY_BUILDER (
    winner VARCHAR(9) CONSTRAINT winners_query_builder_pk PRIMARY KEY NOT NULL CHECK (winner IN ('white', 'black', 'none')),
    matches INTEGER
);

CREATE TABLE IF NOT EXISTS PIECES_QUERY_BUILDER (
    piece VARCHAR(6) CONSTRAINT pieces_query_builder_pk PRIMARY KEY NOT NULL CHECK (piece IN ('bishop', 'knight', 'pawn', 'queen', 'rook', 'king')),
    matches INTEGER
);

INSERT INTO WINNERS_QUERY_BUILDER (winner, matches) VALUES ('none', 6);
INSERT INTO WINNERS_QUERY_BUILDER (winner, matches) VALUES ('white', 10);
INSERT INTO WINNERS_QUERY_BUILDER (winner, matches) VALUES ('black', 3);

INSERT INTO PIECES_QUERY_BUILDER (piece, matches) VALUES ('queen', 5);
INSERT INTO PIECES_QUERY_BUILDER (piece, matches) VALUES ('bishop', 4);
INSERT INTO PIECES_QUERY_BUILDER (piece, matches) VALUES ('knight', 2);
INSERT INTO PIECES_QUERY_BUILDER (piece, matches) VALUES ('pawn', 0);
INSERT INTO PIECES_QUERY_BUILDER (piece, matches) VALUES ('rook', 2);
INSERT INTO PIECES_QUERY_BUILDER (piece, matches) VALUES ('king', 0);