//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Challenge {
    public static void main(String[] args) {

        int[] grades = {95, 78, 65, 88, 72, 91};

        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;

        // Loop through grades and classify each one
        for (int grade : grades) {
            // Convert numeric grade to letter grade
            char letterGrade = getLetterGrade(grade);

            // Use switch to increment appropriate counter
            switch (letterGrade) {
                case 'A':
                    countA++;
                    break;
                case 'B':
                    countB++;
                    break;
                case 'C':
                    countC++;
                    break;
                case 'D':
                    countD++;
                    break;
                case 'F':
                    countF++;
                    break;
            }
        }

        // Print the report
        System.out.println("Grade Distribution Report: ");
        System.out.println("A(90-100): " + countA);
        System.out.println("B(80-89): " + countB);
        System.out.println("C(70-79): " + countC);
        System.out.println("D(60-69): " + countD);
        System.out.println("F(Below 60): " + countF);
        System.out.println("Total Students: " + grades.length);
    }

    // Helper method to convert numeric grade to letter grade
    public static char getLetterGrade(int grade) {
        if (grade >= 90) {
            return 'A';
        } else if (grade >= 80) {
            return 'B';
        } else if (grade >= 70) {
            return 'C';
        } else if (grade >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }
}
