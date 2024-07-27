package flowershop.dao.mysql;

import flowershop.dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//TODO: import MysqlConnection
//TODO: import Product

public class MysqlDaoManager extends DaoManager {

    private static final Logger logger = Logger.getLogger(MysqlDaoManager.class.getName());
    private static MysqlDaoManager instance;
    private Connection connection;

    private TreeDao treeDao;
    private FlowerDao flowerDao;
    private DecorationDao decorationDao;
    private TicketDao ticketDao;
    private ProductReaderDao<Product> productReaderDao;

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

    public static MysqlDaoManager getInstance() {
        if (instance == null) {
            instance = new MysqlDaoManager();
        }
        return instance;
    }


    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public ProductReaderDao getProductReaderDao() {
        if (productReaderDao == null) {
            productReaderDao = new MysqlProductReaderDao(connection);
        }
        return productReaderDao;
    }

    @Override
    public TreeDao getTreeDao() {
        if (treeDao == null) {
            treeDao = new MysqlTreeDao(connection);
        }
        return treeDao;
    }

    @Override
    public FlowerDao getFlowerDao() {
        if (flowerDao == null) {
            flowerDao = new MysqlFlowerDao(connection);
        }
        return flowerDao;
    }

    @Override
    public DecorationDao getDecorationDao() {
        if (decorationDao == null) {
            decorationDao = new MysqlDecorationDao(connection);
        }
        return decorationDao;
    }

    @Override
    public TicketDao getTicketDao() {
        if (ticketDao == null) {
            ticketDao = new MysqlTicketDao(connection);
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