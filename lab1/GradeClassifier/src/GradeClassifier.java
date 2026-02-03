//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class GradeClassifier {
    public static void main(String[] args) {

        int[] grades = {95, 78, 65, 88, 72, 91};

        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;

        // TODO: Loop through grades and classify each one
        for (int grade : grades) {
            // TODO: Use if-else to assign category and increment counter
            if (grade >= 90) {
                countA++;
            }
            if (grade >= 80 && grade < 90) {
                countB++;
            }
            if (grade >= 70 && grade < 80) {
                countC++;
            }
            if (grade < 70) {
                countD++;
            }
            if (grade < 60) {
                countF++;
            }
        }

        // TODO: Print the report
    System.out.println("Grade Distribution Report: ");
    System.out.println("A(90-100): " + countA);
    System.out.println("B(80-99): " + countB);
    System.out.println("C(70-79): " + countC);
    System.out.println("E(60-69): " + countD);
    System.out.println("F(Below 60): " + countF);
    System.out.println("Total Students: " + grades.length);
    }
}