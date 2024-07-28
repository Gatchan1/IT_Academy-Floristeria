package flowershop.dao;

import flowershop.dao.mysql.MysqlDaoManager;

public abstract class DaoManager {

    private static DaoManager instance = null;

    public static void setManager(String database) {
        if (instance != null) {
            throw new IllegalStateException("Manager ya inicializado");
        } else if (database.equalsIgnoreCase("mysql")) {
            instance = MysqlDaoManager.getInstance();
        } /*else if ( database.equalsIgnoreCase("mongo"))) {
            instance = MongoDaoManager.getInstance();
        }*/ else {
            throw new IllegalStateException("La base de datos escogida no es válida");
        }
    }

    public static DaoManager getManager() {
        if (instance == null) {
            throw new IllegalStateException("Manager no inicializado");
        }
        return instance;
    }

    public abstract TreeDao getTreeDao();
    public abstract FlowerDao getFlowerDao();
    public abstract DecorationDao getDecorationDao();
    public abstract TicketDao getTicketDao();

    public abstract ProductReaderDao getProductReaderDao();
}