package flowershop.dao.mysql;

import flowershop.configdb.mysql.ConnectionMysql;
import flowershop.dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlDaoManager extends DaoManager {

    private static final Logger logger = Logger.getLogger(MysqlDaoManager.class.getName());
    private static MysqlDaoManager instance;
    private Connection connection;

    private TreeDao treeDao;
    private FlowerDao flowerDao;
    private DecorationDao decorationDao;
    private TicketDao ticketDao;
    private ProductReaderDao productReaderDao;

    private MysqlDaoManager() {
        try {
            this.connection = ConnectionMysql.getDbConnection();
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