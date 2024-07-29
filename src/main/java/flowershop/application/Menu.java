package flowershop.application;

import flowershop.dao.DaoManager;
import flowershop.helpers.Input;
import flowershop.configdb.mysql.ConnectionMysql;

public class Menu {

    private final ProductController productController;
    private final TicketController ticketController;
    //private final StockController stockController;

    private static final int MIN_OPTION = 0;
    private static final int MAX_OPTION = 9;

    public Menu(DaoManager daoManager) {
        this.productController = ProductController.getInstance(daoManager);
        this.ticketController = TicketController.getInstance(daoManager);
        // this.stockController = StockController.getInstance(daoManager);
    }

    private static int showMenu() {

        System.out.println("\n _________________________________________________");
        System.out.println("|           Menu Principal Floristería            |");
        System.out.println("|-------------------------------------------------|");
        System.out.println("|                                                 |");
        System.out.println("| 1. Crear un producto                            |");
        System.out.println("| 2. Eliminar un producto                         |");
        System.out.println("| 3. Actualizar stock de un producto              |");
        System.out.println("| 4. Stock por categoría                          |");
        System.out.println("| 5. Stock de productos                           |");
        System.out.println("| 6. Valor total del stock                        |");
        System.out.println("| 7. Crear un ticket                              |");
        System.out.println("| 8. Listar tickets                               |");
        System.out.println("| 9. Ingresos por ventas                          |");
        System.out.println("| 0. Salir                                        |");
        System.out.println("|                                                 |");
        System.out.println("|_________________________________________________|");

        return Input.readIntInRange(
                "Por favor, introduce un número para la opción deseada: ", MIN_OPTION, MAX_OPTION);
    }

    public void startMenu() {

        boolean exit = false;

        do {
            switch (showMenu()) {
                case 1:
                    productController.addProduct();
                    break;
                case 2:
                    productController.deleteProduct();
                    break;
                case 3:
                    productController.updateProductStock();
                    break;
                case 4:
                    //stockController.;
                    break;
                case 5:
                    //stockController.;
                    break;
                case 6:
                    //stockController.;
                    break;
                case 7:
                    //ticketController.;
                    ticketController.addTicket(productController);
                    break;
                case 8:
                    ticketController.showOldTickets();
                    break;
                case 9:
                    ticketController.totalIncome();
                    break;
                case 0:
                    byeBye();
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    public static void byeBye() {
        System.out.println("\n" +
                "***************************************\n" +
                "*                                     *\n" +
                "*  Flores en el sol,                  *\n" +
                "*  belleza en cada pétalo.            *\n" +
                "*  Ciao Bellx!                        *\n" +
                "*                                     *\n" +
                "***************************************\n"
        );
        ConnectionMysql.closeConnection();
    }
}