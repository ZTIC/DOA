import employees.EmployeeManager;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String EMPLOYEE_CSV = "employees.csv";

    public static void main(String[] args) {
        EmployeeManager em = new EmployeeManager();
        em.addSalesPerson("Valdemar Buco", "931-931-931", "va@gm.co", "+351939363999", "rua dad00", LocalDate.now(), 2500.00, 50.00);

        System.out.println(em);
        System.out.printf("Monthly Pay: %.2f €%n", em.calculateTotalPayroll());


    // ---------- FILTER ----------
        System.out.println("\n=== Salespeople ===");
        em.listSalespeople().forEach(System.out::println);

        System.out.println("\n=== Managers ===");
        em.listManagers().forEach(System.out::println);

    // ---------- PAYROLL ----------
        System.out.println("\n=== Total Payroll ===");
        System.out.printf("Total: %.2f €%n",
                em.calculateTotalPayroll());

    // ---------- SAVE ----------
        em.save(EMPLOYEE_CSV);

        System.out.println("\n=== Employees saved to CSV ===");
    }
}