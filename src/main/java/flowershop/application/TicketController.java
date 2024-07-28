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

        String id = Input.readString("Introduce el id del producto: ");
        int amount = Input.readInt("Introduce la cantidad de este producto: ");

       // Product productAdd = getProduct.


    }

    public void deleteTicket(){

    }



}