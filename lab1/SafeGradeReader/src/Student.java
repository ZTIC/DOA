

public class Student {

    private String name;
    private Grade grade = new Grade();

    public Student(String name) {
        this.name = name;
        this.grade = new  Grade();
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void addGrade(int k, int x, int y, int z){
        grade.setValue1(k);
        grade.setValue2(x);
        grade.setValue3(y);
        grade.setValue4(z);
    }

    public double getAverage(){
        return grade.average();
    }

    public boolean hasGrades(){
        return grade.getValue1() != 0 || grade.getValue2() != 0 || grade.getValue3() != 0 || grade.getValue4() != 0;
    }
    @Override
    public String toString() {
        return name + " - Average: " + String.format("%.2f", getAverage());
    }
}
