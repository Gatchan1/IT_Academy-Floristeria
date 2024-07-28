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
        this.productReaderDao = daoManager.getProductReaderDao();              //TODO  (nueva linea)
    }

    public static TicketController getInstance(DaoManager daoManager){
        if (instance == null) {
            instance = new TicketController(daoManager);                       //TODO  (typo)
        }
        return instance;
    }

    public void addTicket(ProductController productController){                 //TODO  (pasar por parámetro!!)

        int control = -1;
        double saleTotal;
        HashMap<Product, Integer> saleProductsAdd = new HashMap<Product, Integer>();

        Product productAdd = productController.getSelectedProduct();             //TODO  (productController ha de ser instancia)

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
            List<Ticket> tickets = ticketDao.findAll();
            for (Ticket ticketPrint : tickets){
                System.out.println(ticketPrint.toString);
            }

        }


        public void totalIncome(){
            System.out.println("Ha seleccionado mostrar el total de ingresos: ")
            List<Ticket> tickets = ticketDao.findAll();
            double income;
            for (Ticket ticket : tickets){
                income += ticket.getSaleTotal();
            }

            System.out.println("El total de ingresos es " + income + "€. ");
            //test TicketDao.getTotalRevenue();
        }


    }