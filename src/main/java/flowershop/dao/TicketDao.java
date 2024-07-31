package flowershop.dao;

import flowershop.entities.Ticket;

public interface TicketDao extends GenericDao<Ticket>{
    public double getTotalRevenue();
}
