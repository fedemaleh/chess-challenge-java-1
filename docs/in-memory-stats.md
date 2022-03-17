# In Memory Statistics Store
This document explains the strategy to store the statistics requested in the [challenge](../README.md) as an In-Memory Store.

## Store Schema
The store consists of 2 dictionaries:

- **Winners**: It contains the number of checkmates registered for each color. There is a *NONE* value that it's used when there was no checkmate.
- **Pieces**: It contains the number of checkmates made by piece type.

A `JSON` example of these stores can be:

```json
// Winners
{
  "white": 4,
  "black": 3,
  "none": 10
}
```

```json
// Pieces
{
  "bishop": 10,
  "queen": 20,
  "king": 0,
  "knight": 7,
  "pawn": 1,
  "rook": 15
}
```

Finally, the [InMemoryStatistics](../src/main/java/com/chess_challenge/java_1/statistics/repositories/inmemory/InMemoryStatistics.java) class is the repository layer representation of the [DetectorStatistics](../src/main/java/com/chess_challenge/java_1/model/DetectorStatistics.java).

### Why do we need the `InMemoryStatistics` class?
Though the class it's the same as the model one, it's needed because of different reasons:

1. Decouple the model and repository layers. We can change either the model or the repo structure without affecting the other layer.
2. Keep `DetectorStrategy` immutable. Repositories are mutable by nature, it's better to encapsulate the mutation on the repository layer and keep the model immutable. Each time the statistics are queried, the repository will create a new instance of the model class. 