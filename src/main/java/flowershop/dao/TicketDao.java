package flowershop.dao;

//SI LA ENTIDAD O MODELO NO TIENE ID GENERICO(SERVIRA O NO?):
//public interface TicketDao extends GenericDao<TicketDao> {
//public interface TicketDao extends GenericDao<TicketDao, Integer> {
public interface TicketDao<ID> extends GenericDao<Ticket<ID>, ID>{

    //void createTicket(Ticket<ID> ticket, List<TicketDetail<ID>> details);


    //double getTotalRevenue();

}
