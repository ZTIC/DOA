public class BankAccount {
    private String accountNumber;
    private Person accountHolder;
    private double balance;
    private String accountType;

    public BankAccount(String accountNumber, Person accountHolder, double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }


    public Person getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }


    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    // Method to deposit
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be greater than 0.");
        }
        balance += amount;
        System.out.println("Deposited " + amount);
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount < balance) {
            balance -= amount;
            System.out.printf("Withdrawn €%.2f... Success!\n", amount);
            System.out.printf("Withdrawn €%.2f... Success!\n", balance);
        } else if (amount > balance) System.out.printf("Attempt to withdraw €%.2f... Failed: Insufficient funds\n", amount);
        else System.out.printf("Withdraw failed: Amount must be greater than €%.2f... ", amount);

    }

    public String getAccountSummary() {
        return "Account Summary: \n" +
                "Account Number: " + accountNumber + "\n" +
                "Account Type: " + accountType + "\n" +
                String.format("Balance: €%.2f\n", balance) + "\n" +
                accountHolder.toString();
    }

//    public void getAccountSummary() {
//        System.out.println("Account Summary: ");
//        System.out.println("Account Number: " + accountNumber);
//        System.out.println("Account Type: " + accountType);
//        System.out.println("Balance: " + getBalance());
//        System.out.println("\n");
//        System.out.println("Account Holder: " + accountHolder.getName());
//        System.out.println("Date of Birth: " + accountHolder.getDateOfBirth());
//        System.out.println("Phone: " + accountHolder.getPhoneNumber());
//        System.out.println("Address: " + accountHolder.getAddress());
//        System.out.println("\n");
//    }
}
