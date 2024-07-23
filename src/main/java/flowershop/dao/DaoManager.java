package flowershop.dao;

import flowershop.dao.mysql.MysqlDaoManager;

import java.util.Locale;

public abstract class DaoManager {

    private static DaoManager instance = null;
    //public abstract ProductDao getProductDao();
    public abstract TreeDao getTreeDao();
    public abstract FlowerDao getFlowerDao();
    public abstract DecorationDao getDecorationDao();
    public abstract TicketDao getTicketDao();


    public static synchronized void setManager(String database) {
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