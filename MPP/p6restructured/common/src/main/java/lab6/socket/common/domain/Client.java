package lab6.socket.common.domain;

public class Client extends BaseEntity<Long> {
    private String name, email;
    private int age;

    public Client(Long _id, String _name, int _age , String _email){
        super(_id);
        name = _name;
        email = _email;
        age = _age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }

    public void setName(String _name) { name = _name; }
    public void setAge(int _age) { age = _age; }
    public void setEmail(String _email) { email = _email; }

    @Override
    public String toString() {
        return "Client{ id = " + id + ", name = " + name + ", age = " + age + ", email = " + email + " }";
    }
}
