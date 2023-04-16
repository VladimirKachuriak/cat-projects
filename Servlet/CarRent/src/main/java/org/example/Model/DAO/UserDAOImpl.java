package org.example.Model.DAO;

import org.apache.log4j.Logger;
import org.example.Model.Car;
import org.example.Model.ConnectionPool;
import org.example.Model.Order;
import org.example.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{
    private static final Logger log = Logger.getLogger(UserDAOImpl.class.getSimpleName());
    private static final String SQL_ADD_USER = "INSERT INTO users(login,firstname,lastname,email,password,role,status,phone_number,account)" +
            "VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET login = (?),firstname = (?),lastname = (?),email = (?),password = (?),role = (?),status=(?),phone_number =(?),account=(?) WHERE id = (?);";
    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM users;";
    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = (?);";
    public static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id = (?);";
    public static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = (?) AND password = (?);";
    public static final String SQL_BLOCK_USER_BY_ID = "UPDATE users SET status=? WHERE id=?;";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id=(?);";

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_FIND_ALL_USERS);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                User user = new User();
                mapToUser(rs, user);
                users.add(user);
            }
            log.debug("get all users");
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return users;
    }

    @Override
    public User getById(int id) {
        User user = null;

        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_FIND_USER_BY_ID);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                user = new User();
                mapToUser(rs, user);
            }
            log.debug("get user by id:"+id);
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return user;
    }

    @Override
    public boolean create(User user) {
        boolean result = false;

        Connection con = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement(SQL_ADD_USER)) {
            pst.setString(1, user.getLogin());
            pst.setString(2, user.getFirstName());
            pst.setString(3, user.getLastName());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getPassword());
            pst.setString(6, user.getRole().toString());
            pst.setString(7, user.getStatus().toString());
            pst.setString(8, user.getPhoneNumber());
            pst.setInt(9, 0);

            result = pst.executeUpdate()>0;
            log.debug("create new user");
            ConnectionPool.getInstance().releaseConnection(con);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;

        Connection con = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement(SQL_UPDATE_USER)) {
            pst.setString(1, user.getLogin());
            pst.setString(2, user.getFirstName());
            pst.setString(3, user.getLastName());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getPassword());
            pst.setString(6, String.valueOf(user.getRole()));
            pst.setString(7, String.valueOf(user.getStatus()));
            pst.setString(8, user.getPhoneNumber());
            pst.setInt(9, user.getAccount());
            pst.setInt(10, user.getId());
            result = pst.executeUpdate()>0;
            ConnectionPool.getInstance().releaseConnection(con);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        Connection con = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            pst.setInt(1, id);
            result = pst.executeUpdate()>0;
            ConnectionPool.getInstance().releaseConnection(con);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }
    private void mapToUser(ResultSet rs, User user) throws SQLException {
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setFirstName(rs.getString("firstname"));
        user.setLastName(rs.getString("lastname"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(User.Role.valueOf(rs.getString("role")));
        user.setStatus(User.Status.valueOf(rs.getString("status")));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAccount(rs.getInt("account"));
    }

    @Override
    public User findByLogin(String login) {
        User user = null;

        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            pst.setString(1,login);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                user = new User();
                mapToUser(rs, user);
            }
            log.debug("get user by login:"+login);
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return user;
    }
}
