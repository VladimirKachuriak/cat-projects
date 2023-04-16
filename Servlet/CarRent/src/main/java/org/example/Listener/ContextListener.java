package org.example.Listener;

import org.apache.log4j.Logger;
import org.example.Filter.EncodingFilter;
import org.example.Model.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(ContextListener.class.getSimpleName());
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.debug("Start context listener");
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("finish contextListener");
    }
}
