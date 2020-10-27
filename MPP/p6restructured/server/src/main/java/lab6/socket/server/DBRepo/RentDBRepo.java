package lab6.socket.server.DBRepo;

import javafx.util.Pair;
import lab6.socket.common.domain.Rent;
import lab6.socket.common.domain.validators.Validator;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentDBRepo implements Repo<Pair<Long, Long>, Rent> {
    private static final String URL = "jdbc:postgresql://localhost:5432/MovieRental";
    private static final String USER = System.getProperty("pgUsername");;
    private static final String PASSWORD = System.getProperty("pgPassword");

    private Validator<Rent> validator;

    public RentDBRepo(Validator<Rent> _validator) {
        validator = _validator;
    }

    @Override
    public Optional<Rent> find(Pair<Long, Long> id) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("select * from Rents where clientID = ? and movieID = ?");
            statement.setLong(1, id.getKey());
            statement.setLong(2, id.getValue());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LocalDate returnDate = resultSet.getDate("returnDate").toLocalDate();
                Rent rent = new Rent(id.getKey(), id.getValue(), returnDate);
                return Optional.of(rent);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Rent> findAll() {
        List<Rent> result = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("select * from Rents");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long clientId = resultSet.getLong("clientID");
                Long movieID = resultSet.getLong("movieID");
                LocalDate returnDate = resultSet.getDate("returnDate").toLocalDate();
                result.add(new Rent(clientId, movieID, returnDate));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return result;
        }
    }

    @Override
    public Optional<Rent> add(Rent rent) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("insert into Rents(clientID, movieID, returnDate) values (?, ?, ?)");
            statement.setLong(1, rent.getId().getKey());
            statement.setLong(2, rent.getId().getValue());
            statement.setDate(3, Date.valueOf(String.valueOf(rent.getReturnDate())));
            statement.executeUpdate();
            return Optional.empty();
        }
        catch (PSQLException ignored) {}
        catch (SQLException ex) {
            ex.printStackTrace();
            return Optional.of(rent);
        }
        return Optional.of(rent);
    }

    @Override
    public Optional<Rent> delete(Pair<Long, Long> id) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("delete from Rents where clientID = ? and movieID = ?");
            statement.setLong(1, id.getKey());
            statement.setLong(2, id.getValue());
            statement.executeUpdate();
            return find(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return find(id);
        }
    }

    @Override
    public Optional<Rent> update(Rent rent) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("update Rents set returnDate = ? where clientID = ? and movieID = ?");
            statement.setDate(1, Date.valueOf(rent.getReturnDate()));
            statement.setLong(2, rent.getId().getKey());
            statement.setLong(3, rent.getId().getValue());
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Optional.of(rent);
        }
    }
}
