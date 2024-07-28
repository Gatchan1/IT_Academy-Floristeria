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

    private static ProductController instance;
    private final TicketDao ticketDao;
    private final ProductReaderDao productReaderDao;

    public TicketController(DaoManager daoManager){
        this.ticketDao = daoManager.getTicketDao();
    }

    public static TicketController getInstance(DaoManager daoManager){
        if (instance == null) {
            instance = new ProductController(daoManager);
        }
        return instance;
    }

    public void addTicket(){

    }

    public void deleteTicker(){

    }



}