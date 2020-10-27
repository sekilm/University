package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by radu.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Discipline extends BaseEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "teacher", nullable = false)
    private String teacher;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StudentDiscipline> studentDisciplines = new HashSet<>();

    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(
                studentDisciplines.stream()
                        .map(StudentDiscipline::getStudent)
                        .collect(Collectors.toSet())
        );
    }

    public void addStudent(Student student) {
        StudentDiscipline studentDiscipline = new StudentDiscipline();
        studentDiscipline.setStudent(student);
        studentDiscipline.setDiscipline(this);
        studentDisciplines.add(studentDiscipline);
    }

    public void addGrade(Student student, Integer grade) {
        StudentDiscipline studentDiscipline = new StudentDiscipline();
        studentDiscipline.setStudent(student);
        studentDiscipline.setGrade(grade);
        studentDiscipline.setDiscipline(this);
        studentDisciplines.add(studentDiscipline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discipline that = (Discipline) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", credits=" + credits +
                "} " + super.toString();
    }
}
