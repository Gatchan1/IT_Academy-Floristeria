package flowershop.configdb.mysql;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateDatabaseMysql {

    private static final Logger logger = Logger.getLogger(CreateDatabaseMysql.class.getName());

    public static void initializeDatabase() {
        if (!existsDatabase()) {
            createDatabase();
            createTables();
            InitialStockMysql.insertInitialStock();
        }
    }

    public static boolean existsDatabase() {
        boolean exists = false;
        try (Connection connection = ConnectionMysql.getInitialConnection();
             Statement statement = connection.createStatement()) {
            String checkDatabaseQuery = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + ConnectionMysql.DB_NAME + "'";
            try (ResultSet resultSet = statement.executeQuery(checkDatabaseQuery)) {
                if (resultSet.next()) {
                    exists = true;
                    logger.info("La base de datos '" + ConnectionMysql.DB_NAME + "' ya existe");
                } else {
                    logger.info("La base de datos '" + ConnectionMysql.DB_NAME + "' no existe");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error verificando la existencia de la base de datos '" + ConnectionMysql.DB_NAME + "': " + e.getMessage(), e);
        }
        return exists;
    }

    public static void createDatabase() {
        try (Connection connection = ConnectionMysql.getInitialConnection();
             PreparedStatement createDBStatement = connection.prepareStatement(QueriesMysql.createDatabaseQuery())) {
            createDBStatement.executeUpdate();
            logger.info("Base de datos '" + ConnectionMysql.DB_NAME + "' lista y operativa");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creando base de datos '" + ConnectionMysql.DB_NAME + "': " + e.getMessage(), e);
        }
    }

    private static void createTables() {
        Connection connection = null;
        try {
            connection = ConnectionMysql.getDbConnection();
            createTable(connection, QueriesMysql.TableProductQuery());
            createTable(connection, QueriesMysql.TableFlowerQuery());
            createTable(connection, QueriesMysql.TableTreeQuery());
            createTable(connection, QueriesMysql.TableDecorationQuery());
            createTable(connection, QueriesMysql.TableTicketQuery());
            createTable(connection, QueriesMysql.TableTicketDetailQuery());
            logger.info("Tablas listas y operativas");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creando tablas: " + e.getMessage(), e);
            if (connection != null) {
                rollbackTables();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error cerrando la conexión: " + e.getMessage(), e);
                }
            }
        }
    }

    private static void createTable(Connection connection, String queryTable) throws SQLException {
        try (PreparedStatement useDbStatement = connection.prepareStatement(queryTable)) {
            useDbStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creando tabla: " + e.getMessage(), e);
            throw e;
        }
    }

    private static void rollbackTables() {
        String[] tables = {"flower", "tree", "decoration", "ticket", "ticket_detail", "product"};
        try (Connection connection = ConnectionMysql.getDbConnection()) {
            for (String table : tables) {
                try (PreparedStatement dropTableStatement = connection.prepareStatement("DROP TABLE IF EXISTS " + table)) {
                    dropTableStatement.executeUpdate();
                    logger.info("Tabla '" + table + "' eliminada.");
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error eliminando tabla '" + table + "': " + e.getMessage(), e);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error en la conexión a la base de datos durante rollback: " + e.getMessage(), e);
        }
    }
}
