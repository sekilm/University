package ro.ubb.jdbc;

/**
 * Created by radu.
 */
public class Student {
    private Long id;
    private String name;
    private int groupNumber;

    public Student() {
    }

    public Student(Long id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.groupNumber = grade;
    }

    public Student(String name, int grade) {
        this(null, name, grade);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", groupNumber=" + groupNumber +
                '}';
    }
}
