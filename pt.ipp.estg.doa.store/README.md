Project 1
DeOroAtelier Store (DOA Store) Management System
Table of Contents

    Project Overview
    Learning Objectives
    System Entities and Attributes
    UML Design Requirements
    Required Operations (CRUD)
    Business Rules and Constraints
    File Persistence Strategy
    Package Organization
    Submission Requirements

Project Overview
Context

DeOroAtelier is a boutique jewelry store specializing in handcrafted pieces including necklaces, earrings, and rings. The store requires a management system to handle inventory, employee information, customer data, orders, and payments. As the store values craftsmanship over technology infrastructure, they require a lightweight solution that does not depend on database systems.
Goal

Develop a console-based Java application that manages the core operations of DeOroAtelier Store. The system must demonstrate proficiency in Object-Oriented Programming principles including inheritance, interfaces, generics, encapsulation, and information hiding. All data persistence must be implemented using CSV files and Java Collections Framework.
Scope

The system should support:

    Employee management (salespeople and managers)
    Jewelry inventory control with specialized attributes per jewelry type
    Customer relationship management
    Order processing and tracking
    Payment recording and association with orders
    Data persistence through CSV files
    Complete CRUD operations for all entities

Learning Objectives

Upon completion of this project, students will demonstrate ability to:

    Apply OOP Principles:
        Design and implement class hierarchies using inheritance
        Define and implement interfaces for common behaviors
        Apply encapsulation and information hiding
        Utilize generics for type-safe collections

    Design Software Architecture:
        Create well-structured package organization
        Define clear relationships between entities (composition, aggregation, association)
        Implement business logic with appropriate constraints

    Implement Data Persistence:
        Parse and generate CSV files
        Manage data consistency across file operations
        Handle file I/O exceptions appropriately

    Utilize Java Collections:
        Select appropriate collection types (ArrayList, HashMap, etc.)
        Implement searching, filtering, and sorting operations
        Manage object relationships through collections

System Entities and Attributes
1. Employee Entity

Base Class: Employee
Attribute 	Type 	Description 	Constraints
employeeId 	int 	Unique identifier 	Immutable, auto-generated
name 	String 	Full name 	Required, non-empty
nif 	String 	Tax identification number 	Required, unique, 9 digits
hireDate 	LocalDate 	Date of employment 	Required, cannot be future date
salary 	double 	Monthly salary 	Required, > 0

Derived Classes:

    Salesperson (extends Employee)
        Additional attributes:
            commissionRate (double): Percentage of sales (0-100)
            totalSales (double): Cumulative sales amount

    Manager (extends Employee)
        Additional attributes:
            department (String): Department managed (e.g., "Sales", "Operations")
            bonus (double): Annual performance bonus

2. Jewelry Entity

Base Class: Jewelry
Attribute 	Type 	Description 	Constraints
jewelryId 	int 	Unique identifier 	Immutable, auto-generated
name 	String 	Product name 	Required, non-empty
type 	JewelryType 	Enum: NECKLACE, EARRING, RING 	Required
material 	String 	Material (gold, silver, platinum) 	Required
weight 	double 	Weight in grams 	Required, > 0
price 	double 	Unit price in euros 	Required, > 0
stock 	int 	Available quantity 	Required, ≥ 0
category 	Category 	Enum: LUXURY, CASUAL, BRIDAL 	Required

Derived Classes:

    Necklace (extends Jewelry)
        Additional attributes:
            length (double): Length in centimeters

    Earring (extends Jewelry)
        Additional attributes:
            claspType (String): Type of clasp (e.g., "Stud", "Hook", "Leverback")

    Ring (extends Jewelry)
        Additional attributes:
            size (int): Ring size (European standard, 10-30)

3. Customer Entity

Class: Customer
Attribute 	Type 	Description 	Constraints
customerId 	int 	Unique identifier 	Immutable, auto-generated
name 	String 	Full name 	Required, non-empty
nif 	String 	Tax identification number 	Required, unique, 9 digits
email 	String 	Email address 	Required, valid format
phone 	String 	Phone number 	Required, 9 digits
address 	String 	Full address 	Required
4. Order Entity

