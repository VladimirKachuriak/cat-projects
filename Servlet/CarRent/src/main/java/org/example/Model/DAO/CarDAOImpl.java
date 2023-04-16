package org.example.Model.DAO;

import org.apache.log4j.Logger;
import org.example.Controller.UserOrderServlet;
import org.example.Model.Car;
import org.example.Model.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    private static final Logger log = Logger.getLogger(CarDAOImpl.class.getSimpleName());
    private static final String SQL_ADD_CAR = "INSERT INTO cars(brand,model,release_date,state,auto_class,price)" +
            "VALUES(?,?,?,?,?,?)";
    private static final String SQL_UPDATE_CAR = "UPDATE cars SET brand = (?),model = (?),release_date = (?),state = (?),auto_class = (?),price = (?) WHERE id = (?);";
    private static final String SQL_GET_CAR_BY_ID = "SELECT *FROM cars WHERE id = ?;";
    private static final String SQL_GET_ALL_CAR = "SELECT *FROM cars";
    private static final String SQL_DELETE_CAR_BY_ID = "DELETE FROM cars WHERE id=(?);";
    public static final String SQL_COUNT_ALL_CAR = "SELECT COUNT(*) FROM cars;";
    public static final String SQL_GET_ALL_BRAND = "SELECT DISTINCT brand FROM cars;";
    private static final String SQL_GET_ALL_OFFSET = "SELECT *FROM cars LIMIT ?,?;";


    private static final String SQL_COUNT_MATCH_BRAND = "SELECT COUNT(*) FROM cars WHERE brand = ? ;";

    private static final String SQL_COUNT_MATCH_BRAND_CLASS = "SELECT COUNT(*) FROM cars WHERE brand = ? AND auto_class = ? ;";

    private static final String SQL_COUNT_MATCH_CLASS = "SELECT COUNT(*) FROM cars WHERE  auto_class = ? ;";


    private static final String SQL_SORT_BY_PRICE_ASC_OFFSET = "SELECT *FROM cars ORDER BY price ASC LIMIT ?,?;";
    private static final String SQL_SORT_BY_PRICE_DESC_OFFSET = "SELECT *FROM cars ORDER BY price DESC LIMIT ?,?;";
    private static final String SQL_SORT_BY_NAME_ASC_OFFSET = "SELECT *FROM cars ORDER BY model ASC LIMIT ?,?;";
    private static final String SQL_SORT_BY_NAME_DESC_OFFSET = "SELECT *FROM cars ORDER BY model DESC LIMIT ?,?;";

    private static final String SQL_SORT_BY_PRICE_AND_MATCH_BRAND_ASC_OFFSET = "SELECT *FROM cars WHERE brand = ? ORDER BY price ASC LIMIT ?,?;";
    private static final String SQL_SORT_BY_PRICE_AND_MATCH_BRAND_DESC_OFFSET = "SELECT *FROM cars WHERE brand = ? ORDER BY price DESC LIMIT ?,?;";
    private static final String SQL_SORT_BY_NAME_AND_MATCH_BRAND_ASC_OFFSET = "SELECT *FROM cars WHERE brand = ? ORDER BY model ASC LIMIT ?,?;";
    private static final String SQL_SORT_BY_NAME_AND_MATCH_BRAND_DESC_OFFSET = "SELECT *FROM cars WHERE brand = ? ORDER BY model DESC LIMIT ?,?;";

    private static final String SQL_SORT_BY_PRICE_AND_MATCH_BRAND_CLASS_ASC_OFFSET = "SELECT *FROM cars WHERE brand = ? AND auto_class = ? ORDER BY price ASC LIMIT ?,?;";
    private static final String SQL_SORT_BY_PRICE_AND_MATCH_BRAND_CLASS_DESC_OFFSET = "SELECT *FROM cars WHERE brand = ? AND auto_class = ? ORDER BY price DESC LIMIT ?,?;";
    private static final String SQL_SORT_BY_NAME_AND_MATCH_BRAND_CLASS_ASC_OFFSET = "SELECT *FROM cars WHERE brand = ? AND auto_class = ? ORDER BY model ASC LIMIT ?,?;";
    private static final String SQL_SORT_BY_NAME_AND_MATCH_BRAND_CLASS_DESC_OFFSET = "SELECT *FROM cars WHERE brand = ? AND auto_class = ? ORDER BY model DESC LIMIT ?,?;";

    private static final String SQL_SORT_BY_PRICE_AND_MATCH_CLASS_ASC_OFFSET = "SELECT *FROM cars WHERE  auto_class = ? ORDER BY price ASC LIMIT ?,?;";
    private static final String SQL_SORT_BY_PRICE_AND_MATCH_CLASS_DESC_OFFSET = "SELECT *FROM cars WHERE auto_class = ? ORDER BY price DESC LIMIT ?,?;";
    private static final String SQL_SORT_BY_NAME_AND_MATCH_CLASS_ASC_OFFSET = "SELECT *FROM cars WHERE auto_class = ? ORDER BY model ASC LIMIT ?,?;";
    private static final String SQL_SORT_BY_NAME_AND_MATCH_CLASS_DESC_OFFSET = "SELECT *FROM cars WHERE auto_class = ? ORDER BY model DESC LIMIT ?,?;";
    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_CAR);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
            log.debug("get all cars from database");
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public Car getById(int id) {
        Car car = null;
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_GET_CAR_BY_ID);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                car = new Car();
                mapToCar(rs, car);
            }
            log.debug("get car by id:"+id);
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return car;
    }

    @Override
    public boolean create(Car car) {
        boolean result = false;

        Connection con = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement(SQL_ADD_CAR)) {
            pst.setString(1, car.getBrand());
            pst.setString(2, car.getModel());
            pst.setDate(3, new java.sql.Date(car.getReleaseDate().getTime()));
            pst.setString(4, "AVAIL");
            pst.setString(5, car.getAutoClass().toString());
            pst.setInt(6, car.getPrice());

            result = pst.executeUpdate()>0;
            ConnectionPool.getInstance().releaseConnection(con);
            log.debug("create new car");
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Car car) {
        boolean result = false;

        Connection con = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement(SQL_UPDATE_CAR)) {
            pst.setString(1, car.getBrand());
            pst.setString(2, car.getModel());
            pst.setDate(3,  new java.sql.Date(car.getReleaseDate().getTime()));
            pst.setString(4, String.valueOf(car.getState()));
            pst.setString(5, String.valueOf(car.getAutoClass()));
            pst.setInt(6, car.getPrice());
            pst.setInt(7, car.getId());
            result = pst.executeUpdate()>0;
            ConnectionPool.getInstance().releaseConnection(con);
            log.debug("update car");
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
        try (PreparedStatement pst = con.prepareStatement(SQL_DELETE_CAR_BY_ID)) {
            pst.setInt(1, id);
            result = pst.executeUpdate()>0;
            ConnectionPool.getInstance().releaseConnection(con);
            log.debug("delete car with id:"+id);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    private void mapToCar(ResultSet rs, Car car) throws SQLException {
        car.setId(rs.getInt("id"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setReleaseDate(rs.getDate("release_date"));
        car.setState(Car.State.valueOf(rs.getString("state")));
        car.setAutoClass(Car.Class.valueOf(rs.getString("auto_class")));
        car.setPrice(rs.getInt("price"));
    }

    @Override
    public int countAllCars() {
        Connection con = ConnectionPool.getInstance().getConnection();
        int res = 0;
        try (PreparedStatement pst = con.prepareStatement(SQL_COUNT_ALL_CAR)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
            log.debug("count all cars");
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return res;
    }


    @Override
    public List<String> getBrands() {
        List<String> brands = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BRAND)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                brands.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return brands;
    }

    @Override
    public List<Car> offSet(int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_OFFSET);
            pst.setInt(1,start);
            pst.setInt(2,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public int countMatchBrand(String brand) {
        Connection con = ConnectionPool.getInstance().getConnection();
        int res = 0;
        try (PreparedStatement pst = con.prepareStatement(SQL_COUNT_MATCH_BRAND)) {
            pst.setString(1,brand);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return res;
    }

    @Override
    public int countMatchClass(String rate) {
        Connection con = ConnectionPool.getInstance().getConnection();
        int res = 0;
        try (PreparedStatement pst = con.prepareStatement(SQL_COUNT_MATCH_CLASS)) {
            pst.setString(1,rate);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return res;
    }

    @Override
    public int countMatchBrandAndClass(String brand,String rate) {
        Connection con = ConnectionPool.getInstance().getConnection();
        int res = 0;
        try (PreparedStatement pst = con.prepareStatement(SQL_COUNT_MATCH_BRAND_CLASS)) {
            pst.setString(1,brand);
            pst.setString(2,rate);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return res;
    }

    @Override
    public List<Car> sortByPriceASC(int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_PRICE_ASC_OFFSET);
            pst.setInt(1,start);
            pst.setInt(2,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByPriceDESC(int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_PRICE_DESC_OFFSET);
            pst.setInt(1,start);
            pst.setInt(2,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByNameASC(int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_NAME_ASC_OFFSET);
            pst.setInt(1,start);
            pst.setInt(2,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByNameDESC(int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_NAME_DESC_OFFSET);
            pst.setInt(1,start);
            pst.setInt(2,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByPriceMatchBrandASC(String brand, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_PRICE_AND_MATCH_BRAND_ASC_OFFSET);
            pst.setString(1,brand);
            pst.setInt(2,start);
            pst.setInt(3,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByPriceMatchBrandDESC(String brand, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_PRICE_AND_MATCH_BRAND_DESC_OFFSET);
            pst.setString(1,brand);
            pst.setInt(2,start);
            pst.setInt(3,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByNameMatchBrandASC(String brand, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_NAME_AND_MATCH_BRAND_ASC_OFFSET);
            pst.setString(1,brand);
            pst.setInt(2,start);
            pst.setInt(3,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByNameMatchBrandDESC(String brand, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_NAME_AND_MATCH_BRAND_DESC_OFFSET);
            pst.setString(1,brand);
            pst.setInt(2,start);
            pst.setInt(3,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByPriceMatchRateASC(String rate, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_PRICE_AND_MATCH_CLASS_ASC_OFFSET);
            pst.setString(1,rate);
            pst.setInt(2,start);
            pst.setInt(3,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByPriceMatchRateDESC(String rate, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_PRICE_AND_MATCH_CLASS_DESC_OFFSET);
            pst.setString(1,rate);
            pst.setInt(2,start);
            pst.setInt(3,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByNameMatchRateASC(String rate, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_NAME_AND_MATCH_CLASS_ASC_OFFSET);
            pst.setString(1,rate);
            pst.setInt(2,start);
            pst.setInt(3,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByNameMatchRateDESC(String rate, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_NAME_AND_MATCH_CLASS_DESC_OFFSET);
            pst.setString(1,rate);
            pst.setInt(2,start);
            pst.setInt(3,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByPriceMatchBrandAndClassASC(String brand, String auto_class, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_PRICE_AND_MATCH_BRAND_CLASS_ASC_OFFSET);
            pst.setString(1,brand);
            pst.setString(2,auto_class);
            pst.setInt(3,start);
            pst.setInt(4,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByPriceMatchBrandAndClassDESC(String brand, String auto_class, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_PRICE_AND_MATCH_BRAND_CLASS_DESC_OFFSET);
            pst.setString(1,brand);
            pst.setString(2,auto_class);
            pst.setInt(3,start);
            pst.setInt(4,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByNameMatchBrandAndClassASC(String brand, String auto_class, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_NAME_AND_MATCH_BRAND_CLASS_ASC_OFFSET);
            pst.setString(1,brand);
            pst.setString(2,auto_class);
            pst.setInt(3,start);
            pst.setInt(4,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }

    @Override
    public List<Car> sortByNameMatchBrandAndClassDESC(String brand, String auto_class, int start, int limit) {
        List<Car> cars = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(SQL_SORT_BY_NAME_AND_MATCH_BRAND_CLASS_DESC_OFFSET);
            pst.setString(1,brand);
            pst.setString(2,auto_class);
            pst.setInt(3,start);
            pst.setInt(4,limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Car car = new Car();
                mapToCar(rs, car);
                cars.add(car);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionPool.getInstance().releaseConnection(con);
        };
        return cars;
    }


}
