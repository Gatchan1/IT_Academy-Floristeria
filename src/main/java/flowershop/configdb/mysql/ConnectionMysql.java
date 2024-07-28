package flowershop.configdb.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStream;
import java.io.IOException;

public class ConnectionMysql {

    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String MULTI_QUERIES = "?allowMultiQueries=true";
    protected static final String DB_NAME = "floristeria";

    private static final Logger logger = Logger.getLogger(ConnectionMysql.class.getName());

    private static Properties getDatabaseProperties() {
        Properties properties = new Properties();
        try (InputStream input = ConnectionMysql.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.log(Level.SEVERE, "No se pudo encontrar el archivo config.properties");
                return properties;
            }
            properties.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la configuración de la base de datos", e);
        }
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
                logger.log(Level.SEVERE, "Error al cerrar la conexión", e);
            }
        }
    }
}
