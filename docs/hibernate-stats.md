# Hibernate Statistics Store
This document explains the strategy to store the statistics requested in the [challenge](../README.md) as a Relational Database. In particular, this store was designed to use with a `ORM` (Hibernate). 
## Store Schema
The store consists of 2 tables:

- **Winners**: It contains one row for each chess board analysed. It has an `id` and the color that won (or none).  
- **Pieces**: It contains one row for each piece that made a checkmate. It has an `id` and the piece.

The schema can be seen in the following diagram:

![Hibernate Schema](Hibernate%20Schema.png)

The schema had to comply with `Hibernate / JPA` limitations. This meant having an `ID` in each table that wasn't necessary.

In the code, the integration consists of 3 parts:

1. **Configuration**: I needed to add to the [application.yml](../src/main/resources/application.yml) file the needed configurations to use the `Postgress` database. In particular the `spring.datasource` config with the `DB` url, user and password, and the `spring.jpa` one with the information about the database (mostly the dialect). 
2. **JPA Repos**: `JPA` is a tool that handles the query creation and execution in Spring. It needs and interface that extends `JpaRepository` and Spring will create an implementation in runtime for it. One interface for each table was needed ([HibernatePiecesRepo](../src/main/java/com/chess_challenge/java_1/statistics/repositories/hibernate/HibernatePiecesRepo.java) and [HibernateWinnersRepo](../src/main/java/com/chess_challenge/java_1/statistics/repositories/hibernate/HibernateWinnersRepo.java)). 
3. **Table Mappings**: In order to map the DB to objects, one class per table entity is needed. Given `JPA` limitations, these classes cannot be immutable as JPA requires a default constructor and setters. The entity classes are [HibernatePiece](../src/main/java/com/chess_challenge/java_1/statistics/repositories/hibernate/HibernatePiece.java) and [HibernateWinner](../src/main/java/com/chess_challenge/java_1/statistics/repositories/hibernate/HibernateWinner.java).   