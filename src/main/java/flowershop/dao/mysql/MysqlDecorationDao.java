package flowershop.dao.mysql;

import flowershop.dao.DecorationDao;
//TODO: import Decoration
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlDecorationDao implements DecorationDao<ID> {
    private final Connection connection;
    private static final Logger logger = Logger.getLogger(MysqlDecorationDao.class.getName());

    public MysqlDecorationDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Decoration decoration) throws SQLException {
        connection.setAutoCommit(false);

        String sqlProduct = "INSERT INTO product (name, stock, price, type) VALUES (?, ?, ?, ?)";
        String sqlDecoration = "INSERT INTO decoration (id_product, material) VALUES (?, ?)";
        try (PreparedStatement stmtProduct = connection.prepareStatement(sqlProduct);
             PreparedStatement stmtDecoration = connection.prepareStatement(sqlDecoration);
        ) {
            Integer productId = null;

            stmtProduct.setString(1, decoration.getName());
            stmtProduct.setInt(2, decoration.getStock());
            stmtProduct.setDouble(3, decoration.getPrice());
            stmtProduct.setString(4, "DECORATION");   //check
            int affectedRows = stmtProduct.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            }
            try (ResultSet rs = stmtProduct.getGeneratedKeys()) {
                if (rs.next()) {
                    productId = rs.getInt(1);
                }
            }
            if (productId == null) {
                throw new SQLException();
            }

            stmtDecoration.setInt(1, productId);
            stmtDecoration.setString(2, decoration.getMaterial());
            affectedRows = stmtDecoration.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al introducir elemento en la base de datos");
            }

            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al introducir elemento en la base de datos", e);
        }
    }
}
