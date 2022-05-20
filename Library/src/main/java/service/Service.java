package service;

import model.Book;
import model.BookModel;
import model.Status;
import model.User;

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
    public void addBook(String name, String author, String category, LocalDate release, Integer quantity) throws Exception {
        bookService.addBook(name,author,category,release,quantity);
    }
}
