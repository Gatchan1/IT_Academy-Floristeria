package flowershop.application;

import flowershop.dao.*;
import flowershop.entities.*;
import flowershop.helpers.Input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class TicketController {

    private static TicketController instance;
    private final TicketDao ticketDao;
    private final ProductReaderDao productReaderDao;

    public TicketController(DaoManager daoManager) {

        this.ticketDao = daoManager.getTicketDao();
    }

    public static TicketController getInstance(DaoManager daoManager){
        if (instance == null) {
            instance = new TocketController(daoManager);
        }
        return instance;
    }

    public void addTicket(){

        int control = -1;
        double saleTotal;
        HashMap<Product, Integer> saleProductsAdd = new HashMap<Product, Integer>();

        do{
        Product productAdd = ProductController.getSelectedProduct();
            try {
                int amount = Input.readInt("Introduce la cantidad de ventas para este producto: ");
                saleTotal += productAdd.getPrice() * amount;
                saleProductsAdd.add(productAdd, amount);
                control = Input.readInt("Pulsa \'0\' si has terminado de añadir productos en el ticket. ")
            }catch (InputMismatchException e){
                 System.out.println("El tipo de dato introducido no es correcto. ")
        } while (control != 0);

        Ticket newTicket = new Ticket(saleProductsAdd, saleTotal);
        TicketDao.create(newTicket);
            System.out.println("Nuevo ticket de venta añadido correctamente. \n" +  Ticket.toString());

    }

    public void deleteTicket(){

    }



}