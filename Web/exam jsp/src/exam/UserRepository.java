package exam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/Web-lab8";

    static final String DbUser = "postgres";
    static final String DbPassword = "K14w8m8f";

    public User getUser(String uname) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            con = DriverManager.getConnection(DB_URL, DbUser, DbPassword);

            String query = "select * from users2 where username=?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, uname);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                return null;

            User user = new User();
            user.username = rs.getString("username");
            user.password = rs.getString("password");

            rs.close();
            stmt.close();
            con.close();

            return user;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ignored) {}
        }
        return null;
    }

    public boolean checkUsername(String username) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean exists = false;

        try {
            Class.forName(JDBC_DRIVER);

            con = DriverManager.getConnection(DB_URL, DbUser, DbPassword);

            String query = "select * from users2 where username=?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if(rs.next())
                exists = true;

            rs.close();
            stmt.close();
            con.close();

            return exists;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ignored) {}
        }

        return exists;
    }

    public void addUser(User user) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            con = DriverManager.getConnection(DB_URL, DbUser, DbPassword);

            String query = "insert into users2(username, password) values (?, ?)";
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.username);
            stmt.setString(2, user.password);

            stmt.executeUpdate();

            stmt.close();
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ignored) {}
        }
    }

    public int getUserId(String uname) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            con = DriverManager.getConnection(DB_URL, DbUser, DbPassword);

            String query = "select * from users2 where username=?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, uname);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                return -1;

            int userid = rs.getInt("id");

            rs.close();
            stmt.close();
            con.close();

            return userid;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ignored) {}
        }
        return -1;
    }

    public void update(int id, String uname, int topicid, String text) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            con = DriverManager.getConnection(DB_URL, DbUser, DbPassword);

            String query = "update posts set puser = ?, topicid = ?, text = ?, pdate = ? where id = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, uname);
            stmt.setInt(2, topicid);
            stmt.setString(3, text);
            stmt.setInt(4, 6232020);
            stmt.setInt(5, id);
            stmt.executeUpdate();

            stmt.close();
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ignored) {}
        }
    }

    public void addPost(String uname, int id, String text) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            con = DriverManager.getConnection(DB_URL, DbUser, DbPassword);

            String query = "select * from topics where id = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            String auxquery;
            if(!rs.next()) {
                auxquery = "insert into topics(id, topicname) values (?, ?)";
                stmt = con.prepareStatement(auxquery);
                stmt.setInt(1, id);
                stmt.setString(2, "Unnamed topic");

                stmt.executeUpdate();
            }

            String query2 = "insert into posts(puser, topicid, text, pdate) values (?, ?, ?, ?)";
            stmt = con.prepareStatement(query2);

            stmt.setString(1, uname);
            stmt.setInt(2, id);
            stmt.setString(3, text);
            stmt.setInt(4, 6232020);

            stmt.executeUpdate();

            stmt.close();
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ignored) {}
        }
    }
}
