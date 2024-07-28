package flowershop.dao.mysql;

import flowershop.dao.DecorationDao;
import flowershop.entities.Decoration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlDecorationDao implements DecorationDao {
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
        try (PreparedStatement stmtProduct = connection.prepareStatement(sqlProduct, Statement.RETURN_GENERATED_KEYS);
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
            try (ResultSet keys = stmtProduct.getGeneratedKeys()) {
                if (keys.next()) {
                    productId = keys.getInt(1);
                }
            }
            if (productId == null) {
                throw new SQLException();
            }

            stmtDecoration.setInt(1, productId);
            stmtDecoration.setString(2, decoration.getMaterial().name());
            affectedRows = stmtDecoration.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al introducir elemento en la base de datos");
            }

            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al introducir elemento en la base de datos", e);
        }
    }

    @Override
    public Decoration read(String id) {
        Decoration decoration = null;
        String sql = "SELECT name, stock, price, d.material FROM product p " +
                "JOIN decoration d ON p.id_product=d.id_product WHERE p.id_product = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString(1);
                    int stock = rs.getInt(2);
                    double price = rs.getDouble(3);
                    Decoration.Material material = Decoration.Material.valueOf(rs.getString(4));
                    decoration = new Decoration(name, price, stock, material);
                    decoration.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar elemento en la base de datos", e);
        }
        return decoration;   //might be null if the id doesn't match a decoration.
    }

    @Override
    public List<Decoration> findAll() {
        List<Decoration> decorations = new ArrayList<Decoration>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.id_product, name, stock, price, d.material " +
                     "FROM product p JOIN decoration d ON p.id_product=d.id_product")) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int stock = rs.getInt(3);
                double price = rs.getDouble(4);
                Decoration.Material material = Decoration.Material.valueOf(rs.getString(5));

                Decoration decoration = new Decoration(name, price, stock, material);
                decoration.setId(Integer.toString(id));
                decorations.add(decoration);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al leer elementos en la base de datos", e);
        }
        return decorations;
    }

    @Override
    public void updateStock(String id, int stockDiff) throws Exception {
        Decoration decoration = read(id);
        if (decoration == null) {
            throw new Exception("La id introducida no corresponde a ningún elemento"); //TODO: usar excepción personalizada!
        }
        int stock = decoration.getStock();
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
    public boolean exists(Decoration decoration) throws SQLException {
        String sql = "SELECT COUNT(*) FROM product p JOIN decoration d " +
                "ON p.id_product=d.id_product WHERE p.name = ? AND d.material = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, decoration.getName());
            stmt.setString(2, decoration.getMaterial().name());
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
                     "FROM product WHERE type='DECORATION'")) {
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
                     "FROM product WHERE type='DECORATION'")) {
            if (rs.next()) {
                totalValue = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al leer stock en la base de datos", e);
        }
        return totalValue;
    }
}
