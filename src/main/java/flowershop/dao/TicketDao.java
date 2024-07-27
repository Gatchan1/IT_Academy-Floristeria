package flowershop.dao;

//SI LA ENTIDAD O MODELO NO TIENE ID GENERICO(SERVIRA O NO?):
public interface TicketDao extends GenericDao<Ticket>{
    double getTotalRevenue();
}
