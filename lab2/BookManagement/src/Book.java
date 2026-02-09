import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private Author author;
    private double price;
    private String genre;

    public Book(int id, String title, Author author, double price, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Double.compare(price, book.price) == 0 && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, price, genre);
    }

    @Override
    public String toString() {
        return "Book: " +
                "ID: " + id +
                ", Title: " + title +
                ", Author: " + author.toString() +
                ", Price: " + price +
                ", Genre: " + genre;
    }
}
