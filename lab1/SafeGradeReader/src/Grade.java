import java.util.Objects;

public class Grade {
    private int value1 = 0;
    private int value2 = 0;
    private int value3 = 0;
    private int value4 = 0;

    private Student student;

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        if (value1 < 0 || value1 > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        if (value2 < 0 || value2 > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
        this.value2 = value2;
    }

    public int getValue3() {
        return value3;
    }

    public void setValue3(int value3) {
        if (value3 < 0 || value3 > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
        this.value3 = value3;
    }

    public int getValue4() {
        return value4;
    }

    public void setValue4(int value4) {
        if (value4 < 0 || value4 > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
        this.value4 = value4;
    }

    public double average() {
        return ((value1 + value2 + value3 + value4)/4.0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return value1 == grade.value1 && value2 == grade.value2 && value3 == grade.value3 && value4 == grade.value4 && Objects.equals(student, grade.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, value3, value4, student);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "value1=" + value1 +
                ", value2=" + value2 +
                ", value3=" + value3 +
                ", value4=" + value4 +
                ", student=" + student +
                '}';
    }
}
