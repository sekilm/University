package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
//@Table
//@SecondaryTable()
public class Student extends BaseEntity<Long> {
    private static final int SERIAL_NUMBER_LENGTH = 16;

    @Column(name = "serial_number", nullable = false, length =
            SERIAL_NUMBER_LENGTH)
    private String serialNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "group_number", nullable = false)
    private Integer groupNumber;

    //@ManyToMany
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch =
            FetchType.EAGER)
    private Set<StudentDiscipline> studentDisciplines = new HashSet<>();


    public Set<Discipline> getDisciplines() {
        studentDisciplines = studentDisciplines == null ? new HashSet<>() :
                studentDisciplines;
        return Collections.unmodifiableSet(
                this.studentDisciplines.stream().
                        map(StudentDiscipline::getDiscipline).
                        collect(Collectors.toSet()));
    }

    public void addDiscipline(Discipline discipline) {
        StudentDiscipline studentDiscipline = new StudentDiscipline();
        studentDiscipline.setDiscipline(discipline);
        studentDiscipline.setStudent(this);
        studentDisciplines.add(studentDiscipline);
    }

    public void addDisciplines(Set<Discipline> disciplines) {
        disciplines.forEach(this::addDiscipline);
    }

    public void addGrade(Discipline discipline, Integer grade) {
        StudentDiscipline studentDiscipline = new StudentDiscipline();
        studentDiscipline.setDiscipline(discipline);
        studentDiscipline.setGrade(grade);
        studentDiscipline.setStudent(this);
        studentDisciplines.add(studentDiscipline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return serialNumber.equals(student.serialNumber);
    }

    @Override
    public int hashCode() {
        return serialNumber.hashCode();
    }

    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", groupNumber=" + groupNumber +
                ", studentDisciplines=" + studentDisciplines +
                "} " + super.toString();
    }
}
