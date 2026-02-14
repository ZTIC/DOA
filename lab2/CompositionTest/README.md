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
