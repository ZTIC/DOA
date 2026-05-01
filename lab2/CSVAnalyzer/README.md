Exercise 4: CSV Reading and OOP integration
Goal

Parse CSV files containing author and book data, create object structures, and perform statistical analysis.
Task Description

Develop a Java program named CSVAnalyzer.java that performs the following operations:

    CSV File Reading
        Read data from two CSV files:
            authors.csv: Contains author information (firstName, lastName, nif, age)
            books.csv: Contains book information (id, title, authorNif, price, genre)
        Parse the CSV files and create arrays (or collections) of Author and Book objects
        Associate each Book with its corresponding Author using the NIF field

    Analysis Functions

        Implement the following analysis methods:

        a) Find Oldest Author
            Identify and return the author with the maximum age

        b) Find Author with Most Books
            Count the number of books per author
            Return the author who has written the most books

        c) Find Most Popular Genre
            Count occurrences of each genre
            Return the genre that appears most frequently

        d) Find Most Expensive Book
            Identify and return the book with the highest price

    Output Results
        Print the results of all four analysis functions to the console in a clear format

Expected Console Output

Oldest author: Jose Saramago (101 years)
Author with most books: Fernando Pessoa
Most popular genre: Novel
Most expensive book: Os Maias (€30.00)
