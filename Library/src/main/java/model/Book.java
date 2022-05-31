package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@javax.persistence.Entity
@Table(name = "books")
public class Book extends Entity<Integer>{
    private String name;
    private String author;
    private String category;
    private LocalDate release;
    private Integer quantity;
    private Integer initial_quantity;

    public Book(){}

    public Book(String name, String author, String category, LocalDate release) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.release = release;
        this.quantity = null;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Integer getId(){return super.getId();}

    public void setId(Integer id){super.setId(id);}

    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "initial_quantity")
    public Integer getInitial_quantity() {
        return initial_quantity;
    }

    public void setInitial_quantity(Integer initial_quantity) {
        this.initial_quantity = initial_quantity;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    @Column(name = "release")
    public LocalDate getRelease() {
        return release;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRelease(LocalDate release) {
        this.release = release;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", release=" + release +
                '}';
    }
}
