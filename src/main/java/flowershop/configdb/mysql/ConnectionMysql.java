package flowershop.configdb.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionMysql {

    private static Connection connection;

    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String MULTI_QUERIES = "?allowMultiQueries=true";
    protected static final String DB_NAME = "floristeria";

    private static final Logger logger = Logger.getLogger(ConnectionMysql.class.getName());


    private static Properties getDatabaseProperties() {
        Properties properties = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", PASSWORD);
        return properties;
    }

    public static Connection getInitialConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, getDatabaseProperties());
        }
        return connection;
    }

    public static Connection getDbConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL + DB_NAME, getDatabaseProperties());
        }
        return connection;
    }

    public static Connection getDbConnectionMultiQueries() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL + DB_NAME + MULTI_QUERIES, getDatabaseProperties());
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al cerrar la conexion", e);
            }
        }
    }
}