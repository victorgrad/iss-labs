package model;

import utils.BorrowId;

import javax.persistence.*;
import java.time.LocalDate;

@javax.persistence.Entity
@Table(name = "borrows")
public class Borrow extends Entity<BorrowId>{
    private LocalDate date;


    @EmbeddedId
    public BorrowId getBorrowId(){
        return this.getId();
    }

    public void setBorrowId(BorrowId borrowId){
        this.setId(borrowId);
    }

    private User user;
    private Book book;

    @ManyToOne
    @JoinColumn(name = "username", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "borrows_users_username_fk"))
    public User getUser() {
        return user;
    }

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "borrows_books_id_fk"))
    public Book getBook() {
        return book;
    }

    public Borrow(){}

    public Borrow(LocalDate date){
        this.date=date;
    }

    @Column(name = "date")
    public LocalDate getDate(){
        return date;
    }


    public void setIdd(Integer id){
        BorrowId borrowId = this.getId();
        borrowId.setId(id);
        super.setId(borrowId);
    }

    public void setUsername(String username){
        BorrowId borrowId = super.getId();
        borrowId.setUsername(username);
        super.setId(borrowId);
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
