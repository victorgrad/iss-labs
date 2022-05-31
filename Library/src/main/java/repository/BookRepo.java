package repository;

import model.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Set;

public interface BookRepo extends Repository<Integer, Book>{

    Integer findQuantity(Integer id) throws Exception;
    void reduceQuantity(Integer id) throws Exception;
    void increaseQuantity(Integer id) throws Exception;
    Set<Book> findAllAvaliable() throws Exception;
}
