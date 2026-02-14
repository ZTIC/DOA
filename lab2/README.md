Lab 2
Topics: File Manipulation, Matrix Exploration, CSV Reading, and Object-Oriented Programming
Learning Objectives

Upon completion of this laboratory exercise, students will be able to:

    Perform file input/output operations using Java's file handling APIs
    Implement algorithms involving prime number detection and matrix generation
    Design and implement object-oriented structures with proper encapsulation
    Parse and process CSV files to create and manipulate object collections
    Perform data analysis and aggregation on structured datasets

Pre-Lab Setup

Ensure the following before starting:

    Java Development Kit (JDK) installed (version 11 or higher)
    IDE or Text Editor configured and ready
    Working directory created for all lab files
    Test files prepared (you will create some during the lab; CSV files will be provided or created by you)

Exercise 1: File Manipulation
Goal

Create methods to write student information to a text file and read it back to the console.
Task Description

Develop a Java program named FileManipulation.java that implements the following functionality:

    Method 1: Write to File
        Create a method that generates a text file named student_info.txt
        The file should contain:
            The student's name on the first line
            A list of foods the student likes on the second line
        Save the file to the working directory

    Method 2: Read from File
        Create a method that loads the contents of student_info.txt
        Print the entire file content to the console
        Handle the case where the file does not exist

Expected Console Output

Name: Maria Silva
Foods I like: Sushi, Pasta, Ice Cream

Exercise 2: Matrix and Prime Numbers
Goal

Generate a matrix populated with prime numbers and save it to a file based on dimensions read from an input file.
Task Description

Develop a Java program named PrimeMatrix.java that implements the following:

    Method 1: Prime Number Detection
        Create a method isPrime(int n) that returns true if a number is prime, false otherwise
        The method should handle edge cases (numbers less than 2)

    Method 2: Prime Matrix Generation
        Create a method generatePrimeMatrix(int rows, int cols) that:
            Calculates the first N prime numbers (where N = rows × cols)
            Arranges them in a matrix with the specified dimensions
            Returns the matrix as a 2D array

    Method 3: File-Based Matrix Creation
        Create a method that:
            Reads a file matrix_config.txt containing two integers: number of rows and number of columns
            Generates the prime matrix using the dimensions from the file
            Saves the resulting matrix to a new file prime_matrix.txt
            Each row of the matrix should be on a separate line with numbers separated by spaces

    Display Method
        Create a method to print the matrix to the console in a formatted manner

Input File Format (matrix_config.txt)

3
3

Expected Console Output

2 3 5
7 11 13
17 19 23

Expected File Output (prime_matrix.txt)

2 3 5
7 11 13
17 19 23

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

Exercise 5: Object Composition and Relationships
Goal

Understand and implement composition by creating classes that contain other objects as attributes.
Task Description

Build upon Exercise 3 by creating new classes that use composition:

    Class: Address
        Attributes:
            street (String)
            city (String)
            postalCode (String)
            country (String)
        Implement:
            Constructor to initialize all attributes
            Getters and setters for all attributes
            A method getFullAddress() that returns the complete address as a formatted string
            Override toString() method

    Class: Person
        Attributes:
            name (String)
            dateOfBirth (String)
            address (Address object) - demonstrates composition
            phoneNumber (String)
        Implement:
            Constructor accepting all parameters including an Address object
            Getters and setters for all attributes
            A method relocate(Address newAddress) that changes the person's address
            Override toString() to display person info including address

    Class: BankAccount
        Attributes:
            accountNumber (String) - immutable
            accountHolder (Person object) - demonstrates composition
            balance (double)
            accountType (String) - e.g., "Savings", "Checking"
        Implement:
            Constructor to initialize all attributes
            Getters for all attributes
            Setter for accountType only (balance should be modified through methods)
            Method deposit(double amount) - adds to balance if amount > 0
            Method withdraw(double amount) - subtracts from balance if sufficient funds exist
            Method getAccountSummary() - returns formatted string with account details

    Main Program: CompositionTest.java
        Create:
            An Address: "Rua das Flores 123", "Lisboa", "1200-001", "Portugal"
            A Person: "João Santos", "15/03/1990", the address created above, "+351 912345678"
            A BankAccount: Account "PT50000123456789", holder is João Santos, balance 1500.00, type "Savings"
        Demonstrate:
            Printing complete account information (should include person and address details)
            Depositing 500.00
            Withdrawing 300.00
            Attempting to withdraw 2000.00 (should fail)
            Relocating the person to a new address
            Printing updated account information showing new address

Expected Console Output

Account Summary:
Account Number: PT50000123456789
Account Type: Savings
Balance: €1500.00

Account Holder: João Santos
Date of Birth: 15/03/1990
Phone: +351 912345678
Address: Rua das Flores 123, Lisboa 1200-001, Portugal

Depositing €500.00... Success!
New Balance: €2000.00

Withdrawing €300.00... Success!
New Balance: €1700.00

Attempting to withdraw €2000.00... Failed: Insufficient funds

Relocating account holder...
New Address: Avenida da Liberdade 456, Porto 4000-001, Portugal

Updated Account Summary:
Account Holder Address: Avenida da Liberdade 456, Porto 4000-001, Portugal

Deliverables

Place all Java files in a single folder named Lab_YourName_StudentID and deliver it in moodle in a .zip file under Lab 2.
Additional Resources

    Java File I/O: https://docs.oracle.com/javase/tutorial/essential/io/
    Java Collections: https://docs.oracle.com/javase/tutorial/collections/
    CSV Parsing in Java: Consider using String.split() or libraries like Apache Commons CSV
    Java OOP Tutorial: https://docs.oracle.com/javase/tutorial/java/concepts/

Good luck with your laboratory work!
