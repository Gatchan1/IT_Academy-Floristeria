package flowershop.application;

import flowershop.configdb.mysql.CreateDatabaseMysql;
import flowershop.dao.DaoManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Flowershop {
    private String database;
    static Flowershop instance;
    private DaoManager manager;
    private static final Logger logger = Logger.getLogger(Flowershop.class.getName());

    private Flowershop() {
        database = loadDatabaseConfig();
    }

    private static void setInstance() {
        if (instance == null) {
            instance = new Flowershop();
        }
    }

    public static void start() {
        Flowershop.setInstance();
        instance.loadDatabase(instance.database);
        instance.setDaoManager();
        Menu menu = new Menu(instance.getManager());
        menu.startMenu();
    }

    private void setDaoManager() {
        DaoManager.setManager(database);
        this.manager = DaoManager.getManager();
    }

    public DaoManager getManager() {
        return manager;
    }

    private void loadDatabase(String database) {
        if ("mysql".equalsIgnoreCase(database)) {
            CreateDatabaseMysql.initializeDatabase();
        } else if ("mongo".equalsIgnoreCase(database)) {
            //.getMondoDatabase
        }
    }

    private String loadDatabaseConfig() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.log(Level.SEVERE, "Archivo config.properties no encontrado");
                return "mysql";
            }
            properties.load(input);
            return properties.getProperty("database", "mysql");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la configuración de la base de datos", e);
            return "mysql";
        }
    }
}
