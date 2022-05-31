package repository;

import model.Status;
import model.User;

import java.util.Set;

public interface UserRepo extends Repository<String, User> {
    Status authenticate(String username, String password) throws Exception;
    Set<User> getLibrarians()throws Exception;
    Set<User> getSubscribers()throws Exception;
}
