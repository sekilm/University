package lab6.socket.server.DBRepo;

import lab6.socket.common.domain.Movie;
import lab6.socket.common.domain.validators.Validator;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDBRepo implements Repo<Long, Movie> {
    private static final String URL = "jdbc:postgresql://localhost:5432/MovieRental";
    private static final String USER = System.getProperty("pgUsername");;
    private static final String PASSWORD = System.getProperty("pgPassword");

    private Validator<Movie> validator;

    public MovieDBRepo(Validator<Movie> _validator) { validator = _validator; }

    @Override
    public Optional<Movie> find(Long id) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("select * from Movies where movieID = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long movieId = resultSet.getLong("movieID");
                String title = resultSet.getString("title");
                int rating = resultSet.getInt("rating");
                int nrOfAvailableDVDs = resultSet.getInt("nrOfAvailableDVDs");
                return Optional.of(new Movie(movieId, title, rating, nrOfAvailableDVDs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Movie> findAll() {
        List<Movie> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStmt = conn.prepareStatement("select * from Movies");
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("movieID");
                String title = resultSet.getString("title");
                double rating = resultSet.getInt("rating");
                int nrOfAvailableDVDs = resultSet.getInt("nrOfAvailableDVDs");

                result.add(new Movie(id, title, rating, nrOfAvailableDVDs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Optional<Movie> add(Movie movie) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStmt = conn.prepareStatement("insert into Movies(movieID, title, rating, nrOfAvailableDVDs) values (?, ?, ?, ?)");
            preparedStmt.setLong(1, movie.getId());
            preparedStmt.setString(2, movie.getTitle());
            preparedStmt.setDouble(3, movie.getRating());
            preparedStmt.setInt(4, movie.getNrOfAvailableDVDs());
            preparedStmt.executeUpdate();
//            return Optional.of(movie);
            return Optional.empty(); // no sure tho
        }
        catch (PSQLException ignored) {}
        catch (SQLException e) {
            e.printStackTrace();
            return Optional.of(movie);
        }
        return Optional.of(movie);
    }

    @Override
    public Optional<Movie> delete(Long id) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStmt = conn.prepareStatement("delete from Movies where movieID = ?");
            preparedStmt.setLong(1, id);
            preparedStmt.executeUpdate();
            return find(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return find(id);
        }
    }

    @Override
    public Optional<Movie> update(Movie movie) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStmt = conn.prepareStatement("update Movies set title = ?, rating = ?, nrOfAvailableDVDs = ? where movieID = ?");

            preparedStmt.setString(1, movie.getTitle());
            preparedStmt.setDouble(2, movie.getRating());
            preparedStmt.setInt(3, movie.getNrOfAvailableDVDs());
            preparedStmt.setLong(4, movie.getId());

            preparedStmt.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.of(movie);
        }
    }
}
