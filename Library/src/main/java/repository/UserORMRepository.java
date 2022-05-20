package repository;

import model.Status;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Set;

public class UserORMRepository implements UserRepo{
    private SessionFactory sessionFactory;
    public UserORMRepository(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }

    @Override
    public User findOne(String s) throws Exception {
        User user = null;

        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                user = session.get(User.class,s);
                transaction.commit();
            }catch (Exception e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }catch (Exception e){
            throw e;
        }
        return user;
    }

    @Override
    public Set<User> findAll() throws Exception {
        Set<User> users = null;

        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                users = new HashSet<>(session.createQuery("from User", User.class).list());
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
        return users;
    }

    @Override
    public String save(User entity) throws Exception {
        return null;
    }

    @Override
    public void delete(String s) throws Exception {

    }

    @Override
    public void update(User entity) throws Exception {

    }

    @Override
    public boolean exists(String s) throws Exception {
        return false;
    }

    @Override
    public Status authenticate(String username, String password) throws Exception {
        return null;
    }
}
