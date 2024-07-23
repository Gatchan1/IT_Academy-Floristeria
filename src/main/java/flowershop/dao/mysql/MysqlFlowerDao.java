package flowershop.dao.mysql;

import flowershop.dao.FlowerDao;
//TODO: import Flower

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlFlowerDao implements FlowerDao {
    private final Connection connection;
    private static final Logger logger = Logger.getLogger(MysqlFlowerDao.class.getName());

    public MysqlFlowerDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Flower flower) throws SQLException {
        connection.setAutoCommit(false);

        String sqlProduct = "INSERT INTO product (name, stock, price, type) VALUES (?, ?, ?, ?)";
        String sqlFlower = "INSERT INTO flower (id_product, color) VALUES (?, ?)";
        try (PreparedStatement stmtProduct = connection.prepareStatement(sqlProduct);
             PreparedStatement stmtFlower = connection.prepareStatement(sqlFlower);
        ) {
            Integer productId = null;

            stmtProduct.setString(1, flower.name);
            stmtProduct.setInt(2, flower.stock);
            stmtProduct.setDouble(3, flower.price);
            stmtProduct.setString(4, "flower");   //check
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

            stmtFlower.setInt(1, productId);
            stmtFlower.setString(2, flower.color);
            affectedRows = stmtFlower.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al introducir elemento en la base de datos");
            }

            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al introducir elemento en la base de datos", e);
        }
    }

    @Override
    public Flower read(Integer id) {
        Flower flower = null;
        String sql = "SELECT name, stock, price, f.color" +
                "FROM product p JOIN flower f ON p.id_product=f.id_product WHERE p.id_product = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
             stmt.setInt(1, id);
             try (ResultSet rs = stmt.executeQuery()) {
                 if (rs.next()) {
                     String name = rs.getString(1);
                     int stock = rs.getInt(2);
                     double price = rs.getDouble(3);
                     String color = rs.getString(4);
                     flower = new Flower(name, stock, price, color); //check
                     flower.setId(id);
                 }
             }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar elemento en la base de datos", e);
        }
        return flower;
    }

    @Override
    public void updateStock(Integer id, int stockDiff) throws Exception {
        Flower flower = read(id);
        if (flower == null) {
            throw new Exception("La id introducida no corresponde a ningún elemento"); //TODO: usar excepción personalizada!
        }
        int stock = flower.getStock();
        int newStock = stock + stockDiff;
        String sql = "UPDATE product SET stock = ? WHERE id_product = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newStock);
            stmt.setInt(2, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("No se ha producido ninguna modificación"); //TODO: usar excepción personalizada;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al modificar el stock del producto en la base de datos", e);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Flower> findAll() {
        return List.of();
    }
}
