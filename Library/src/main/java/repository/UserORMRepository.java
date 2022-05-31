package repository;

import model.Status;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        String id = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                id = (String)session.save(entity);
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
    public void delete(String s) throws Exception {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                User aux = new User();
                aux.setId(s);
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
    public void update(User entity) throws Exception {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                User user = findOne(entity.getUsername());
                transaction = session.beginTransaction();
                if(entity.getPasswordHash().equals("")){
                    entity.setPasswordHash(user.getPasswordHash());
                }
                session.merge(entity);
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
    }

    @Override
    public boolean exists(String s) throws Exception {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                Query query = session.createQuery("select count(*) from User where username = :id");
                query.setParameter("id",s);
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

    @Override
    public Status authenticate(String username, String password) throws Exception {
        Status sts = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                Query query = session.createQuery("select status from User where username = :id and passwordHash = :psw");
                query.setParameter("id",username);
                query.setParameter("psw",password);
                sts = (Status)query.uniqueResult();
                transaction.commit();


            }catch (RuntimeException e){
                if(transaction!=null)
                    transaction.rollback();
                throw e;
            }
        }catch (Exception e) {
            throw e;
        }
        return sts;
    }

    @Override
    public Set<User> getLibrarians() throws Exception {
        Set<User> users = findAll();
        return users.stream().filter(new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getStatus()==Status.LIBRARIAN;
            }
        }).collect(Collectors.toSet());
    }

    @Override
    public Set<User> getSubscribers() throws Exception {
        Set<User> users = findAll();
        return users.stream().filter(new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getStatus()==Status.USER;
            }
        }).collect(Collectors.toSet());
    }
}
