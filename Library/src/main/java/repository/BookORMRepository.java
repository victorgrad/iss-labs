package repository;

import model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.Set;

public class BookORMRepository implements BookRepo{
    private SessionFactory sessionFactory;

    public BookORMRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer findQuantity(Integer id) throws Exception {
        Integer qt;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                Query query = session.createQuery("select quantity from Book where id = :id");
                query.setParameter("id",id);
                qt = (Integer)query.uniqueResult();
                transaction.commit();


            }catch (RuntimeException e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }catch (Exception e) {
            throw e;
        }
        return qt;
    }

    @Override
    public void reduceQuantity(Integer id) throws Exception {
        Integer qt;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                Query query = session.createQuery("select quantity from Book where id = :id");
                query.setParameter("id",id);
                qt = (Integer)query.uniqueResult();
                qt=qt-1;
                query = session.createQuery("update Book set quantity = :qut where id = :id");
                query.setParameter("qut",qt);
                query.setParameter("id",id);
                query.executeUpdate();
                transaction.commit();
            }catch (RuntimeException e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }catch (Exception e) {
            throw e;
        };
    }

    @Override
    public void increaseQuantity(Integer id) throws Exception {
        Integer qt;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                Query query = session.createQuery("select quantity from Book where id = :id");
                query.setParameter("id",id);
                qt = (Integer)query.uniqueResult();
                qt=qt+1;
                query = session.createQuery("update Book set quantity = :qut where id = :id");
                query.setParameter("qut",qt);
                query.setParameter("id",id);
                query.executeUpdate();
                transaction.commit();
            }catch (RuntimeException e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }catch (Exception e) {
            throw e;
        };
    }

    @Override
    public Set<Book> findAllAvaliable() throws Exception {
        Set<Book> books = null;

        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                books = new HashSet<>(session.createQuery("from Book where quantity > 0", Book.class).list());
                transaction.commit();

            }catch (RuntimeException e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }
        catch (Exception ex){
            throw ex;
        }
        return books;
    }

    @Override
    public Book findOne(Integer integer) throws Exception {
        Book book = null;

        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                book = session.get(Book.class,integer);
                transaction.commit();
            }catch (Exception e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }catch (Exception e){
            throw e;
        }
        return book;
    }

    @Override
    public Set<Book> findAll() throws Exception {
        Set<Book> books = null;

        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                books = new HashSet<>(session.createQuery("from Book", Book.class).list());
                transaction.commit();

            }catch (RuntimeException e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }
        catch (Exception ex){
            throw ex;
        }
        return books;
    }

    @Override
    public Integer save(Book entity) throws Exception {
        Integer id = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                id = (Integer)session.save(entity);
                transaction.commit();

            }catch (RuntimeException e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }
        catch (Exception ex){
            throw ex;
        }
        return id;
    }

    @Override
    public void delete(Integer integer) throws Exception {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                Book aux = new Book();
                aux.setId(integer);
                session.remove(aux);
                transaction.commit();
            }catch (RuntimeException e){
                if(transaction!=null)
                    transaction.rollback();
                throw(e);
            }
        }
        catch (Exception ex){
            throw(ex);
        }
    }

    @Override
    public void update(Book entity) throws Exception {
    }

    @Override
    public boolean exists(Integer integer) throws Exception {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                Query query = session.createQuery("select count(*) from Book where id = :id");
                query.setParameter("id",integer);
                Integer cnt = (Integer)query.uniqueResult();
                transaction.commit();
                if(cnt>0) {
                    return true;
                }
                return false;

            }catch (RuntimeException e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }catch (Exception e) {
            throw e;
        }
    }
}
