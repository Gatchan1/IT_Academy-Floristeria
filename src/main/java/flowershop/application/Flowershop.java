package flowershop.application;
//TODO: import DaoManager
//TODO: import CreateDatabase
//TODO: import Menu

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
         instance.setDaoManager();
         CreateDatabase.create(instance.database);
         Menu.start(manager);
    }

    private void setDaoManager() {
         DaoManager.setManager(database);
         this.manager = DaoManager.getManager();
    }
}

// Habíamos comentado la posibilidad de dejar creado un método estilo public void setDatabase(String db),
// pero no se me ocurre cómo compaginarlo con lo de asignar la database en el constructor.
// Así que por ahora no lo he creado.