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
    //TODO  (eliminar linea productReaderDao)
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
        int control = -1;
        double saleTotal = 0;
        HashMap<Product, Integer> saleProductsAdd = new HashMap<Product, Integer>();
        Product productAdd = productController.getSelectedProduct();

        do {
            try {
                int amount = Input.readInt("Introduce la cantidad de ventas para este producto: ");
                saleTotal += productAdd.getPrice() * amount;
                saleProductsAdd.put(productAdd, amount);
                control = Input.readInt("Pulsa \'0\' si has terminado de añadir productos en el ticket. ");
            } catch (InputMismatchException e) {
                System.out.println("El tipo de dato introducido no es correcto. ");
            }
        } while (control != 0);

        Ticket newTicket = new Ticket(saleProductsAdd, saleTotal);
        try {
            ticketDao.create(newTicket);
            for (Product product : saleProductsAdd.keySet()) {
                int amount = saleProductsAdd.get(product);
                //TODO: add reducteStock to ProductDao
                productDao.reduceStock(product.getId(), amount);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear ticket: ", e);
        }
        System.out.println("Nuevo ticket de venta añadido correctamente. \n" + newTicket.toString());

    }

    public void showOldTickets() {
        System.out.println("Ha seleccionado mostrar tickets antiguos. ");
        List<Ticket> tickets = ticketDao.findAll();
        for (Ticket ticketPrint : tickets) {
            System.out.println(ticketPrint.toString());
        }

    }


    public void totalIncome() {
        System.out.println("Ha seleccionado mostrar el total de ingresos: ");
        List<Ticket> tickets = ticketDao.findAll();
        double income = 0;
        for (Ticket ticket : tickets) {
            income += ticket.getSaleTotal();
        }

        System.out.println("El total de ingresos es " + income + "€. ");
        //test TicketDao.getTotalRevenue();
    }


}