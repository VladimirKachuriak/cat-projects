package org.example.Model.DAO;

import org.apache.log4j.Logger;
import org.example.Model.Car;
import org.example.Model.ConnectionPool;
import org.example.Model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private static final Logger log = Logger.getLogger(OrderDAOImpl.class.getSimpleName());
    private static final String SQL_ADD_ORDER = "INSERT INTO orders(idCar, idUser, startDate, endDate, " +
            "withDriver, account, accountDamage, serial, expire, state, message)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE_ORDER = "UPDATE  orders SET idCar = (?), idUser = (?), startDate = (?), endDate = (?), " +
            "withDriver = (?), account = (?), accountDamage = (?), serial = (?), expire = (?), state = (?), message = (?)" +
            "WHERE id = (?);";
    public static final String SQL_GET_ALL_ORDER = "SELECT * FROM orders;";
    public static final String SQL_GET_ALL_ORDER_BY_CAR_ID = "SELECT * FROM orders WHERE idCar = (?);";
    public static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM orders WHERE id = (?);";
    public static final String SQL_GET_ORDER_BY_USER_ID = "SELECT * FROM orders WHERE idUser = (?);";
    public static final String SQL_DELETE_ORDER_BY_CAR_ID = "DELETE FROM orders WHERE idCar = (?);";
    public static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = (?);";
    public static final String SQL_COUNT_ALL_ORDER = "SELECT COUNT(*) FROM orders;";

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_ORDER);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                mapToCar(rs, order);
                orders.add(order);
            }
            log.debug("get all cars");
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        ;
        return orders;
    }

    @Override
    public Order getById(int id) {
        Order order = null;

        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_GET_ORDER_BY_ID);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                order = new Order();
                mapToCar(rs, order);
            }
            log.debug("get order by id");
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        ;
        return order;
    }

    @Override
    public boolean create(Order order) {
        boolean result = false;

        Connection con = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement(SQL_ADD_ORDER)) {
            pst.setInt(1, order.getIdCar());
            pst.setInt(2, order.getIdUser());
            pst.setDate(3, new Date(order.getStart_date().getTime()));
            pst.setDate(4, new Date(order.getEnd_date().getTime()));
            pst.setBoolean(5, order.isWithDriver());
            pst.setInt(6, order.getAccount());
            pst.setInt(7, order.getAccountDamage());
            pst.setString(8, order.getPassportSerial());
            pst.setDate(9, new Date(order.getPassportExpireDate().getTime()));
            pst.setString(10, String.valueOf(order.getState()));
            pst.setString(11, order.getMessage());

            result = pst.executeUpdate() > 0;
            log.debug("create new order");
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return result;
    }

    @Override
    public boolean update(Order order) {
        boolean result = false;

        Connection con = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement(SQL_UPDATE_ORDER)) {
            pst.setInt(1, order.getIdCar());
            pst.setInt(2, order.getIdUser());
            pst.setDate(3, new Date(order.getStart_date().getTime()));
            pst.setDate(4, new Date(order.getEnd_date().getTime()));
            pst.setBoolean(5, order.isWithDriver());
            pst.setInt(6, order.getAccount());
            pst.setInt(7, order.getAccountDamage());
            pst.setString(8, order.getPassportSerial());
            pst.setDate(9, new Date(order.getPassportExpireDate().getTime()));
            pst.setString(10, String.valueOf(order.getState()));
            pst.setString(11, order.getMessage());
            pst.setInt(12, order.getId());

            result = pst.executeUpdate() > 0;
            log.debug("update order with id" + order.getId());
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        Connection con = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement(SQL_DELETE_ORDER_BY_ID)) {
            pst.setInt(1, id);
            result = pst.executeUpdate() > 0;
            log.debug("delete car by id:" + id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return result;
    }

    @Override
    public boolean deleteOrderByCarId(int id) {
        boolean result = false;
        Connection con = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement(SQL_DELETE_ORDER_BY_CAR_ID)) {
            pst.setInt(1, id);
            result = pst.executeUpdate() > 0;
            log.debug("delete order by car id:" + id);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return result;
    }

    @Override
    public List<Order> getAllOrderByUserId(int id) {
        List<Order> orders = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_GET_ORDER_BY_USER_ID);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                mapToCar(rs, order);
                orders.add(order);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        ;
        return orders;
    }

    @Override
    public List<Order> getAllOrderByCarId() {
        List<Order> orders = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_ORDER_BY_CAR_ID);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                mapToCar(rs, order);
                orders.add(order);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        ;
        return orders;
    }

    @Override
    public int countAllOrders() {
        Connection con = ConnectionPool.getInstance().getConnection();
        int res = 0;
        try (PreparedStatement pst = con.prepareStatement(SQL_COUNT_ALL_ORDER)) {
            ResultSet rs = pst.executeQuery();
            res = rs.getInt(1);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return res;
    }


    private void mapToCar(ResultSet rs, Order order) throws SQLException {
        order.setId(rs.getInt("id"));
        order.setIdUser(rs.getInt("idUser"));
        order.setIdCar(rs.getInt("idCar"));
        order.setStart_date(rs.getDate("startDate"));
        order.setEnd_date(rs.getDate("endDate"));
        order.setWithDriver(rs.getBoolean("withDriver"));
        order.setAccount(rs.getInt("account"));
        order.setAccountDamage(rs.getInt("accountDamage"));
        order.setPassportSerial(rs.getString("serial"));
        order.setPassportExpireDate(rs.getDate("expire"));
        order.setState(Order.State.valueOf(rs.getString("state")));
        order.setMessage(rs.getString("message"));
    }
}
