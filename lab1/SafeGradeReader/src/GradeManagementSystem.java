import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Student[] students = new Student[3];

        // Initialize students
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student("Student " + (i + 1));
        }
        boolean flag = true;
        while (flag) {
            System.out.println("\n###### Welcome to Grade Management System ########");
            System.out.println("Option 1: Add a grade");
            System.out.println("Option 2: View student averages");
            System.out.println("Option 3: Exit");
            System.out.println("Enter your choice: ");

            try {
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        addGrades(scanner, students);
                        break;
                    case "2":
                        viewAverages(students);
                        break;
                    case "3":
                        System.out.println("Existing system...\nBye!");
                        flag = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please to choose valid options {1, 2, or 3}");
                }

            } catch (IllegalArgumentException e) {
                // TODO: Handle invalid grade values
                System.out.println("Error: " + e.getMessage());
            } catch (InputMismatchException e) {
                // TODO: Handle no-integer input
                System.out.print("Error: Please enter a valid integer.");
            } finally {
                if(!flag) {
                    // TODO: Print completion message
                    System.out.print("Grade entry process completed.");
                    scanner.close();
                }
            }
        }

    }

    private static void addGrades(Scanner scanner, Student[] students) {
        System.out.print("Enter student number (1-3): ");
        int studentNumber = Integer.parseInt(scanner.nextLine()) - 1;

        if (studentNumber < 1 || studentNumber > 3) {
            throw new IllegalArgumentException("Invalid student number. Must be 1-3.");
        }

        System.out.print("Enter 4 grades: ");
        int[] grades = new int[4];
        for (int i = 0; i < 4; i++) {
            System.out.print("Enter grade " + (i + 1) + ": ");
            grades[i] = Integer.parseInt(scanner.nextLine());
            if (grades[i] < 0 || grades[i] > 100) {
                throw new IllegalArgumentException("Invalid grade. Must be between 0 and 100.");
            }
        }
        students[studentNumber].addGrade(grades[0], grades[1], grades[2], grades[3]);
        System.out.print("Grades added successfully for " + students[studentNumber].getName() + "!\n") ;
    }

    private static void viewAverages(Student[] students) {
        System.out.print("########### Student Average #########");
        double classTotal = 0;
        double validStudents = 0;
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            if(!student.hasGrades()){
                System.out.print("Student " + student.getName() + " has no grades!\n") ;
            }
            double average = student.getAverage();
            System.out.printf("%s: % 2f%n", student.getName(), average);
            classTotal += average;
            validStudents++;
        }
        if (validStudents < 0) {
            System.out.print("Student has no grades available to calculate!") ;
        }
            double classAverage = classTotal / validStudents;
            System.out.printf("\nAverage grades: %.2f%n", classAverage);
    }
}
