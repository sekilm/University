package lab8;

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

            String query = "select * from Users where username=?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, uname);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                return null;

            User user = new User();
            user.username = rs.getString("username");
            user.password = rs.getString("password");
            user.firstName = rs.getString("firstname");
            user.lastName = rs.getString("lastname");
            user.email = rs.getString("email");

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

            String query = "select * from Users where username=?";
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

            String query = "insert into Users(username, password, firstName, lastName, email) values (?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.username);
            stmt.setString(2, user.password);
            stmt.setString(3, user.firstName);
            stmt.setString(4, user.lastName);
            stmt.setString(5, user.email);

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

    public void addImageToUser(String imagePath, String username) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            con = DriverManager.getConnection(DB_URL, DbUser, DbPassword);

            String query = "insert into images(path, owner) values (?, ?)";
            stmt = con.prepareStatement(query);

            stmt.setString(1, imagePath);
            stmt.setString(2, username);

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

    public List<Image> getImages(String user) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            con = DriverManager.getConnection(DB_URL, DbUser, DbPassword);

            String query = "select * from images where owner <> ?";
            stmt = con.prepareStatement(query);

            stmt.setString(1, user);

            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                return null;

            List<Image> images = new ArrayList<>();
            do {
                String path = rs.getString("path");
                String owner = rs.getString("owner");
                int votes = rs.getInt("votes");
                images.add(new Image(path, owner, votes));
            } while(rs.next());

            stmt.close();
            con.close();

            return images;
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

    public void vote(String path, String uname) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            con = DriverManager.getConnection(DB_URL, DbUser, DbPassword);

            String query = "select * from votes where image=? and username=?";
            stmt = con.prepareStatement(query);

            stmt.setString(1, path);
            stmt.setString(2, uname);

            ResultSet rs = stmt.executeQuery();

            if(rs.next())
                return;

            query = "insert into votes (image, username) values (?, ?)";
            stmt = con.prepareStatement(query);

            stmt.setString(1, path);
            stmt.setString(2, uname);

            stmt.executeUpdate();

            query = "update images set votes = votes + 1 where path=?";
            stmt = con.prepareStatement(query);

            stmt.setString(1, path);

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

    public List<Image> getTopImages(int numberOfImages) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            con = DriverManager.getConnection(DB_URL, DbUser, DbPassword);

            String query = "select * from images order by votes desc limit ?";
            stmt = con.prepareStatement(query);

            stmt.setInt(1, numberOfImages);

            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                return null;

            List<Image> images = new ArrayList<>();
            do {
                String path = rs.getString("path");
                String owner = rs.getString("owner");
                int votes = rs.getInt("votes");
                images.add(new Image(path, owner, votes));
            } while(rs.next());

            stmt.close();
            con.close();

            return images;
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
}
