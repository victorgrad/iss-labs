package service;

import model.*;
import utils.Encrypt;

import java.time.LocalDate;
import java.util.Set;

public interface IService {
    void rent(User user, Set<BookModel> books) throws Exception;
    void addUser(String username, String password, Status status, String firstName, String lastName) throws Exception;
    void updateUser(String username, String password, Status status, String firstName, String lastName) throws Exception;
    void deleteUser(String username)throws Exception;
    Status authenticate(String username, String password) throws Exception;
    User getUser(String username) throws Exception;
    Set<Book> getBooks() throws Exception;
    Set<Book> getAvaliableBooks() throws Exception;
    Integer addBook(String name, String author, String category, LocalDate release, Integer quantity) throws Exception;
    void deleteBook(Integer bookId)throws Exception;
    void receive(Integer bookId,String username)throws Exception;
    Set<Borrow> getBorrows() throws Exception;
    Set<User> getLibrarians() throws Exception;
    Set<User> getSubscribers() throws Exception;
}
