package lab8;

public class User {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String email;

    public User() {}

    public User(String user, String pass, String fname, String lname, String em) {
        username = user;
        password = pass;
        firstName = fname;
        lastName = lname;
        email = em;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
