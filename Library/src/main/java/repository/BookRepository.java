package repository;

import model.Book;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BookRepository implements BookRepo {
    private final Connection connection;

    public BookRepository(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Book findOne(Integer integer) throws Exception {
        Book book = null;
        String sql = "SELECT * from books where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");
                LocalDate release = resultSet.getDate("date").toLocalDate();
                book = new Book(name,author,category,release);
            }
        }
        catch (SQLException err){
            throw new Exception("SQL query error");
        }

        return book;
    }

    @Override
    public Set<Book> findAll() throws Exception {
        Set<Book> books = new HashSet<>();

        try(PreparedStatement statement = connection.prepareStatement("SELECT * from books");
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");
                LocalDate release = resultSet.getDate("release").toLocalDate();
                Book book = new Book(name,author,category,release);
                book.setId(id);
                books.add(book);
            }
        }
        catch (SQLException err){
            throw new Exception("SQL query error");
        }
        return books;
    }

    @Override
    public Integer save(Book entity) throws Exception {
        String sql = "insert into books (name, author, category, release, initial_quantity, quantity) values (?, ?, ?, ?, ?, ?)";
        Integer id = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getAuthor());
            ps.setString(3, entity.getCategory());
            ps.setDate(4, Date.valueOf(entity.getRelease()));
            ps.setInt(5, entity.getQuantity());
            ps.setInt(6, entity.getQuantity());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new Exception("Couldn't insert in repo");
        }
        return id;
    }

    @Override
    public void delete(Integer integer) throws Exception {
        String sql = "delete from books where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, integer);
            statement.executeUpdate();
        }
        catch (SQLException err){
            throw new Exception("Couldn't delete from repo");
        }
    }

    @Override
    public void update(Book entity) throws Exception {
        String sql = "update books set name = ?, author = ?, category = ?, release = ?, initial_quantity = ? where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAuthor());
            statement.setString(3, entity.getCategory());
            statement.setDate(4,Date.valueOf(entity.getRelease()));
            statement.setInt(5,entity.getQuantity());
            statement.setInt(6,entity.getId());

            statement.executeUpdate();
        }
        catch (SQLException err){
            throw new Exception("Couldn't update repo");
        }
    }

    @Override
    public boolean exists(Integer integer) throws Exception {
        String sql = "SELECT count(*) from books where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, integer);
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
    public Integer findQuantity(Integer id) throws Exception {
        Integer quantity = null;
        String sql = "SELECT quantity from books where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                quantity = resultSet.getInt("quantity");
            }
        }
        catch (SQLException err){
            throw new Exception("SQL query error");
        }

        return quantity;
    }

    @Override
    public void reduceQuantity(Integer id) throws Exception {
        String sql = "update books set quantity = ? where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            Integer quantity = findQuantity(id);
            statement.setInt(1,quantity-1);
            statement.setInt(2,id);

            statement.executeUpdate();
        }
        catch (Exception err){
            throw new Exception("Couldn't reduce quantity repo");
        }
    }

    @Override
    public void increaseQuantity(Integer id) throws Exception {

    }

    @Override
    public Set<Book> findAllAvaliable() throws Exception {
        return null;
    }
}
