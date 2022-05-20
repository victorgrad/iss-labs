package repository;

import model.Entity;

import java.util.Set;

public interface Repository<ID, E extends Entity<ID>> {
    E findOne(ID id) throws Exception;
    Set<E> findAll() throws Exception;
    ID save(E entity) throws Exception;
    void delete(ID id) throws Exception;
    void update(E entity) throws Exception;
    boolean exists(ID id) throws Exception;
}
