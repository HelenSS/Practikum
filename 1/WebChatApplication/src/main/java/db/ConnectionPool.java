package db;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String URL = "jdbc:mysql://localhost:3306/mychat";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "Helen";
    private static final String PASSWORD = "80163251255";
    private static final int MAX_TOTAL = 10;
    private static DataSource dataSource;
    private static Logger logger = Logger.getLogger(ConnectionPool.class.getName());

    static {
        DriverAdapterCPDS cpds = new DriverAdapterCPDS();
        try {
            cpds.setDriver(DRIVER);
            cpds.setUrl(URL);
            cpds.setUser(USERNAME);
            cpds.setPassword(PASSWORD);

            SharedPoolDataSource pool = new SharedPoolDataSource();
            pool.setConnectionPoolDataSource(cpds);
            pool.setMaxTotal(MAX_TOTAL);
            dataSource = pool;
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}