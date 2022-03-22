# Jooq Statistics Store
This document explains the strategy to store the statistics requested in the [challenge](../README.md) as a Relational Database. In particular, this store was designed to use with a `Query Builder` (Jooq). 
## Store Schema
The store consists of 2 tables:

- **Winners**: It contains one row for each possible winner (White, Black, None) and a counter for the matches.  
- **Pieces**: It contains one row for each possible pieces and a counter for the matches.

The schema can be seen in the following diagram:

![Jooq Schema](Jooq%20Schema.png)

The Query Builder Pattern brings 2 advantages:
1. It brings the flexibility to create any query in a declaratively (similar to PySpark) without losing the type checks an ORM brings.
2. Jooq brings a code generation feature to create all the database objects in the application.
 
In the code, the integration consists of just configuration: Create a `jooq` [gradle](../build.gradle) task setting all the DB configurations for code generation and the connection to the database.  

You can find all the code generated classes in the [jooq.db](../src/main/java/com/chess_challenge/java_1/statistics/repositories/jooq/db/) package.