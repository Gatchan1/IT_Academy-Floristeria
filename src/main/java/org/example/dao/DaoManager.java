package org.example.dao;

public abstract class DaoManager {

    private static DaoManager manager = null;

    public static synchronized void setManager(DaoManager manager) {
        if (DaoManager.manager == null) {
            DaoManager.manager = manager;
        } else {
            throw new IllegalStateException("Maneger ya inicializado");
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