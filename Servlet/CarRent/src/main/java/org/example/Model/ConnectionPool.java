package org.example.Model;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConnectionPool {
    private static final Logger log = Logger.getLogger(ConnectionPool.class.getSimpleName());
    private String url;
    private String user;
    private String password;
    private static List<Connection> connectionPool;
    private static List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 10;
    private static ConnectionPool instance;


    public static synchronized ConnectionPool getInstance() {
       log.debug("get instance of connection pool");
        if (instance == null) {
            try {
                instance = new ConnectionPool();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private ConnectionPool() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ResourceBundle resource = ResourceBundle.getBundle("db");
        user = resource.getString("db.user");
        password = resource.getString("db.password");
        url = resource.getString("db.url");
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        connectionPool = pool;
    }

    public Connection getConnection() {
        log.debug("get connection");
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        log.debug("release connection");
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public void shutdown() throws SQLException {
        log.debug("shutdown connectionPool");
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }

    private static Connection createConnection(String url, String user, String password)
            throws SQLException {
        log.debug("create connection");
        return DriverManager.getConnection(url, user, password);
    }
}
