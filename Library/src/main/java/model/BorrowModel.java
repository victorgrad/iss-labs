package model;

public class BorrowModel {
    private Integer bookId;
    private String username;
    private String firstName;
    private String lastName;
    private String bookName;
    private String authorName;
    private String date;

    public BorrowModel(Borrow borrow){
        this.bookId = borrow.getBook().getId();
        this.username = borrow.getUser().getUsername();
        this.firstName = borrow.getUser().getFirstName();
        this.lastName = borrow.getUser().getLastName();
        this.bookName = borrow.getBook().getName();
        this.authorName = borrow.getBook().getAuthor();
        this.date = borrow.getDate().toString();
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
