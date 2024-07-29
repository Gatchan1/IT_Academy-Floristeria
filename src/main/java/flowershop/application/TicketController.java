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
        int control = -1;
        double saleTotal = 0;
        HashMap<Product, Integer> saleProductsAdd = new HashMap<Product, Integer>();
        Product productAdd = productController.getSelectedProduct();

        do {
            try {
                int amount = Input.readInt("Introduce la cantidad de ventas para este producto: ");
                saleTotal += productAdd.getPrice() * amount;
                saleProductsAdd.put(productAdd, amount);
                control = Input.readInt("Introduce un nuevo ID, o pulsa \'0\' si has terminado de añadir productos en el ticket. ");
            } catch (InputMismatchException e) {
                System.out.println("El tipo de dato introducido no es correcto. ");
            }
        } while (control != 0);

        Ticket newTicket = new Ticket(saleProductsAdd, saleTotal);
        try {
            ticketDao.create(newTicket);
            for (Product productStockReduce : saleProductsAdd.keySet()) {
                int amount = -(saleProductsAdd.get(product));
                if (productStockReduce instanceof Flower) {
                    flowerDao.updateStock(product.getId(), amount);
                } else if (productStockReduce instanceof Tree) {
                    treeDao.updateStock(product.getId(), amount);
                } else if (productStockReduce instanceof Decoration) {
                    decorationDao.updateStock(product.getId(), amount);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear ticket: ", e);
        }
        System.out.println("Nuevo ticket de venta añadido correctamente. \n" + newTicket.toString());

    }

    public void showOldTickets() {
        System.out.println("Ha seleccionado mostrar tickets antiguos. ");
        List<Ticket> tickets = ticketDao.findAll();
        tickets.forEach(System.out::println);
    }

    public void totalIncome() {
        System.out.println("Ha seleccionado mostrar el total de ingresos: ");
        double income = ticketDao.getTotalRevenue();
        System.out.println("El total de ingresos es " + income + "€. ");
    }
}


}