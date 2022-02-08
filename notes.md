### Notes
Some of the design choices in this implementation can be improved in the future:

1. The implementation for the ``moveTo`` method is duplicated in all the pieces as it's the very same logic. It can be improved by using composition + **strategy** pattern and have the `moveTo` logic in the main `Piece` class.
2. This implementation went for a simple design, as the focus was doing a first implementation of the problem. I focused on getting the algorithm right and checking the difficulty and length of the challenge. More attention could be payed to improving the algorithm themselves so they are easier to understand and remove most of the `ifs`.
3. The `Type` enum is not adding any value except for serialization / deserialization of the pieces. It could be removed in the future. 