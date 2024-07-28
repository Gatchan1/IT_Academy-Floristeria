package flowershop.dao.mysql;

import flowershop.dao.ProductReaderDao;
import flowershop.entities.Decoration;
import flowershop.entities.Flower;
import flowershop.entities.Product;
import flowershop.entities.Tree;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlProductReaderDao implements ProductReaderDao {
    private final Connection connection;
    private static final Logger logger = Logger.getLogger(MysqlProductReaderDao.class.getName());

    public MysqlProductReaderDao(Connection connection) {
        this.connection = connection;
    }

    public Product read(String id) {
        Product product = null;
        String type = null;
        String typeQuery = "SELECT type FROM product WHERE id_product = ?";
        try (PreparedStatement stmtType = connection.prepareStatement(typeQuery)) {
            stmtType.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmtType.executeQuery()) {
                if (rs.next()) {
                    type = rs.getString(1).toLowerCase();
                }
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
            stmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString(1);
                    int stock = rs.getInt(2);
                    double price = rs.getDouble(3);
                    product = switch (type) {
                        case "flower" -> {
                            String color = rs.getString(4);
                            yield new Flower(name, price, stock, color);
                        }
                        case "decoration" -> {
                            Decoration.Material material = Decoration.Material.valueOf(rs.getString(4));
                            yield new Decoration(name, price, stock, material);
                        }
                        case "tree" -> {
                            double height = rs.getDouble(4);
                            yield new Tree(name, price, stock, height);
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
