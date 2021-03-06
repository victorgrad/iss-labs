package model;

public class BookModel {
    private Integer id;
    private String name;
    private String author;
    private String category;
    private String release;
    private Integer quantity;

    public BookModel(Book book){
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.category = book.getCategory();
        this.release = book.getRelease().toString();
        this.quantity= book.getQuantity();
    }

    public BookModel(Integer id, String name, String author, String category, String release, Integer quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.release = release;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " by " +author;
    }
}
