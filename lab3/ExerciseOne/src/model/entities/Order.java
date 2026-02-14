package model.entities;

import model.Common;

import java.util.ArrayList;
import java.util.List;

public class Order extends Common {
    private List<Book> books = new ArrayList<>();
    private double totalPrice;

    public Order(int id, List<Book> books) {
        super(id);
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Book> addBook(Book book) {

        return null;
    }
    public void calculateTotalPrice() {
        totalPrice = 0;
        for (Book book : books) {
            totalPrice += book.getPrice();
        }
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Order{" +
                "books=" + books +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
