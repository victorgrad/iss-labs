package repository;

import model.Book;
import model.Borrow;
import org.javatuples.Triplet;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/*
public class BorrowRepository implements BorrowRepo{
    private final Connection connection;

    public BorrowRepository(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }


    @Override
    public Borrow findOne(Triplet<String, Integer, LocalDate> objects) throws Exception {
        return null;
    }

    @Override
    public Set<Borrow> findAll() throws Exception {
        Set<Borrow> borrows = new HashSet<>();

        try(PreparedStatement statement = connection.prepareStatement("SELECT * from borrows");
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                Integer id = resultSet.getInt("id");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                Borrow borrow = new Borrow();
                borrow.setId(new Triplet<String,Integer,LocalDate>(username,id,date));
                borrows.add(borrow);
            }
        }
        catch (SQLException err){
            throw new Exception("SQL query error");
        }
        return borrows;
    }

    @Override
    public Triplet<String, Integer, LocalDate> save(Borrow entity) throws Exception {
        String sql = "insert into borrows (username, id, date) values (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getId().getValue0());
            ps.setInt(2, entity.getId().getValue1());
            ps.setDate(3,Date.valueOf(entity.getId().getValue2()));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Couldn't insert in repo");
        }
        return entity.getId();
    }

    @Override
    public void delete(Triplet<String, Integer, LocalDate> objects) throws Exception {
        String sql = "delete from borrows where username = ?, id = ?, date = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, objects.getValue0());
            statement.setInt(2,objects.getValue1());
            statement.setDate(3,Date.valueOf(objects.getValue2()));

            statement.executeUpdate();
        }
        catch (SQLException err){
            throw new Exception("Couldn't delete from repo");
        }
    }

    @Override
    public void update(Borrow entity) throws Exception {

    }

    @Override
    public boolean exists(Triplet<String, Integer, LocalDate> objects) throws Exception {
        String sql = "SELECT count(*) from borrows where username = ?, id = ?, date = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, objects.getValue0());
            statement.setInt(2,objects.getValue1());
            statement.setDate(3,Date.valueOf(objects.getValue2()));

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
}
*/