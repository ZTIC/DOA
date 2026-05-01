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
