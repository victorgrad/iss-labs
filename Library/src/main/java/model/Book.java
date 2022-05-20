package model;

import java.time.LocalDate;

public class Book extends Entity<Integer>{
    private final String name;
    private final String author;
    private final String category;
    private final LocalDate release;
    private Integer quantity;

    public Book(String name, String author, String category, LocalDate release) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.release = release;
        this.quantity = null;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getRelease() {
        return release;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", release=" + release +
                '}';
    }
}
