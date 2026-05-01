Laboratory 3
Topics: MVC Design Pattern Implementation
Console-Based Bookstore Management System
Learning Objectives

Upon completion of this laboratory exercise, students will be able to:

    Understand the MVC Pattern:
        Identify the responsibilities of Model, View, and Controller layers
        Explain the benefits of separating presentation, business logic, and data management
        Recognize how MVC promotes maintainability and testability

    Apply Separation of Concerns:
        Organize code into distinct layers with clear responsibilities
        Implement loose coupling between components
        Use interfaces to define contracts between layers

    Implement DTOs (Data Transfer Objects):
        Design DTOs to decouple data representation from entity logic
        Transform between entity objects and DTOs
        Understand when and why to use DTOs

    Design Repository Pattern:
        Implement in-memory data storage using Java Collections
        Create CRUD operations through repository classes
        Manage relationships between entities

    Build Console-Based User Interaction:
        Implement View classes for console output
        Design Controller classes to handle user actions
        Coordinate data flow between Model, View, and Controller

Project Overview
Context

You will develop a console-based bookstore management system using the Model-View-Controller (MVC) design pattern. This system will manage books, authors, and customer orders through a clean separation of concerns architecture.
MVC Architecture Overview

Model Layer:

    Contains business entities (Book, Author, Order)
    Implements Data Transfer Objects (DTOs)
    Provides data access through Repository classes
    Manages relationships and data integrity
    No dependencies on View or Controller

View Layer:

    Responsible for presenting data to the user
    Handles console output formatting
    Contains no business logic
    Receives data through DTOs from Controller

Controller Layer:

    Coordinates between Model and View
    Handles user actions and input
    Retrieves data from Model (via Repositories)
    Transforms entities to DTOs
    Passes DTOs to View for display
    Contains application flow logic

Exercise 1: Model Layer Implementation
Goal

Design and implement the Model layer containing entities, DTOs, and repositories with proper encapsulation and relationships.
Task 1.1: Define Common Interface

Create an Entity interface that all entities must implement:

Interface: Identifiable

    Define method signatures for getId() and setId(int id)
    Purpose: Provide a common contract for all entities with identifiers

Task 1.2: Implement Entity Classes

Create three entity classes that implement the Entity interface:

Class: Author

    Attributes:
        id (int) - unique identifier
        firstName (String)
        lastName (String)
        nationality (String)
    Requirements:
        Implement Identifiable interface
        Provide constructor accepting all parameters
        Implement getters and setters for all attributes
        Override toString() method for debugging

Class: Book

    Attributes:
        id (int) - unique identifier
        title (String)
        author (Author) - relationship to Author entity
        price (double)
    Requirements:
        Implement Identifiable interface
        Provide constructor accepting all parameters
        Implement getters and setters for all attributes
        Override toString() method for debugging

Class: Order

    Attributes:
        id (int) - unique identifier
        books (List) - collection of books in the order
        totalPrice (double) - calculated from books
    Requirements:
        Implement Identifiable interface
        Provide constructor that initializes empty book list
        Implement method to add books to the order
        Implement method to calculate total price from all books
        Implement getters and setters
        Override toString() method for debugging

Task 1.3: Create Data Transfer Objects (DTOs)

Design DTO classes that contain only data for presentation (no business logic):

Class: AuthorDTO

    Attributes:
        id (int)
        firstName (String)
        lastName (String)
        nationality (String)
    Requirements:
        Provide constructor accepting all parameters
        Implement only getters (DTOs are typically immutable)
        No business logic methods

Class: BookDTO

    Attributes:
        id (int)
        title (String)
        authorName (String) - combined first and last name
        price (double)
    Requirements:
        Provide constructor accepting all parameters
        Implement only getters
        Note: Contains authorName instead of Author object for simplified presentation

Class: OrderDTO

    Attributes:
        orderId (int)
        bookDTOs (List)
        totalPrice (double)
    Requirements:
        Provide constructor accepting all parameters
        Implement only getters
        Contains collection of BookDTOs, not Book entities

Task 1.4: Implement Repository Classes

Create repository classes for data access using in-memory storage:

