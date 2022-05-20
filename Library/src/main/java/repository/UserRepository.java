package repository;

import model.Status;
import model.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserRepository implements UserRepo{
    private final Connection connection;

    public UserRepository(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public boolean exists(String username) throws Exception {
        String sql = "SELECT count(*) from users where username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getInt("count") > 0){
                    return true;
                }
            }
        }
        catch (SQLException err){
            throw new Exception("SQL query error");
        }
        return false;
    }

    @Override
    public User findOne(String username) throws Exception {
        User user = null;
        String sql = "SELECT * from users where username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String status = resultSet.getString("status");
                user = getUser(username, firstName, lastName, status);
            }
        }
        catch (SQLException err){
            throw new Exception("SQL query error");
        }

        return user;
    }

    /**
     * returns the password hash of a user
     * @param username String the username of the user
     * @return String, if the user exists, null otherwise
     */
    public String getPasswordHash(String username) throws Exception {
        String pass = null;
        String sql = "SELECT password from users where username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                pass = resultSet.getString("pass");
            }
        }
        catch (SQLException err){
            throw new Exception("SQL query error");
        }

        return pass;
    }

    @Override
    public Set<User> findAll() throws Exception {
        Set<User> users = new HashSet<>();

        try(PreparedStatement statement = connection.prepareStatement("SELECT * from users");
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String status = resultSet.getString("status");
                User user = null;
                user = getUser(username, firstName, lastName, status);
                users.add(user);
            }
        }
        catch (SQLException err){
            throw new Exception("SQL query error");
        }
        return users;
    }

    private User getUser(String username, String firstName, String lastName, String status) {
        User user = null;
        switch (status) {
            case "user" -> {
                user = new User(username, Status.USER, firstName, lastName);
            }
            case "librarian" -> {
                user = new User(username, Status.LIBRARIAN, firstName, lastName);
            }
            case "admin" -> {
                user = new User(username, Status.ADMIN, firstName, lastName);
            }
        }
        return user;
    }

    @Override
    public String save(User entity) throws Exception {
        String sql = "insert into users (username, status, first_name, last_name, password) values (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String status = null;
            switch (entity.getStatus()) {
                case USER -> {
                    status = "user";
                }
                case LIBRARIAN -> {
                    status = "librarian";
                }
                case ADMIN -> {
                    status = "admin";
                }
            }

            ps.setString(1, entity.getId());
            ps.setString(2, status);
            ps.setString(3, entity.getFirstName());
            ps.setString(4, entity.getLastName());
            ps.setString(5, entity.getPasswordHash());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Couldn't insert in repo");
        }

        return entity.getId();
    }

    @Override
    public void delete(String username) throws Exception {
        String sql = "delete from users where username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, username);
            statement.executeUpdate();
        }
        catch (SQLException err){
            throw new Exception("Couldn't delete from repo");
        }
    }

    @Override
    public void update(User entity) throws Exception {
        String sql = "update users set first_name = ?, last_name = ? where username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getId());

            statement.executeUpdate();
        }
        catch (SQLException err){
            throw new Exception("Couldn't update repo");
        }
    }

    @Override
    public Status authenticate(String username, String password) throws Exception {
        String sql = "SELECT * from users where username = ? and password = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2,password);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String status = resultSet.getString("status");
                switch (status) {
                    case "user" -> {
                        return Status.USER;
                    }
                    case "librarian" -> {
                        return Status.LIBRARIAN;
                    }
                    case "admin" -> {
                        return Status.ADMIN;
                    }
                }
            }
            else{
                return Status.ERROR;
            }
        }
        catch (SQLException err){
            throw new Exception("SQL query error");
        }
        return Status.ERROR;
    }
}
