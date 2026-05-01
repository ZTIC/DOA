# DeOroAtelier Store (DOA Store) Management System

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17+-orange)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-24.0+-blue)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

A modern, production-ready REST API for jewelry store management, evolved from a console-based Java application to a professional Spring Boot microservice with database persistence.

## 📋 Table of Contents
- [Overview](#overview)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database Design](#database-design)
- [Business Rules](#business-rules)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

The DeOroAtelier Store (DOA Store) Management System is a comprehensive solution for managing jewelry retail operations. This project represents a complete modernization from a CSV-based console application to a professional, production-ready architecture.

### Key Features
- **Complete CRUD operations** for employees, jewelry, customers, orders, and payments
- **Inheritance hierarchies** for employees (Salesperson/Manager) and jewelry (Necklace/Earring/Ring)
- **Complex business rules** for order processing, inventory management, and payment handling
- **RESTful API** with proper HTTP semantics and status codes
- **Comprehensive validation** at both API and business logic layers
- **Global exception handling** with structured error responses
- **Containerized database** using Docker Compose

### Evolution from Project 1

| Aspect | Project 1 | Final Project |
|--------|-----------|---------------|
| Persistence | CSV files | MySQL/PostgreSQL via JPA |
| Architecture | Monolithic with manager classes | Layered (Controller-Service-Repository) |
| Interface | Console-based | REST API with JSON |
| Data Access | Manual file I/O | Spring Data JPA repositories |
| Deployment | Local execution | Docker Compose for database |
| Validation | Manual in code | Jakarta Bean Validation |
| Error Handling | Return codes/messages | Global exception handler |
| Testing | Manual console testing | API testing with Postman/cURL |

## 🛠 Technology Stack

### Core Framework
- **Spring Boot 3.2.x** - Application framework and dependency management
- **Spring MVC** - REST API implementation
- **Spring Data JPA** - Database persistence and ORM
- **Spring Boot Validation** - Bean validation integration

### Database
- **MySQL 8.0** or **PostgreSQL 15** - Relational database (configurable)
- **Hibernate** - JPA implementation
- **Docker Compose** - Containerized database deployment

### Languages & Tools
- **Java 17+** - Primary programming language
- **Maven** - Build tool and dependency management
- **Jackson** - JSON processing
- **Lombok** (optional) - Boilerplate code reduction

## 🏗 Architecture

### Layered Architecture Pattern

```
pt.ipp.estg.doa.store
├── controller           // REST API endpoints
├── service             // Business logic layer
├── repository          // Data access layer  
├── model
│   ├── entity          // JPA entities
│   └── dto             // Data Transfer Objects
│       ├── request     // Incoming DTOs with validation
│       └── response    // Outgoing DTOs
├── exception           // Custom exceptions & handlers
├── config              // Configuration classes
└── DoaStoreApplication.java
```

### Layer Responsibilities

#### Controller Layer
- Handles HTTP requests/responses
- Input validation using `@Valid`
- Returns appropriate HTTP status codes
- No business logic

#### Service Layer
- Contains all business logic
- Transaction management with `@Transactional`
- Orchestrates repository operations
- Throws custom exceptions

#### Repository Layer
- Extends `JpaRepository` interfaces
- Custom query methods
- No business logic

#### DTO Layer
- Decouples API from entity model
- Request validation annotations
- Response formatting

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Docker and Docker Compose
- Maven 3.8+
- Postman (for API testing)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/ZTIC/DOA.git
   cd doa-store
   ```

2. **Start the database container**
   ```bash
   docker-compose up -d
   ```

3. **Configure database connection** (if needed)

   Edit `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/doa_store
       username: doa_user
       password: secure_password
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
   ```

4. **Build the application**
   ```bash
   mvn clean package
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   or
   ```bash
   java -jar target/doa-store-0.0.1-SNAPSHOT.jar
   ```

6. **Verify the application is running**
   ```bash
   curl http://localhost:8080/api/health
   ```

### Docker Commands

```bash
# Start database
docker-compose up -d

# View logs
docker-compose logs -f

# Stop database
docker-compose down

# Reset database (removes volumes)
docker-compose down -v
```

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Employee Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/employees` | List all employees |
| GET | `/employees/{id}` | Get employee by ID |
| GET | `/employees/nif/{nif}` | Find by NIF |
| POST | `/employees/salesperson` | Create salesperson |
| POST | `/employees/manager` | Create manager |
| PUT | `/employees/{id}/salary` | Update salary |
| DELETE | `/employees/{id}` | Delete employee |

**Example: Create Salesperson**
```bash
curl -X POST http://localhost:8080/api/employees/salesperson \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Silva",
    "nif": "123456789",
    "hireDate": "2023-01-15",
    "salary": 2500.00,
    "commissionRate": 5.5
  }'
```

### Jewelry Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/jewelry` | List all jewelry |
| GET | `/jewelry/{id}` | Get jewelry by ID |
| GET | `/jewelry/type/{type}` | Filter by type |
| GET | `/jewelry/category/{category}` | Filter by category |
| GET | `/jewelry/in-stock` | In-stock items |
| POST | `/jewelry/necklace` | Create necklace |
| POST | `/jewelry/earring` | Create earring |
| POST | `/jewelry/ring` | Create ring |
| PUT | `/jewelry/{id}/price` | Update price |
| PUT | `/jewelry/{id}/stock` | Update stock |
| DELETE | `/jewelry/{id}` | Delete jewelry |

**Example: Create Ring**
```bash
curl -X POST http://localhost:8080/api/jewelry/ring \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Diamond Ring",
    "type": "RING",
    "material": "Platinum",
    "weight": 8.0,
    "price": 2500.00,
    "stock": 5,
    "category": "BRIDAL",
    "size": 18
  }'
```

### Customer Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/customers` | List all customers |
| GET | `/customers/{id}` | Get customer by ID |
| GET | `/customers/nif/{nif}` | Find by NIF |
| GET | `/customers/email/{email}` | Find by email |
| POST | `/customers` | Create customer |
| PUT | `/customers/{id}` | Update customer |
| DELETE | `/customers/{id}` | Delete customer |

### Order Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/orders` | List all orders |
| GET | `/orders/{id}` | Get order by ID |
| GET | `/orders/customer/{customerId}` | Orders by customer |
| GET | `/orders/status/{status}` | Filter by status |
| POST | `/orders` | Create order |
| POST | `/orders/{id}/items` | Add item to order |
| PUT | `/orders/{id}/status` | Update status |
| DELETE | `/orders/{id}` | Cancel order |

**Example: Create Order**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "items": [
      {
        "jewelryId": 1,
        "quantity": 2
      }
    ]
  }'
