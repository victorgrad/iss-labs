package repository;

import model.Book;

public interface BookRepo extends Repository<Integer, Book>{

    Integer findQuantity(Integer id) throws Exception;
    void reduceQuantity(Integer id) throws Exception;
}
