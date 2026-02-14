Exercise 3: Object-Oriented Programming
Goal

Design and implement object-oriented classes representing authors and books with proper encapsulation.
Task Description

Create a Java program with the following class structure:

    Class: Author
        Attributes:
            firstName (String)
            lastName (String)
            nif (String) - National Identification Number
            age (int)
        Implement:
            Constructor(s) to initialize all attributes
            Getters for all attributes
            Setters for mutable attributes (age can change; NIF should be immutable)
            A method to return the full name as a single string

    Class: Book
        Attributes:
            id (int) - immutable unique identifier
            title (String)
            author (Author object)
            price (double)
            genre (String)
        Implement:
            Constructor(s) to initialize all attributes
            Getters for all attributes
            Setters for mutable attributes (id should be immutable)
            A method to return a formatted string representation of the book

    Main Program
        Create a class BookManagement.java with a main method
        Instantiate the following objects:
            Author: José Saramago, NIF: 290116422, Age: 101
            Book: ID: 1, Title: "Ensaio sobre a Cegueira", Author: José Saramago, Price: 25.50, Genre: "Novel"
        Print information about the book to the console using the book's formatted output method

Expected Console Output

Book: Ensaio sobre a Cegueira by Jose Saramago

