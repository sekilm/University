package ro.ubb.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu.
 * <p>
 * <p>
 * Result set (rs)
 * <p>
 * id, name, groupnumber   <--- pos 0
 * 1, "s1", 914            <--- pos 1 (after the first call of rs.next())
 * 2, "s2", 914            <--- pos 2
 */
public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/catalog";
    private static final String USER = System.getProperty("username");
    private static final String PASSWORD = System.getProperty("password");

    public static void main(String[] args) {
        System.out.println("hello");

        try {
            addStudent();

            System.out.println("all students: ");
            List<Student> students = getAllStudents();
            students.stream()
                    .forEach(System.out::println);

            Student studentUpdate = students.get(0);
            studentUpdate.setName("update");
            updateStudent(studentUpdate);
            students = getAllStudents();
            System.out.println("\nafter update");
            students.stream()
                    .forEach(System.out::println);

            deleteById(students.get(0).getId());
            students = getAllStudents();
            System.out.println("\nafter delete");
            students.stream()
                    .forEach(System.out::println);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteById(Long id) throws SQLException {
        String sql = "delete from student where id=?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    private static void updateStudent(Student student) throws SQLException {
        String sql = "update student set name=?, groupnumber=? where id=?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setString(1, student.getName());
        preparedStatement.setInt(2, student.getGroupNumber());
        preparedStatement.setLong(3, student.getId());
        preparedStatement.executeUpdate();
    }

    private static List<Student> getAllStudents() throws SQLException {
        List<Student> result = new ArrayList<>();
        String sql = "select * from student";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            int groupNumber = resultSet.getInt("groupnumber");

            result.add(new Student(id, name, groupNumber));
        }

        return result;
    }

    private static void addStudent() throws SQLException {
        Student student = new Student("s1", 914);
        String sql = "insert into student (name, groupnumber) values(?,?)";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setString(1, student.getName());
        preparedStatement.setInt(2, student.getGroupNumber());
        preparedStatement.executeUpdate();

    }
}
