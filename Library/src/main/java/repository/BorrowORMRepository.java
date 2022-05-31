package repository;

import model.Book;
import model.Borrow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.javatuples.Triplet;
import utils.BorrowId;
import utils.Trip;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BorrowORMRepository implements BorrowRepo{
    SessionFactory sessionFactory;

    public BorrowORMRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Borrow findOne(BorrowId objects) throws Exception {
        Borrow borrow = null;

        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                borrow = session.get(Borrow.class,objects);
                transaction.commit();
            }catch (Exception e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }catch (Exception e){
            throw e;
        }
        return borrow;
    }

    @Override
    public Set<Borrow> findAll() throws Exception {
        Set<Borrow> borrows = null;

        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                borrows = new HashSet<>(session.createQuery("from Borrow", Borrow.class).list());
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
        return borrows;
    }

    @Override
    public BorrowId save(Borrow entity) throws Exception {
        BorrowId id = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                id = (BorrowId)session.save(entity);
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
    public void delete( BorrowId objects) throws Exception {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                Borrow aux = new Borrow();
                aux.setId(objects);
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
    public void update(Borrow entity) throws Exception {

    }

    @Override
    public boolean exists( BorrowId objects) throws Exception {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                Query query = session.createQuery("select count(*) from Borrow where user.username = :usr and book.id = :id");
                query.setParameter("usr",objects.getUsername());
                query.setParameter("id",objects.getId());
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
