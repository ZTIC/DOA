//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BookManagement {
    public static void main(String[] args) {
        Author author = new Author("José", "Saramago", "290116422", 101);
        Book book = new Book(1, "Ensaio sobre a Cegueira", author, 25.50, "Novel");

        System.out.println(book.toString());
    }
}