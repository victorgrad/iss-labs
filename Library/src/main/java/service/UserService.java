package service;

import model.Status;
import model.User;
import repository.UserRepo;
import utils.Encrypt;

public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void addUser(String username, String password, Status status, String firstName, String lastName) throws Exception {
        User user = new User(username,status,firstName,lastName);
        user.setPasswordHash(Encrypt.getSha(password));
        userRepo.save(user);
    }

    public Status authenticate(String username, String password) throws Exception {
        String passwordHash = Encrypt.getSha(password);
        return userRepo.authenticate(username,passwordHash);
    }

    public User getUser(String username) throws Exception {
        return userRepo.findOne(username);
    }
}
