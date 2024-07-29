package flowershop.application;

import flowershop.dao.*;
import flowershop.entities.*;
import flowershop.helpers.Input;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketController {

    private static TicketController instance;
    private final TicketDao ticketDao;
    private static final Logger logger = Logger.getLogger(TicketController.class.getName());

    public TicketController(DaoManager daoManager) {
        this.ticketDao = daoManager.getTicketDao();
    }

    public static TicketController getInstance(DaoManager daoManager) {
        if (instance == null) {
            instance = new TicketController(daoManager);
        }
        return instance;
    }

    public void addTicket(ProductController productController) {
        boolean continueLoop = true;
        double saleTotal = 0;
        HashMap<Product, Integer> saleProductsAdd = new HashMap<Product, Integer>();
        Product productAdd = productController.getSelectedProduct();

        do {
            try {
                int amount = Input.readInt("Introduce la cantidad de ventas para este producto: ");
                saleTotal += productAdd.getPrice() * amount;
                saleProductsAdd.put(productAdd, amount);
                continueLoop = Input.readYesNo("Pulsa 's' para continuar, 'n' si has terminado de añadir productos en el ticket. ");
            } catch (InputMismatchException e) {
                System.out.println("El tipo de dato introducido no es correcto. ");
            }
            if (continueLoop){
                productAdd = productController.getSelectedProductInLoop();
            }
        } while (continueLoop);

        Ticket newTicket = new Ticket(saleProductsAdd, saleTotal);
        try {
            ticketDao.create(newTicket);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear ticket: ", e);
        }
        System.out.println("Nuevo ticket de venta añadido correctamente. \n" + newTicket.toStringAlt());
    }

    public void showOldTickets() {
        List<Ticket> tickets = ticketDao.findAll();
        if (tickets.isEmpty()) {
            System.out.println("No hay tickets antiguos disponibles.");
        } else {
            System.out.println("\nListado de Tickets Antiguos:");
            System.out.println("----------------------------");
            tickets.forEach(System.out::println);
        }
    }

    public void totalIncome() {
        double income = ticketDao.getTotalRevenue();
        System.out.printf("\n-> El total de ingresos es: %.2f€.\n", income);
    }
}