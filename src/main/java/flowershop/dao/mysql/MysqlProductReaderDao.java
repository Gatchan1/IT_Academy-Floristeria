package flowershop.dao.mysql;

//TODO: import Product
import flowershop.dao.ProductReaderDao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlProductReaderDao implements ProductReaderDao<Product<Integer>, Integer> {
    private final Connection connection;
    private static final Logger logger = Logger.getLogger(MysqlProductReaderDao.class.getName());

    public MysqlProductReaderDao(Connection connection) {
        this.connection = connection;
    }

    public Product<Integer> read(Integer id) {
        Product<Integer> product = null;
        String type = null;
        try (Statement stmtType = connection.createStatement()) {
            ResultSet rs = stmtType.executeQuery("SELECT type FROM product WHERE id_product = id");
            if (rs.next()) {
                type = rs.getString(1).toLowerCase();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar elemento en la base de datos", e);
        }
        if (type == null) {
            return product;
        }

        String attribute = switch (type) {
            case "flower" -> "color";
            case "decoration" -> "material";
            case "tree" -> "height";
            default -> null;
        };

        String sql = "SELECT name, stock, price, " + type + "." + attribute + " FROM product p JOIN "
                     + type + " ON p.id_product=" + type + ".id_product WHERE p.id_product = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString(1);
                    int stock = rs.getInt(2);
                    double price = rs.getDouble(3);
                    product = switch (type) {
                        case "flower" -> {
                            String color = rs.getString(4);
                            yield new Flower<Integer>(name, price, stock, color);
                        }
                        case "decoration" -> {
                            String material = rs.getString(4);
                            yield new Decoration<Integer>(name, price, stock, material);
                        }
                        case "tree" -> {
                            double height = rs.getDouble(4);
                            yield new Tree<Integer>(name, price, stock, height);
                        }
                        default -> product;
                    };
                    if (product != null) {
                        product.setId(id);
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar elemento en la base de datos", e);
        }
        return product;
    }
}
