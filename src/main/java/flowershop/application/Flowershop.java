package flowershop.application;

import flowershop.configdb.mysql.CreateDatabaseMysql;
import flowershop.dao.DaoManager;

public class Flowershop {
    private String database;
    static Flowershop instance;
    private DaoManager manager;

    private Flowershop() {
        database = "mysql";
    }

    private static void setInstance() {
        if (instance == null) {
            instance = new Flowershop();
        }
    }

    public static void start() {
        Flowershop.setInstance();
        CreateDatabaseMysql.initializeDatabase();
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
}