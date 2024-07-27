package flowershop.dao.mysql;

import flowershop.dao.FlowerDao;
//TODO: import Flower

import java.sql.*;
import java.util.ArrayList;
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
        try (PreparedStatement stmtProduct = connection.prepareStatement(sqlProduct, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtFlower = connection.prepareStatement(sqlFlower);
        ) {
            Integer productId = null;

            stmtProduct.setString(1, flower.getName());
            stmtProduct.setInt(2, flower.getStock());
            stmtProduct.setDouble(3, flower.getPrice());
            stmtProduct.setString(4, "FLOWER");   //check
            int affectedRows = stmtProduct.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            }
            try (ResultSet keys = stmtProduct.getGeneratedKeys()) {
                if (keys.next()) {
                    productId = keys.getInt(1);
                }
            }
            if (productId == null) {
                throw new SQLException();
            }

            stmtFlower.setInt(1, productId);
            stmtFlower.setString(2, flower.getColor());
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
    public Flower read(String id) {
        Flower flower = null;
        String sql = "SELECT name, stock, price, f.color " +
                "FROM product p JOIN flower f ON p.id_product=f.id_product WHERE p.id_product = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString(1);
                    int stock = rs.getInt(2);
                    double price = rs.getDouble(3);
                    String color = rs.getString(4);
                    flower = new Flower<Integer>(name, price, stock, color);
                    flower.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar elemento en la base de datos", e);
        }
        return flower;   //might be null if the id doesn't match a flower.
    }

    @Override
    public List<Flower> findAll() {
        List<Flower> flowers = new ArrayList<Flower>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.id_product, name, stock, price, f.color " +
                     "FROM product p JOIN flower f ON p.id_product=f.id_product")) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int stock = rs.getInt(3);
                double price = rs.getDouble(4);
                String color = rs.getString(5);

                Flower flower = new Flower(name, price, stock, color);
                flower.setId(Integer.toString(id));
                flowers.add(flower);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al leer elementos en la base de datos", e);
        }
        return flowers;
    }

    @Override
    public void updateStock(String id, int stockDiff) throws Exception {
        Flower flower = read(id);
        if (flower == null) {
            throw new Exception("La id introducida no corresponde a ningún elemento"); //TODO: usar excepción personalizada!
        }
        int stock = flower.getStock();
        int newStock = stock + stockDiff;
        String sql = "UPDATE product SET stock = ? WHERE id_product = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newStock);
            stmt.setInt(2, Integer.parseInt(id));
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("No se ha producido ninguna modificación"); //TODO: usar excepción personalizada;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al modificar el stock del producto en la base de datos", e);
        }
    }

    @Override
    public void deleteById(String id) throws Exception {
        String sql = "DELETE FROM product WHERE id_product = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("No se ha producido ningún borrado"); //TODO: usar excepción personalizada;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al borrar el producto en la base de datos", e);
        }
    }

    @Override
    public boolean exists(Flower flower) throws SQLException {
        String sql = "SELECT COUNT(*) FROM product p JOIN flower f " +
                "ON p.id_product=f.id_product WHERE p.name = ? AND f.color = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, flower.getName());
            stmt.setString(2, flower.getColor());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public int getTotalStock() {
        int totalStock = 0;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(stock) " +
                     "FROM product WHERE type='FLOWER'")) {
            if (rs.next()) {
                totalStock = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al leer stock en la base de datos", e);
        }
        return totalStock;
    }

    @Override
    public double getTotalValue() {
        int totalValue = 0;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(stock * price) " +
                     "FROM product WHERE type='FLOWER'")) {
            if (rs.next()) {
                totalValue = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al leer stock en la base de datos", e);
        }
        return totalValue;
    }
}
//public Flower read(String name, String color) ?