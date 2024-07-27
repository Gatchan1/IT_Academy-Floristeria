package flowershop.dao.mysql;

import flowershop.dao.TicketDao;
//TODO: import Ticket
//TODO: import Product

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlTicketDao implements TicketDao<Integer> {
    private final Connection connection;
    private static final Logger logger = Logger.getLogger(MysqlTicketDao.class.getName());

    public MysqlTicketDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Ticket<Integer> ticket) throws SQLException {
        // primero crear el elemento de la tabla ticket, y sacar esa id, y
        // luego crear los elementos de la tabla ticket_detail.
        connection.setAutoCommit(false);

        String sqlTicket = "INSERT INTO ticket (total_price) VALUES (?)";
        String sqlDetail = "INSERT INTO ticket_detail (id_ticket, id_product, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmtTicket = connection.prepareStatement(sqlTicket, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtDetail = connection.prepareStatement(sqlDetail);
        ) {
            Integer ticketId = null;

            stmtTicket.setDouble(1, ticket.getSaleTotal());
            int affectedRows = stmtTicket.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            }
            try (ResultSet keys = stmtTicket.getGeneratedKeys()) {
                if (keys.next()) {
                    ticketId = keys.getInt(1);
                }
            }
            if (ticketId == null) {
                throw new SQLException();
            }

            Map<Product, Integer> products = ticket.getSaleProducts();
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                stmtDetail.setInt(1, ticketId);
                stmtDetail.setInt(2, key.getId());      //check
                stmtDetail.setInt(3, value);
                affectedRows = stmtDetail.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Error al introducir elemento en la base de datos");
                }
            }

            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al introducir elemento en la base de datos", e);
        }
    }

    @Override
    public Ticket<Integer> read(Integer id) {
        Ticket<Integer> ticket = null;
        String sql = "SELECT sale_date, total_price, id_product, quantity " +
                "FROM ticket t JOIN ticket_detail td ON t.id_ticket=td.id_ticket WHERE t.id_ticket = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                LocalDate saleDate = LocalDate.parse("2000-01-01");
                double saleTotal = 0;
                // ^^^ both these values are only placeholders;
                // the real values are set while looping the ResultSet.
                boolean dateAndTotalAreSet = false;
                HashMap<Product, Integer> saleProducts = new HashMap<Product, Integer>();

                MysqlProductReaderDao productReader = new MysqlProductReaderDao(connection);

                while (rs.next()) {
                    if (!dateAndTotalAreSet) {
                        saleDate = rs.getDate(1).toLocalDate();
                        saleTotal = rs.getDouble(2);
                        dateAndTotalAreSet = true;
                    }
                    int productId = rs.getInt(3);
                    int quantity = rs.getInt(4);

                    Product product = productReader.read(productId);
                    // AQUI ESTAMOS USANDO LA MISMA CONNECTION PARA HACER UNA CONSULTA MIENTRAS ITERAMOS OTRA CONSULTA.
                    // OJALÁ NO DÉ PROBLEMAS
                    saleProducts.put(product, quantity);
                }

                ticket = new Ticket(saleProducts, saleTotal);     //check
                ticket.setSaleDate(saleDate);                     //check
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar elemento en la base de datos", e);
        }
        return ticket;   //might be null if the id doesn't match a ticket.
    }

    @Override
    public List<Ticket<Integer>> findAll() {
        return List.of();
    }

    @Override
    public double getTotalRevenue() {
        return 0;
    }
}