Class: AuthorRepository

    Attributes:
        authors (Map<Integer, Author>) - stores authors by ID
        nextId (int) - for auto-generating IDs
    Methods to implement:
        addAuthor(Author author) - assigns ID and stores author
        findById(int id) - returns Author or null
        findAll() - returns List of all authors
        updateAuthor(Author author) - updates existing author
        deleteAuthor(int id) - removes author from storage

Class: BookRepository

    Attributes:
        books (Map<Integer, Book>) - stores books by ID
        nextId (int) - for auto-generating IDs
    Methods to implement:
        addBook(Book book) - assigns ID and stores book
        findById(int id) - returns Book or null
        findAll() - returns List of all books
        findByAuthor(int authorId) - returns List of books by specific author
        updateBook(Book book) - updates existing book
        deleteBook(int id) - removes book from storage

Class: OrderRepository

    Attributes:
        orders (Map<Integer, Order>) - stores orders by ID
        nextId (int) - for auto-generating IDs
    Methods to implement:
        addOrder(Order order) - assigns ID and stores order
        findById(int id) - returns Order or null
        findAll() - returns List of all orders
        updateOrder(Order order) - updates existing order
        deleteOrder(int id) - removes order from storage

Expected Behavior

After implementing the Model layer, you should be able to:

    Create Author, Book, and Order objects
    Store them in repositories with auto-generated IDs
    Retrieve objects by ID or get all objects
    Establish relationships (Book has Author, Order has Books)
    Transform entities to DTOs for presentation

Exercise 2: View Layer Implementation
Goal

Implement View classes responsible for formatting and displaying data to the console. Views should contain no business logic and work exclusively with DTOs.
Task 2.1: Create Author View

Class: AuthorView

Implement methods for displaying author information:

Method: displayAuthor(AuthorDTO author)

    Receives a single AuthorDTO
    Formats and prints author details to console

Method: displayAllAuthors(List authors)

    Receives a list of AuthorDTOs
    Prints all authors in a formatted table or list

Method: displayAuthorAddedConfirmation(AuthorDTO author)

    Displays confirmation message when author is successfully added
    Includes author's full name

Task 2.2: Create Book View

Class: BookView

Implement methods for displaying book information:

Method: displayBook(BookDTO book)

    Receives a single BookDTO
    Formats and prints book details including title, author name, and price

Method: displayAllBooks(List books)

    Receives a list of BookDTOs
    Prints all books in a formatted list

Method: displayBookAddedConfirmation(BookDTO book)

    Displays confirmation message when book is successfully added
    Includes book title and author name

Method: displayBooksByAuthor(List books, String authorName)

    Displays books filtered by a specific author
    Includes header indicating the author's name

Task 2.3: Create Order View

Class: OrderView

Implement methods for displaying order information:

Method: displayOrder(OrderDTO order)

    Receives a single OrderDTO
    Prints order ID and summary information

Method: displayOrderDetails(OrderDTO order)

    Receives an OrderDTO
    Prints detailed breakdown including:
        Order header with ID
        Each book in the order with individual prices
        Total order price

Method: displayAllOrders(List orders)

    Receives a list of OrderDTOs
    Prints summary of all orders

Method: displayOrderCreatedConfirmation(OrderDTO order)

    Displays confirmation message when order is successfully created
    Includes order ID and total price

Expected Console Outputs

Example: Display Author

=== Author Details ===
ID: 1
Name: Jose Saramago
Nationality: Portuguese

Example: Display All Books

=== Book Catalog ===
1. Ensaio sobre a Cegueira by Jose Saramago - €24.99
2. Dom Casmurro by Machado de Assis - €19.99
3. Mensagem by Fernando Pessoa - €15.50

Example: Display Order Details

=== Order Summary ===
Order ID: #O001
-------------------
Book: Ensaio sobre a Cegueira - €24.99
Book: Dom Casmurro - €19.99
-------------------
Total: €44.98

Example: Confirmation Messages

Author added: Jose Saramago
Book added: Ensaio sobre a Cegueira by Jose Saramago
Order created: #O001 Total: €44.98

Exercise 3: Controller Layer Implementation
Goal

Implement Controller classes that coordinate between Model and View, handle user actions, and manage data transformations.
Task 3.1: Create Author Controller

Class: AuthorController

    Attributes:
        authorRepository (AuthorRepository)
        authorView (AuthorView)

    Constructor:
        Initialize with repository and view instances

    Methods to implement:

Method: addAuthor(String firstName, String lastName, String nationality)

    Create new Author entity with provided data
    Save to repository using authorRepository.addAuthor()
    Convert Author entity to AuthorDTO
    Call authorView.displayAuthorAddedConfirmation() with DTO

Method: displayAuthor(int id)

    Retrieve Author from repository by ID
    Convert Author entity to AuthorDTO
    Call authorView.displayAuthor() with DTO
    Handle case where author is not found

Method: displayAllAuthors()

    Retrieve all authors from repository
    Convert each Author entity to AuthorDTO
    Create List
    Call authorView.displayAllAuthors() with DTO list

Method: convertToDTO(Author author)

    Private helper method
    Transform Author entity to AuthorDTO
    Extract and format necessary fields

Task 3.2: Create Book Controller

Class: BookController

    Attributes:
        bookRepository (BookRepository)
        authorRepository (AuthorRepository)
        bookView (BookView)

    Constructor:
        Initialize with repository and view instances

    Methods to implement:

Method: addBook(String title, int authorId, double price)

    Retrieve Author from authorRepository by authorId
    Create new Book entity with provided data and Author reference
    Save to repository using bookRepository.addBook()
    Convert Book entity to BookDTO
    Call bookView.displayBookAddedConfirmation() with DTO
    Handle case where author is not found

Method: displayBook(int id)

    Retrieve Book from repository by ID
    Convert Book entity to BookDTO
    Call bookView.displayBook() with DTO
    Handle case where book is not found

Method: displayAllBooks()

    Retrieve all books from repository
    Convert each Book entity to BookDTO
    Create List
    Call bookView.displayAllBooks() with DTO list

Method: displayBooksByAuthor(int authorId)

    Retrieve Author from repository to get name
    Retrieve books by author from bookRepository.findByAuthor()
    Convert each Book entity to BookDTO
    Call bookView.displayBooksByAuthor() with DTO list and author name

Method: convertToDTO(Book book)

    Private helper method
    Transform Book entity to BookDTO
    Extract author's full name from Author object
    Format price and other fields

Task 3.3: Create Order Controller

Class: OrderController

    Attributes:
        orderRepository (OrderRepository)
        bookRepository (BookRepository)
        orderView (OrderView)

    Constructor:
        Initialize with repository and view instances

    Methods to implement:

Method: createOrder(List bookIds)

    Create new Order entity
    For each bookId in the list:
        Retrieve Book from bookRepository
        Add Book to Order
    Calculate total price using Order's method
    Save to repository using orderRepository.addOrder()
    Convert Order entity to OrderDTO
    Call orderView.displayOrderCreatedConfirmation() with DTO
    Handle cases where books are not found

Method: displayOrder(int id)

    Retrieve Order from repository by ID
    Convert Order entity to OrderDTO
    Call orderView.displayOrderDetails() with DTO
    Handle case where order is not found

Method: displayAllOrders()

    Retrieve all orders from repository
    Convert each Order entity to OrderDTO
    Create List
    Call orderView.displayAllOrders() with DTO list

Method: convertToDTO(Order order)

    Private helper method
    Transform Order entity to OrderDTO
    Convert each Book in order to BookDTO
    Create List for OrderDTO
    Include total price

Expected Controller Behavior

Controllers should:

    Never directly output to console (delegate to View)
    Always work with repositories for data access
    Transform entities to DTOs before passing to View
    Handle null cases when entities are not found
    Coordinate multi-step operations (e.g., creating order requires retrieving books)

Exercise 4: Integration and Data Flow
Goal

Create the main application entry point that demonstrates complete MVC data flow and integration of all components.
Task 4.1: Create Main Application Class

Class: BookstoreApplication

Implement the main method that orchestrates the entire application:

Setup Phase:

    Instantiate all repositories (AuthorRepository, BookRepository, OrderRepository)
    Instantiate all views (AuthorView, BookView, OrderView)
    Instantiate all controllers with their dependencies

Demonstration Scenarios:

Implement the following sequence to demonstrate the system:

Scenario 1: Add Authors

    Add author: José Saramago, Portuguese
    Add author: Machado de Assis, Brazilian
    Add author: Fernando Pessoa, Portuguese
    Display all authors

