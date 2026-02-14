
public class CompositionTest {
    public static void main(String[] args) {
        Address address = new Address("Travessa da Figueira", "Barreiro", "2830-370", "Portugal");
        Person person = new Person("Valdemar Buco", "27/02/1984", address, "+351939363999");
        BankAccount bankAccount = new BankAccount("PT50000123456789", person, 1500.00, "Savings");

        // Print account summary
        System.out.println(bankAccount.getAccountSummary());
        System.out.println();

        // Deposit money
        bankAccount.deposit(500.00);
        System.out.println();

        // Withdraw money
        bankAccount.withdraw(300.00);
        System.out.println();
        // Attempt to withdraw more than available
        bankAccount.withdraw(2000.00);
        System.out.println();

        // Relocate the person
        System.out.println("Relocating account holder...");
        Address newAddress = new Address("Rua do Adamastor", "Barreiro", "2830-104", "Portugal");
        person.relocate(newAddress);
        System.out.println("New Address: " + person.getAddress());
        System.out.println();

        // Print updated account information
        System.out.println("Updated Account Summary:");
        System.out.println("Account Holder Address: " + bankAccount.getAccountHolder().getAddress());
    }
}