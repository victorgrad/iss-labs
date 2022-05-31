package service;

import model.*;

import java.time.LocalDate;
import java.util.Set;

public class Service implements IService{
    private final BorrowService borrowService;
    private final UserService userService;
    private final BookService bookService;

    public Service(BorrowService borrowService, UserService userService, BookService bookService) {
        this.borrowService = borrowService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public void rent(User user, Set<BookModel> books) throws Exception {
        borrowService.rent(user,books);
    }

    @Override
    public void addUser(String username, String password, Status status, String firstName, String lastName) throws Exception {
        userService.addUser(username,password,status,firstName,lastName);
    }

    @Override
    public void updateUser(String username, String password, Status status, String firstName, String lastName) throws Exception {
        userService.updateUser(username,password,status,firstName,lastName);
    }

    @Override
    public void deleteUser(String username) throws Exception {
        userService.deleteUser(username);
    }

    @Override
    public Status authenticate(String username, String password) throws Exception {
        return userService.authenticate(username,password);
    }

    @Override
    public User getUser(String username) throws Exception {
        return userService.getUser(username);
    }

    @Override
    public Set<Book> getBooks() throws Exception {
        return bookService.getBooks();
    }

    @Override
    public Set<Book> getAvaliableBooks() throws Exception {
        return bookService.getAvaliableBooks();
    }

    @Override
    public Integer addBook(String name, String author, String category, LocalDate release, Integer quantity) throws Exception {
        return bookService.addBook(name,author,category,release,quantity);
    }

    @Override
    public void deleteBook(Integer bookId) throws Exception {
        bookService.deleteBook(bookId);
    }

    @Override
    public void receive(Integer bookId, String username) throws Exception {
        borrowService.receive(bookId,username);
    }

    @Override
    public Set<Borrow> getBorrows() throws Exception {
        return borrowService.getBorrows();
    }

    @Override
    public Set<User> getLibrarians() throws Exception {
        return userService.getLibrarians();
    }

    @Override
    public Set<User> getSubscribers() throws Exception {
        return userService.getSubscribers();
    }
}