Scenario 2: Add Books

    Add book: "Ensaio sobre a Cegueira" by José Saramago, €24.99
    Add book: "Memorial do Convento" by José Saramago, €22.50
    Add book: "Dom Casmurro" by Machado de Assis, €19.99
    Add book: "Mensagem" by Fernando Pessoa, €15.50
    Display all books

Scenario 3: Display Books by Author

    Display all books by José Saramago
    Display all books by Fernando Pessoa

Scenario 4: Create Orders

    Create order with books: "Ensaio sobre a Cegueira" and "Dom Casmurro"
    Create order with books: "Memorial do Convento" and "Mensagem"
    Display detailed information for first order

Scenario 5: Display All Orders

    Display summary of all orders

Task 4.2: Implement User Interaction

For students who complete the basic implementation early:

Create a console menu system that allows users to:

    Choose actions (add author, add book, create order, display data)
    Input data through console
    Navigate through different operations
    Exit the application

Menu Structure:

=== Bookstore Management System ===
1. Add Author
2. Add Book
3. Create Order
4. Display All Authors
5. Display All Books
6. Display All Orders
7. Display Books by Author
8. Exit
   Enter your choice:

Expected Complete Output

Running the BookstoreApplication should produce output similar to:

=== Bookstore Management System ===

Author added: Jose Saramago
Author added: Machado de Assis
Author added: Fernando Pessoa

=== All Authors ===
1. Jose Saramago (Portuguese)
2. Machado de Assis (Brazilian)
3. Fernando Pessoa (Portuguese)

Book added: Ensaio sobre a Cegueira by Jose Saramago
Book added: Memorial do Convento by Jose Saramago
Book added: Dom Casmurro by Machado de Assis
Book added: Mensagem by Fernando Pessoa

=== Book Catalog ===
1. Ensaio sobre a Cegueira by Jose Saramago - €24.99
2. Memorial do Convento by Jose Saramago - €22.50
3. Dom Casmurro by Machado de Assis - €19.99
4. Mensagem by Fernando Pessoa - €15.50

=== Books by Jose Saramago ===
1. Ensaio sobre a Cegueira - €24.99
2. Memorial do Convento - €22.50

Order created: #O001 Total: €44.98
Order created: #O002 Total: €38.00

=== Order Details: #O001 ===
Book: Ensaio sobre a Cegueira - €24.99
Book: Dom Casmurro - €19.99
-------------------
Total: €44.98

=== All Orders ===
Order #O001 - Total: €44.98
Order #O002 - Total: €38.00

Package Organization

Organize your code according to the following structure:

pt.ipp.estg.bookstore
│
├── model
│   ├── entities
│   │   ├── Identifiable.java (interface)
│   │   ├── Author.java
│   │   ├── Book.java
│   │   └── Order.java
│   │
│   ├── dto
│   │   ├── AuthorDTO.java
│   │   ├── BookDTO.java
│   │   └── OrderDTO.java
│   │
│   └── repository
│       ├── AuthorRepository.java
│       ├── BookRepository.java
│       └── OrderRepository.java
│
├── view
│   ├── AuthorView.java
│   ├── BookView.java
│   └── OrderView.java
│
├── controller
│   ├── AuthorController.java
│   ├── BookController.java
│   └── OrderController.java
│
└── BookstoreApplication.java (main class)

Deliverables

Place all Java files in a single folder named Lab_YourName_StudentID and deliver it in moodle in a .zip file under Lab 3.
Additional Resources

    MVC Pattern: https://en.wikipedia.org/wiki/Model–view–controller
    Java Collections: https://docs.oracle.com/javase/tutorial/collections/
    DTO Pattern: https://www.baeldung.com/java-dto-pattern
    Repository Pattern: https://www.baeldung.com/java-repository-pattern

Tips for Success

    Start with Model layer - establish your data structures first
    Keep layers independent - avoid circular dependencies
    Use meaningful names - class and method names should be self-explanatory
    Test incrementally - verify each layer works before moving to the next
    Think about data flow - trace how data moves from Model → Controller → View
    Keep Views simple - only formatting and output, no logic
    Controllers coordinate - they orchestrate operations but don't do the work themselves

Good luck with your laboratory work!
