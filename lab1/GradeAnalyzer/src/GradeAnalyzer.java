import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class GradeAnalyzer {
    public static void main(String[] args) {

        // TODO: Declare and initialize the grades array
        double[] grades = {78.5, 92.0, 85.5, 88.0, 76.5};

        // TODO: Calculate average
        double sum = 0;
        for (int i = 0; i < grades.length; i++) {
            sum += grades[i];
        }

        // TODO: Find maximum grade
        double maxGrade = grades[0];
        for (double grade : grades) {
            if (grade > maxGrade) {
                maxGrade = grade;
            }
        }

        // TODO: Count grades above 85.0
        int countAbove85 = 0;
        for (int i = 0; i < grades.length; i++){
            if(grades[i] > 85.0){
                countAbove85++;
            }
        }
        System.out.println("The grade is " + sum/grades.length);
        System.out.println("Max Grade: " + maxGrade);
        System.out.println("Students with Grades Above 85.0: " + countAbove85);
    }
}