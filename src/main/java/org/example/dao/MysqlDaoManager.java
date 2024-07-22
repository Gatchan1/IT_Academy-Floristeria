package org.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//Por no abrir otro paquete, pero esto iria en la implementacion dao de mysql:
// package.org.example.dao.mysql;
public class MysqlDaoManager extends DaoManager {

    private static final Logger logger = Logger.getLogger(DaoFactoryJdbc.class.getName());
    private static MysqlDaoManager instance;
    private Connection connection;

    private TreeDao<Integer> treeDao;
    private FlowerDao<Integer> flowerDao;
    private DecorationDao<Integer> decorationDao;
    private TicketDao<Integer> ticketDao;
    private ProductDao<Integer> productDao;

    private MysqlDaoManager() {
        try {

            //AQUI SE LE PASA A LA CONEXION A LA BASE DE DATOS
            //se me olvido comentarlo pero estaria bien crear un paquete mysql
            //para la conexion o conexiones y tb cargar la bbdd desde el ide

            this.connection = MysqlConnection.getDbConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener la conexión a la base de datos.", e);
        }
    }

    public static synchronized MysqlDaoManager getInstance() {
        if (instance == null) {
            instance = new MysqlDaoManager();
        }
        return instance;
    }


    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public ProductDao getProductDao() {
        if (productDao == null) {
            productDao = new ProductDaoMsql(connection);
        }
        return productDao;
    }

    @Override
    public TreeDao getTreeDao() {
        if (treeDao == null) {
            treeDao = new TreeDaoMysql(connection);
        }
        return treeDao;
    }

    @Override
    public FlowerDao getFlowerDao() {
        if (flowerDao == null) {
            flowerDao = new FlowerDaoMysql(connection);
        }
        return flowerDao;
    }

    @Override
    public DecorationDao getDecorationDao() {
        if (decorationDao == null) {
            decorationDao = new DecorationDaoMysql(connection);
        }
        return decorationDao;
    }

    @Override
    public TicketDao getTicketDao() {
        if (ticketDao == null) {
            ticketDao = new TicketDaoJdbc(connection);
        }
        return ticketDao;
    }
}
/*EJEMPLO DE CONNECTION:
public static Connection getDbConnection() throws SQLException {

        return DriverManager.getConnection(URL + DB_NAME, getDatabaseProperties());
    }

propiedades de esa conexion:
private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String MULTI_QUERIES = "?allowMultiQueries=true";
    protected static final String DB_NAME = "blackrose";

    private static Properties getDatabaseProperties() {
        Properties properties = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", PASSWORD);
        return properties;
    }
 */