package service;

import model.Book;
import model.BookModel;
import model.Status;
import model.User;
import utils.Encrypt;

import java.time.LocalDate;
import java.util.Set;

public interface IService {
    void rent(User user, Set<BookModel> books) throws Exception;
    void addUser(String username, String password, Status status, String firstName, String lastName) throws Exception;
    Status authenticate(String username, String password) throws Exception;
    User getUser(String username) throws Exception;
    Set<Book> getBooks() throws Exception;
    void addBook(String name, String author, String category, LocalDate release, Integer quantity) throws Exception;
}
