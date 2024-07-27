package flowershop.dao;

//SI LA ENTIDAD O MODELO NO TIENE ID GENERICO(SERVIRA O NO?):
public interface TicketDao<ID> extends GenericDao<Ticket<ID>, ID>{

    double getTotalRevenue();

    //void createTicket(Ticket<ID> ticket, List<TicketDetail<ID>> details);

}
