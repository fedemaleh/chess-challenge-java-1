# Redis Statistics Store
This document explains the strategy to store the statistics requested in the [challenge](../README.md) as a Redis Database. 
## Store Schema
The store consists of 2 set of keys:

- **winners:$color**: It contains one key for each possible board result (`winners:white`, `winners:black`, `winners:none`). Each key contains a counter type to store the number of boards with the result.  
- **pieces:$type**: It contains one key for each possible board result (`pieces:queen`, `pieces:rook`, `pieces:knight`, `pieces:bishop`, `pieces:king`, `pieces:pawn`). Each key contains a counter type to store the number of boards with the result.

The querying strategy can be seen in the following diagram:

![Redis Schema](Redis%20Schema.png)

This schema brings the following advantages:

1. Simple model. We use simple keys and use the `INCR` command.
2. Single query to build statistics. If desired, this schema supports getting all the information with a single `MGET` command.

The main disadvantage for this command is that the `MGET` needs each needed key to be explicit.  

A second possible schema would be using 2 `HASH` key.

- **winners**: It contains one sub-key for each possible board result (`white`, `black`, `none`). Each key contains a counter type to store the number of boards with the result.
- **pieces**: It contains one sub-key for each possible board result (`queen`, `rook`, `knight`, `bishop`, `king`, `pawn`). Each key contains a counter type to store the number of boards with the result.

The querying strategy can be seen in the following diagram:

![Redis Schema - Hashes](Redis%20Schema%20-%20Hashes.png)

This schema allows for each *type* of statistics (winners, pieces) to be grouped with a more expressive data type while keeping the ability to use counters with the `HINCRBY` command.

The main disadvantage is that 2 calls to `HGETALL` command will be needed to query the stats, one for each collection.