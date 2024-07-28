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

        Product productAdd = ProductController.getSelectedProduct();

        do{
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

    public void showOldTickets(){
            System.out.println("Ha seleccionado mostrar tickets antiguos. ");
            int option = selectSort();
            switch (option){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;


            }


    }

    public int selectSort() {

            int option = Input.readInt("Indica el tipo de orden que quieres darle a la búsqueda: \n" +
                    "1. Por el total de la venta (DESC) \n" +
                    "2. Por total de venta (ASC) \n" +
                    "3. Por fecha (DESC) \n" +
                    "4. por fecha (ASC) \n" +
                    "5. Por ID (DESC) \n" +
                    "6. Por ID (ASC) \n");
            return option;


    }





}