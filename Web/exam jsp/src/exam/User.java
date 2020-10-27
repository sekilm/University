package exam;

public class User {
    public String username;
    public String password;

    public User() {}

    public User(String user, String pass) {
        username = user;
        password = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
