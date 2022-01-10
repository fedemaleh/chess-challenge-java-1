## Chess Challenge

### Objective
This challenge is thought of as a training challenge to try different technologies and design methodologies. Chess is just the excuse to practice a little bit of coding...

### Requirements
1. **Modeling the chess pieces.** We need to model each of the pieces in the game, only how they move and how they take. We need to model:
   1. **King:** This piece can move one square in each of the 8 directions (forward, backward, left, right, and 4 diagonals). *Note: We won't model castling.*
   2. **Queen:** This piece can move any number of squares in each of the 8 directions.
   3. **Rook:** This piece can move any number of squares horizontally (left and right) and vertically (forward, backward).
   4. **Bishop:** This piece can move any number of squares diagonally. 
   5. **Knight:** This piece has a unique movement. It can either move 2 squares horizontally and 1 vertically or 2 vertically and 1 horizontally.
   6. **Pawn:** This piece can move 1 square forward each time or 2 squares the first time it's moved (if it's white then it happens when it's placed in row 2 while for black pawns happens in row 7). It takes pieces that are in the adjacent forward diagonal squares. *Note: We will model neither promotion nor en passant.*

2. **Checkmate Detector:** In this requirement, the program will receive the path to a `json` file that lists the pieces in the chessboard. The program should return if there is a checkmate on the board (and which color wins). We can say there is a checkmate if one of the Kings is being attacked by a rival piece, it can't move and no other piece can take the attacking piece or block the attack. *Note: the Knight is the only piece which attack can't be blocked*.
The `json` file should have the following format:
```json
{
    "pieces": [
        {
            "type": "king", // one of "king", "queen", "rook", "bishop", "knight", "pawn"
            "position": {
                "row": 1,
                "column": "f" // one of "a", "b", "c", "d", "e", "f", "g", "h",
            },
            "color": "white" // one of "white", "black"
        }
    ],
}
```

The challenge will provide `json` samples to test the implementation. 

3. **Creating a Web Server:** Once we have a working checkmate detector, we will want to have a web service to use it. This web server will have a single endpoint that will receive the board (same `json` format as above) and return the result. A sample request is be:

```bash
curl -X POST 'localhost:8080/checkmate/detector' -d @path_to_json_file
```

4. **Collecting Statistics**. Now that we can use our checkmate detector through a web server we want to be able to collect some statistics of the boards analysed. The metrics to collect are:
   *  **Win Distribution:** We want to know the result distribution of the boards analysed. In short we want to know the number of white checkmates, black checkmates and no checkmate boards.
   *  **Conquering Pieces:** We want to know the number of checkmates made by each type of piece. If a checkmate was made by more than one pieces, we count one to each of the pieces attacking the king.
   *  **Invalid Boards:** We want to know how many of the boards showed an invalid board. A board is invalid if any piece is in a non-existent square, both kings are being attacked at the same time, if one color doesn't have a king or if there are more than one king of the same color. *Bonus: update the checkmate detector to return an error if the board is invalid*.
    1. Implement the statistics using an in-memory store strategy.
    2. Implement the statistics using a Postgress (or other SQL database) store strategy.
    3. Implement the statistics using a Redis store strategy.
    4. Implement the statistics using a Cassandra store strategy.
    5. Implement the statistics using a MongoDB store strategy.
    6. Implement the statistics using a Neo4J store strategy.
    **Note:** The web server should be able to configure each of the store strategies by configuration
    **Note:** The data model for each strategy (including in-memory) must be documented. 
    

