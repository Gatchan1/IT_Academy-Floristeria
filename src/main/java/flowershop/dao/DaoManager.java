package flowershop.dao;

import flowershop.dao.mysql.MysqlDaoManager;

import java.util.Locale;

public abstract class DaoManager {

    private static DaoManager manager = null;

    /*    public static synchronized void setManager(DaoManager manager) {
            if (DaoManager.manager == null) {
                DaoManager.manager = manager;
            } else {
                throw new IllegalStateException("Maneger ya inicializado");
            }
        }
     */
    public static synchronized void setDatabase(String database) {
        String dbSelection = database.toLowerCase();
        if (manager != null) {
            throw new IllegalStateException("Manager ya inicializado");
        } else if (dbSelection.equals("mysql")) {
            manager = MysqlDaoManager.getInstance();
        } /*else if ( dbSelection.equals("mongo"))) {
            manager = MongoDaoManager.getInstance();
        }*/ else {
            throw new IllegalStateException("La base de datos escogida no es válida");
        }
    }

    public static DaoManager getManager() {
        if (manager == null) {
            throw new IllegalStateException("Manager no inicializado");
        }
        return manager;
    }


    public abstract ProductDao getProductDao();
    public abstract TreeDao getTreeDao();
    public abstract FlowerDao getFlowerDao();
    public abstract DecorationDao getDecorationDao();
    public abstract TicketDao getTicketDao();
}
/*EN EL MAIN se puede usar setManager para establecer si queremos la fabrica de mysql o la de mongo.
Y despues podemos ya podemos usar el mas abstracto DaoManager.getFactory para obtener los daos

public static void main(String[] args) {
        CreateDatabaseMysql.initializeDatabase();

        DaoManager.setManager(MysqlDaoManager.getInstance());

        FlowerDao<Integer> flowerDao = DaoManager.getFactory().getFlowerDao();

        Flower<Integer> flower = flowerDao.read(2);

        System.out.println(flower.toString());
    }