Class: Order
Attribute 	Type 	Description 	Constraints
orderId 	int 	Unique identifier 	Immutable, auto-generated
customer 	Customer 	Customer who placed order 	Required, composition
orderDate 	LocalDate 	Date order was placed 	Required, auto-set
jewelryItems 	List 	List of jewelry and quantities 	Required, non-empty
totalAmount 	double 	Total order value 	Calculated field
status 	OrderStatus 	Enum: PENDING, ACCEPTED, DELIVERED, CANCELED 	Required, default PENDING

Helper Class: OrderItem
Attribute 	Type 	Description 	Constraints
jewelry 	Jewelry 	Jewelry piece ordered 	Required
quantity 	int 	Number of units 	Required, > 0
subtotal 	double 	jewelry.price × quantity 	Calculated field
5. Payment Entity

Class: Payment
Attribute 	Type 	Description 	Constraints
paymentId 	int 	Unique identifier 	Immutable, auto-generated
order 	Order 	Associated order 	Required, composition
amount 	double 	Payment amount 	Required, > 0
paymentDate 	LocalDate 	Date of payment 	Required, auto-set
paymentMethod 	PaymentMethod 	Enum: CREDIT_CARD, BANK_TRANSFER, CASH 	Required
UML Design Requirements
Class Diagram Overview

Students must design a comprehensive UML class diagram that includes all entities, their relationships, and key methods. The diagram should clearly show:
1. Inheritance Hierarchies

Employee Hierarchy:

Employee (abstract or concrete base class)
├── Salesperson
└── Manager

Jewelry Hierarchy:

Jewelry (abstract or concrete base class)
├── Necklace
├── Earring
└── Ring

2. Class Relationships
   Relationship Type 	From 	To 	Multiplicity 	Description
   Composition 	Order 	Customer 	1 to 1 	Order contains a Customer
   Composition 	Order 	OrderItem 	1 to many 	Order contains OrderItems
   Association 	OrderItem 	Jewelry 	many to 1 	OrderItem references Jewelry
   Composition 	Payment 	Order 	many to 1 	Payment is linked to Order
   Inheritance 	Salesperson 	Employee 	is-a 	Salesperson extends Employee
   Inheritance 	Manager 	Employee 	is-a 	Manager extends Employee
   Inheritance 	Necklace 	Jewelry 	is-a 	Necklace extends Jewelry
   Inheritance 	Earring 	Jewelry 	is-a 	Earring extends Jewelry
   Inheritance 	Ring 	Jewelry 	is-a 	Ring extends Jewelry
3. Interfaces (Recommended)

Students should define and implement appropriate interfaces:

    Persistable Interface:
        Methods: toCSV(), fromCSV(String csvLine)
        Purpose: Standardize CSV serialization/deserialization

    Searchable Interface (using Generics):
        Methods: findById(int id), findByName(String name)
        Purpose: Provide search capabilities across entities

    Calculable Interface:
        Methods: calculateTotal()
        Purpose: Define calculation behavior for entities with totals

4. Enumerations

Define the following enums:

    JewelryType: NECKLACE, EARRING, RING
    Category: LUXURY, CASUAL, BRIDAL
    OrderStatus: PENDING, ACCEPTED, DELIVERED, CANCELED
    PaymentMethod: CREDIT_CARD, BANK_TRANSFER, CASH

5. UML Class Structure Template

For each class in the diagram, include:

Class Header:

    Class name
    Stereotypes (if applicable: <>, <>, <>)

