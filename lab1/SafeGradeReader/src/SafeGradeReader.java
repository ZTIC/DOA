import java.util.Scanner;
import java.util.InputMismatchException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class SafeGradeReader {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int[] grades = new int[3];
        int validCount = 0;

        try {
            for (int i = 0; i < grades.length; i++) {
                System.out.print("Enter grade " + (i + 1) + ": ");
                int grade = scanner.nextInt();
                // TODO: validate grade (0-100)
                if (grade < 0 || grade > 100) {
                    throw new IllegalArgumentException("Grade must be between 0 and 100.");
                }
                grades[i] = grade;
                validCount++;
            }
            // TODO: Calculate and display average of valid grades
            int sum = 0;
            for (int i = 0; i < grades.length; i++) {
                sum += grades[i];
            }
            double average = (double) (sum / validCount);
            System.out.println("Average of valid grades: " + average);
        } catch (IllegalArgumentException e) {
            // TODO: Handle invalid grade values
            System.out.println("Error: " + e.getMessage());
        }catch (InputMismatchException e){
            // TODO: Handle no-integer input
            System.out.print("Error: Please enter a valid integer.");
        }finally {
            // TODO: Print completion message
            System.out.print("Grade entry process completed.");
            scanner.close();
        }
    }
}