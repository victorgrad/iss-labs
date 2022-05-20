package repository;

import model.Status;
import model.User;

public interface UserRepo extends Repository<String, User> {
    Status authenticate(String username, String password) throws Exception;
}
