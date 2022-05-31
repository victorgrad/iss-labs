package service;

import model.Book;
import repository.BookRepo;

import java.time.LocalDate;
import java.util.Set;

public class BookService {
    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Set<Book> getBooks() throws Exception {
        return bookRepo.findAll();
    }

    public Set<Book> getAvaliableBooks() throws Exception {
        return bookRepo.findAllAvaliable();
    }

    public Integer addBook(String name, String author, String category, LocalDate release, Integer quantity) throws Exception {
        Book book = new Book(name,author,category,release);
        book.setQuantity(quantity);
        book.setInitial_quantity(quantity);
        return bookRepo.save(book);
    }

    public void deleteBook(Integer bookId)throws Exception{
        bookRepo.delete(bookId);
    }
}
