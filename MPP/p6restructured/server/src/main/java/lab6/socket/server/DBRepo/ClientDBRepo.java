package lab6.socket.server.DBRepo;

import lab6.socket.common.domain.Client;
import lab6.socket.common.domain.validators.Validator;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDBRepo implements Repo<Long, Client> {
    private static final String URL = "jdbc:postgresql://localhost:5432/MovieRental";
    private static final String USER = System.getProperty("pgUsername");;
    private static final String PASSWORD = System.getProperty("pgPassword");

    private Validator<Client> validator;

    public ClientDBRepo(Validator<Client> val) {
        validator = val;
    }

    @Override
    public Optional<Client> find(Long id) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStmt = conn.prepareStatement("select * from Clients where clientID = ?");
            preparedStmt.setLong(1, id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) {
                Long cid = resultSet.getLong("clientID");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");

                Client client = new Client(cid, name, age, email);
                return Optional.of(client);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Iterable<Client> findAll() {
        List<Client> result = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStmt = conn.prepareStatement("select * from Clients");
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("clientID");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");

                result.add(new Client(id, name, age, email));
            }
            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    public Optional<Client> add(Client client) {
        try {
            validator.validate(client);

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStmt = conn.prepareStatement("insert into Clients(clientID, name, email, age)values (?, ?, ?, ?)");
            preparedStmt.setLong(1, client.getId());
            preparedStmt.setString(2, client.getName());
            preparedStmt.setString(3, client.getEmail());
            preparedStmt.setInt(4, client.getAge());
            preparedStmt.executeUpdate();
        }
        catch (PSQLException ignored) {}
        catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Client> delete(Long id) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStmt = conn.prepareStatement("delete from Clients where clientID = ?");
            preparedStmt.setLong(1, id);
            preparedStmt.executeUpdate();
        }
        catch (PSQLException e) {}
        catch (SQLException e) {
            e.printStackTrace();
        }
        return find(id);
    }

    public Optional<Client> update(Client client) {
        try {
            validator.validate(client);

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStmt = conn.prepareStatement("update Clients set name = ?, email = ?, age = ? where clientID = ?");

            preparedStmt.setString(1, client.getName());
            preparedStmt.setString(2, client.getEmail());
            preparedStmt.setInt(3, client.getAge());
            preparedStmt.setLong(4, client.getId());
            preparedStmt.executeUpdate();
            return Optional.of(client);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
