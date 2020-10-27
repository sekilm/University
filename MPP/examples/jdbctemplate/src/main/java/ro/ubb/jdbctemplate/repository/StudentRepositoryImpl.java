package ro.ubb.jdbctemplate.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import ro.ubb.jdbctemplate.domain.Student;

import java.util.Arrays;
import java.util.List;

/**
 * author: radu
 */
public class StudentRepositoryImpl implements StudentRepository {
    @Autowired
    private JdbcOperations jdbcOperations;



    @Override
    public List<Student> findAll() {
        String sql = "select * from student";
        return jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            int grade = rs.getInt("grade");

            return new Student(id, name, grade);
        });
    }

    @Override
    public void save(Student student) {
        String sql = "insert into student (name, grade) values (?,?)";
        jdbcOperations.update(sql, student.getName(), student.getGrade());
    }

    @Override
    public void update(Student student) {
        String sql = "update student set name=?, grade=? where id=?";
        jdbcOperations.update(sql, student.getName(), student.getGrade(),
                              student.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from student where id=?";
        jdbcOperations.update(sql, id);
    }
}