Attributes Section:

    Visibility (-, +, #)
    Attribute name
    Data type
    Constraints (if relevant)

Methods Section:

    Visibility
    Method name
    Parameters with types
    Return type

Example Format:

┌─────────────────────────────────┐
│        Employee                 │
├─────────────────────────────────┤
│ - employeeId: int               │
│ - name: String                  │
│ - nif: String                   │
│ - hireDate: LocalDate           │
│ - salary: double                │
├─────────────────────────────────┤
│ + Employee(...)                 │
│ + getEmployeeId(): int          │
│ + getName(): String             │
│ + setName(String): void         │
│ + calculateMonthlyPay(): double │
│ + toString(): String            │
└─────────────────────────────────┘

Required Operations (CRUD)
Employee Management

Create:

    Add new Salesperson with commission rate
    Add new Manager with department and bonus

Read:

    List all employees
    Find employee by ID
    Find employees by name
    Filter employees by type (Salesperson/Manager)
    Calculate total payroll (sum of all salaries)

Update:

    Update employee salary
    Update salesperson commission rate
    Update manager bonus
    Update employee contact information

Delete:

    Remove employee by ID
    Validation: Cannot delete if employee has associated orders (for salespeople)

Jewelry Management

Create:

    Add new Necklace with length
    Add new Earring with clasp type
    Add new Ring with size

Read:

    List all jewelry items
    Find jewelry by ID
    Find jewelry by name
    Filter jewelry by type (Necklace/Earring/Ring)
    Filter jewelry by category (Luxury/Casual/Bridal)
    Filter jewelry by material
    Find jewelry in stock (stock > 0)
    Find low stock items (stock < threshold)

Update:

    Update jewelry price
    Update jewelry stock quantity
    Update jewelry details (material, weight, etc.)

Delete:

    Remove jewelry by ID
    Validation: Cannot delete if jewelry is referenced in any order

Customer Management

Create:

    Add new customer with all details

Read:

    List all customers
    Find customer by ID
    Find customer by NIF
    Find customer by email
    Search customers by name

Update:

    Update customer contact information
    Update customer address

Delete:

    Remove customer by ID
    Validation: Cannot delete if customer has associated orders

Order Management

Create:

    Create new order for a customer
    Add jewelry items to order with quantities
    Validate stock availability before order creation
    Automatically calculate total amount
    Set status to PENDING by default

Read:

    List all orders
    Find order by ID
    Find orders by customer
    Find orders by status
    Find orders by date range
    Calculate total revenue (sum of all delivered orders)

Update:

    Update order status (PENDING → ACCEPTED → DELIVERED or CANCELED)
    Add items to pending order
    Remove items from pending order
    Validation: Cannot modify DELIVERED or CANCELED orders

Delete:

    Cancel order (sets status to CANCELED, restores stock)
    Validation: Cannot delete DELIVERED orders

Payment Management

Create:

    Add payment for an order
    Validate payment amount
    Record payment method and date

Read:

    List all payments
    Find payment by ID
    Find payments by order
    Find payments by payment method
    Find payments by date range
    Calculate total payments received

Update:

    Update payment method (if not yet processed)

Delete:

    Remove payment record
    Validation: Consider business rules for payment deletion

Business Rules and Constraints
General Rules

    Unique Identifiers:
        All IDs (employeeId, jewelryId, customerId, orderId, paymentId) must be unique and auto-generated
        Implement auto-increment mechanism (track highest ID per entity)

    NIF Validation:
        Employee and Customer NIFs must be exactly 9 digits
        NIFs must be unique across all employees and across all customers
        NIF must contain only numeric characters

    Date Constraints:
        Hire dates cannot be in the future
        Order dates and payment dates are automatically set to current date
        Payment date cannot be before order date

Employee Rules

    Salary Constraints:
        Salary must be greater than 0
        Managers typically have higher base salaries than salespeople

    Commission:
        Salesperson commission rate must be between 0 and 100 (percentage)
        Commission is calculated based on total sales

Jewelry Rules

    Stock Management:
        Stock quantity cannot be negative
        When order is placed, stock must be sufficient for requested quantity
        When order is delivered, stock is permanently reduced
        When order is canceled, stock is restored

    Pricing:
        Price must be greater than 0
        Weight must be greater than 0

    Ring Size:
        Ring size must be between 10 and 30 (European standard)

    Jewelry Type Validation:
        Necklaces must have length > 0
        Earrings must have a clasp type defined
        Rings must have a valid size

Order Rules

    Order Creation:
        Order must have at least one jewelry item
        Stock must be checked and reserved when order status changes to ACCEPTED
        Total amount is automatically calculated from OrderItems

    Order Status Transitions:
        Valid transitions:
            PENDING → ACCEPTED
            PENDING → CANCELED
            ACCEPTED → DELIVERED
            ACCEPTED → CANCELED
        Invalid transitions:
            DELIVERED → any other status (orders are final)
            CANCELED → any other status (canceled orders cannot be reactivated)

    Stock Deduction:
        Stock is reduced when order status changes to DELIVERED
        Stock is restored when order status changes to CANCELED
        Stock is reserved (validated but not yet deducted) when status is ACCEPTED

Payment Rules

    Payment Amount:
        Payment amount must be greater than 0
        Total payments for an order should not exceed order total amount
        Multiple partial payments are allowed for one order

    Payment Method:
        Must be one of: CREDIT_CARD, BANK_TRANSFER, CASH

Data Integrity Rules

    Referential Integrity:
        Cannot delete a customer who has orders
        Cannot delete jewelry that appears in any order
        Cannot delete an order that has associated payments

    Cascade Operations:
        When an order is deleted/canceled, consider what happens to payments
        Define clear cascading behavior in documentation

File Persistence Strategy
Overview

All data must be persisted using CSV (Comma-Separated Values) files. Each entity type should have its own CSV file. The system must load data at startup and save data after modifications.
CSV File Structure
employees.csv

employeeId,employeeType,name,nif,hireDate,salary,additionalField1,additionalField2
1,SALESPERSON,Maria Silva,123456789,2020-01-15,2500.00,5.5,15000.00
2,MANAGER,João Santos,987654321,2018-06-01,4500.00,Sales,3000.00

Format Notes:

    employeeType: SALESPERSON or MANAGER
    For SALESPERSON: additionalField1 = commissionRate, additionalField2 = totalSales
    For MANAGER: additionalField1 = department, additionalField2 = bonus

jewelry.csv

jewelryId,jewelryType,name,material,weight,price,stock,category,additionalField
1,NECKLACE,Golden Chain,Gold,15.5,450.00,10,LUXURY,50.0
2,EARRING,Silver Studs,Silver,3.2,120.00,25,CASUAL,Stud
3,RING,Diamond Ring,Platinum,8.0,2500.00,5,BRIDAL,18

Format Notes:

    jewelryType: NECKLACE, EARRING, or RING
    For NECKLACE: additionalField = length
    For EARRING: additionalField = claspType
    For RING: additionalField = size

customers.csv

customerId,name,nif,email,phone,address
1,Ana Costa,111222333,ana.costa@email.com,912345678,Rua das Flores 123, Lisboa
2,Pedro Sousa,444555666,pedro.sousa@email.com,923456789,Avenida Central 45, Porto

orders.csv

orderId,customerId,orderDate,totalAmount,status
1,1,2024-11-01,570.00,DELIVERED
2,2,2024-11-15,2500.00,PENDING

order_items.csv

orderItemId,orderId,jewelryId,quantity,subtotal
1,1,1,1,450.00
2,1,2,1,120.00
3,2,3,1,2500.00

Note: This is a separate file to handle the one-to-many relationship between orders and jewelry items.
payments.csv

paymentId,orderId,amount,paymentDate,paymentMethod
1,1,570.00,2024-11-01,CREDIT_CARD
2,2,1000.00,2024-11-15,BANK_TRANSFER
3,2,1500.00,2024-11-20,BANK_TRANSFER

CSV Operations Requirements
Loading Data (at application startup)

    Read all CSV files in the following order:
        employees.csv
        jewelry.csv
        customers.csv
        orders.csv
        order_items.csv (to populate Order.jewelryItems)
        payments.csv

    Parse each line:
        Split by comma
        Convert strings to appropriate types (int, double, LocalDate, enums)
        Create objects and add to collections

    Establish relationships:
        Link orders to customers
        Link order items to jewelry pieces
        Link payments to orders

    Track highest IDs for auto-increment

Saving Data (after modifications)

    Convert objects to CSV strings using toCSV() method
    Write to corresponding CSV files
    Ensure data consistency (all files are updated atomically)

Error Handling

    Handle FileNotFoundException if CSV files don't exist (create default empty files)
    Handle IOException during read/write operations
    Handle parsing errors (invalid data format)
    Provide meaningful error messages

Recommended Utility Class

Create a CSVUtil class in the utils package with methods:

    readCSV(String filename): List<String>
    writeCSV(String filename, List<String> lines): void
    parseDate(String dateStr): LocalDate
    formatDate(LocalDate date): String

Package Organization

Students must organize their code into the following package structure:

pt.ipp.estg.doa.store
│
├── employees
│   ├── Employee.java
│   ├── Salesperson.java
│   ├── Manager.java
│   └── EmployeeManager.java (CRUD operations)
│
├── jewelry
│   ├── Jewelry.java
│   ├── Necklace.java
│   ├── Earring.java
│   ├── Ring.java
│   ├── JewelryType.java (enum)
│   ├── Category.java (enum)
│   └── JewelryManager.java (CRUD operations)
│
├── customers
│   ├── Customer.java
│   └── CustomerManager.java (CRUD operations)
│
├── orders
│   ├── Order.java
│   ├── OrderItem.java
│   ├── OrderStatus.java (enum)
│   └── OrderManager.java (CRUD operations)
│
├── payments
│   ├── Payment.java
│   ├── PaymentMethod.java (enum)
│   └── PaymentManager.java (CRUD operations)
│
├── utils
│   ├── CSVUtil.java
│   ├── ValidationUtil.java
│   ├── Persistable.java (interface)
│   └── Searchable.java (interface)
│
└── Main.java (entry point with console menu)

Package Descriptions

    employees: All employee-related classes and management logic
    jewelry: Jewelry hierarchy and inventory management
    customers: Customer entity and management
    orders: Order processing, order items, and status management
    payments: Payment processing and method handling
    utils: Utility classes, interfaces, and helper methods
    Main: Application entry point with user interface (console menu)

Submission Requirements

All code must be delivered in for of Github repository
Repository Setup

    GitHub Repository:
        Create a GitHub repository under the following organization: https://github.com/ULHT-DOA. If you don't have access, ask to the teacher to provide it to you.
        Repository name: doa-store-management-<studentID>
        Add instructor as collaborator: rfgsantos-ulht

    Branch Strategy:
        Main branch: main or master
        Development branch: develop
        Submit final work in branch: submission

    Commit History:
        Regular commits throughout development (minimum 15 commits)
        Meaningful commit messages
        Demonstrate progressive development

Required Files
1. Source Code

   All .java files organized in proper package structure
   No .class files in repository

2. CSV Data Files

   Sample data files for testing (at least 5 entries per entity)
   Place in a data/ folder at project root

3. Documentation

README.md must include:

    Project title and student information (name, student ID, email)
    Brief project description
    How to compile and run the application
    Package structure explanation
    Key features implemented
    Assumptions made
    Known limitations or issues
    References used

4. .gitignore

Include a .gitignore file to exclude:

*.class
*.jar
.DS_Store
.idea/
*.iml
target/
out/
bin/

Submission Process

    Push final code to submission branch
    Submit via Moodle:
        Repository URL
        Commit hash of final submission
        PDF export of README (for backup)

Recommended Approach

Week 1-2:

    Design UML class diagram
    Set up project structure and packages
    Implement base classes (Employee, Jewelry)

Week 3-4:

    Implement derived classes
    Create manager classes with CRUD operations
    Implement CSV reading/writing

Week 5-6:

    Implement business logic and validation
    Create console menu interface
    Test all functionality
    Complete documentation

Additional Resources

    Java Documentation: https://docs.oracle.com/en/java/
    CSV Handling in Java: Use String.split() or BufferedReader
    UML Tools:
        draw.io: https://app.diagrams.net/
        Lucidchart: https://www.lucidchart.com/
        PlantUML: https://plantuml.com/
    Git Tutorial: https://git-scm.com/docs
    MVC Pattern Architecture: https://www.geeksforgeeks.org/system-design/mvc-design-pattern/

Good luck with your project!
