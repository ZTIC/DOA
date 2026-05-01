import customers.CustomerManagement;
import employees.EmployeeManager;
import jewlry.JewelryManager;
import orders.OrderManager;
import payments.PaymentManager;
import ui.ConsoleUI;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String EMPLOYEE_CSV = "employees.csv";
    private static final String JEWELRY_CSV = "jewelry.csv";
    private static final String ORDER_CSV = "orders.csv";
    private static final String PAYMENT_CSV = "payments.csv";
    private static final String CUSTOMER_CSV = "customers.csv";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Managers
        JewelryManager jewelryManager = new JewelryManager();
        PaymentManager paymentManager = new PaymentManager();
        EmployeeManager employeeManager = new EmployeeManager();
        OrderManager orderManager = new OrderManager(jewelryManager, paymentManager);
        CustomerManagement customerManagement = new CustomerManagement();

        // Load data
        jewelryManager.load(JEWELRY_CSV);
        orderManager.load(ORDER_CSV);
        employeeManager.load(EMPLOYEE_CSV);

        ConsoleUI ui = new ConsoleUI(
                jewelryManager,
                orderManager,
                employeeManager,
                customerManagement
        );

        ui.start();

    }
}