```

### Payment Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/payments` | List all payments |
| GET | `/payments/{id}` | Get payment by ID |
| GET | `/payments/order/{orderId}` | Payments for order |
| POST | `/payments` | Add payment |

## 💾 Database Design

### Entity Relationship Diagram

```
Customer 1 ────────── * Order
Order    1 ────────── * OrderItem
OrderItem * ────────── 1 Jewelry
Order    1 ────────── * Payment

Employee (abstract)
    ├── Salesperson
    └── Manager

Jewelry (abstract)
    ├── Necklace
    ├── Earring
    └── Ring
```

### JPA Inheritance Strategy

This project uses **JOINED inheritance strategy** for both Employee and Jewelry hierarchies:

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // common attributes...
}
```

### Key Constraints

- **Unique constraints**: Employee NIF, Customer NIF, Customer email
- **Check constraints**: Salary > 0, Price > 0, Stock >= 0, Ring size 10-30
- **Foreign key constraints**: All relationships properly defined
- **Cascade rules**: Order → OrderItem (ALL), Order → Payment (ALL)

## 📋 Business Rules

### Employee Rules
- NIF must be unique and exactly 9 digits
- Salary must be positive
- Commission rate must be between 0 and 100

### Jewelry Rules
- Stock cannot be negative
- Price must be positive
- Ring size must be between 10 and 30 (European standard)

### Order Rules
- Must contain at least one item
- Status transitions are strictly controlled:
    - PENDING → ACCEPTED
    - PENDING → CANCELED
    - ACCEPTED → DELIVERED
    - ACCEPTED → CANCELED
- Stock is reduced only when order is DELIVERED
- Stock is restored when order is CANCELED

### Customer Rules
- NIF and email must be unique
- Cannot delete customers with existing orders

### Payment Rules
- Payment amount must be positive
- Multiple payments allowed per order

## 🧪 Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=OrderServiceTest
```

### API Testing with Postman

1. Import the Postman collection from `postman/doa-store-collection.json`
2. Set up environment variables:
    - `base_url`: `http://localhost:8011/api/
3. Run the collection tests

### Test Scenarios to Verify

1. **Happy Path**
    - Create customer → Create order → Add items → Process payment → Update status

2. **Validation Tests**
    - Invalid NIF format → 400 Bad Request
    - Duplicate NIF → 409 Conflict
    - Negative price → 400 Bad Request

3. **Business Rule Tests**
    - Order out of stock → 409 Conflict
    - Invalid status transition → 409 Conflict
    - Delete customer with orders → 409 Conflict

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Coding Standards
- Follow Java naming conventions
- Write unit tests for new features
- Update documentation as needed
- Use meaningful commit messages

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- Jose Rodrigues - Initial work - [YourGitHub](https://github.com/ZTIC/DOA)

## 🙏 Acknowledgments

- Project 1 team for the original domain model and business rules
- Spring Boot documentation and community
- Docker documentation for containerization guidance

## 📞 Support

For support, email zticlda@gmail.com or create an issue in the GitHub repository.

---

