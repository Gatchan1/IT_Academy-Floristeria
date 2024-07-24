package flowershop.application;

import flowershop.helpers.Input;
import flowershop.dao.DaoManager;


/*si le inyectamos controladores en el constructor no puede ser static startmenu
        Menu menu = new Menu(daoManager);
        menu.startMenu();*/
public class Menu {

    private final ProductController productController;
    // private final TicketController ticketController;
    private static final int MIN_OPTION = 0;
    private static final int MAX_OPTION = 5;

    public Menu(DaoManager daoManager) {
        this.productController = ProductController.getInstance(daoManager);
        // this.ticketController = TicketCrontroller.getInstance(daoManager);
    }

    private static int showMenu() {

        System.out.println("*************** Menu Principal Floristería ***************");

        int option = Input.readIntInRange(
                        "1. Crear producto.\n" +
                        "2. Eliminar producto.\n" +
                        "3. Actualizar producto.\n" +
                        "4. Stock por producto.\n" +
                        "5. Stock por categoria.\n" +
                        "0. Salir\n" +
                        "---------------------------------------------------------\n" +
                        "Por favor, introduce el número de la opción deseada:", MIN_OPTION, MAX_OPTION);
        return option;
    }

    public void startMenu() {

        boolean exit = false;

        do {
           switch(showMenu()) {
               case 1:
                   productController.addProduct();
                   break;
               case 2:
                   productController.deleteProduct();
                   break;
               case 3:
                   productController.updateProduct();
                   break;
               case 4:
                   productController.stockProducts();
                   break;
               case 5:
                   productController.stockByCategory();
                   break;
               case 6:
                   //ticketController.crearunticket();
                   break;
               case 7:
                   //ticketController.mostratTodos los tickets
               case 0:
                   byeBye();
                   exit = true;
                   break;
           }
        } while (!exit);
    }

    public static void byeBye(){
        System.out.println(
                "Flores en el sol,\n" +
                "belleza en cada pétalo.\n" +
                "Ciao Bellx!\n");
        //DESCONECTAR BBDD??
    }
}
