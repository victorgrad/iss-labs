package service;

import model.BookModel;
import model.Borrow;
import model.User;
import repository.BookRepo;
import repository.BorrowRepo;
import utils.BorrowId;
import utils.Trip;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BorrowService {
    private final BorrowRepo borrowRepo;
    private final BookRepo bookRepo;

    public BorrowService(BorrowRepo borrowRepo, BookRepo bookRepo) {
        this.borrowRepo = borrowRepo;
        this.bookRepo = bookRepo;
    }

    public void rent(User user, Set<BookModel> books) throws Exception {
        Set<String> titles = new HashSet<>();
        for(BookModel book : books){
            if(bookRepo.findQuantity(book.getId())>0){
                Borrow borrow = new Borrow(LocalDate.now());
                borrow.setId(new BorrowId(user.getId(), book.getId()));
                borrowRepo.save(borrow);
                bookRepo.reduceQuantity(book.getId());
            }
            else{
                titles.add(book.getName());
            }
        }
        if(!titles.isEmpty()) {
            String message = "Couldn't rent\n";
            for(String title : titles)
                message+=title+"\n";
            throw new Exception(message);
        }
    }

    public void receive(Integer bookId,String username) throws Exception {
        bookRepo.increaseQuantity(bookId);
        BorrowId borrowId = new BorrowId();
        borrowId.setId(bookId);
        borrowId.setUsername(username);
        borrowRepo.delete(borrowId);
    }

    public Set<Borrow> getBorrows() throws Exception {
        return borrowRepo.findAll();
    }
}
