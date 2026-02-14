package model.entities;

public class Book {
    private String title;
    private Author author;
    private double price;

    public Book(String title, Author author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
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

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Book book = (Book) object;
        return java.lang.Double.compare(price, book.price) == 0 && java.util.Objects.equals(title, book.title) && java.util.Objects.equals(author, book.author);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), title, author, price);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author=" + author +
                ", price=" + price +
                '}';
    }
}
