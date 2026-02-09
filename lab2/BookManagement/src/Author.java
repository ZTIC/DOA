import java.util.Objects;

public class Author {
    private String firstName;
    private String lastName;
    private String nif;
    private int age;
    public Author(String firstName, String lastName, String nif, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nif = nif;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNif() {
        return nif;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return age == author.age && Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName) && Objects.equals(nif, author.nif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, nif, age);
    }

    @Override
    public String toString() {
        return firstName +" "+ lastName;
    }
}
