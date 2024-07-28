package flowershop.dao.mysql;

import flowershop.dao.TreeDao;
import flowershop.entities.Tree;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlTreeDao implements TreeDao {
    private final Connection connection;
    private static final Logger logger = Logger.getLogger(MysqlTreeDao.class.getName());

    public MysqlTreeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Tree tree) throws SQLException {
        connection.setAutoCommit(false);

        String sqlProduct = "INSERT INTO product (name, stock, price, type) VALUES (?, ?, ?, ?)";
        String sqlTree = "INSERT INTO tree (id_product, height) VALUES (?, ?)";
        try (PreparedStatement stmtProduct = connection.prepareStatement(sqlProduct, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtTree = connection.prepareStatement(sqlTree);
        ) {
            Integer productId = null;

            stmtProduct.setString(1, tree.getName());
            stmtProduct.setInt(2, tree.getStock());
            stmtProduct.setDouble(3, tree.getPrice());
            stmtProduct.setString(4, "TREE");   //check
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

            stmtTree.setInt(1, productId);
            stmtTree.setDouble(2, tree.getHeight());
            affectedRows = stmtTree.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al introducir elemento en la base de datos");
            }

            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al introducir elemento en la base de datos", e);
        }
    }

    @Override
    public Tree read(String id) {
        Tree tree = null;
        String sql = "SELECT name, stock, price, t.height FROM product p " +
                "JOIN tree t ON p.id_product=t.id_product WHERE p.id_product = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString(1);
                    int stock = rs.getInt(2);
                    double price = rs.getDouble(3);
                    double height = rs.getDouble(4);
                    tree = new Tree(name, price, stock, height);
                    tree.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar elemento en la base de datos", e);
        }
        return tree;   //might be null if the id doesn't match a tree.
    }

    @Override
    public List<Tree> findAll() {
        List<Tree> trees = new ArrayList<Tree>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.id_product, name, stock, price, t.height " +
                     "FROM product p JOIN tree t ON p.id_product=t.id_product")) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int stock = rs.getInt(3);
                double price = rs.getDouble(4);
                double height = rs.getDouble(5);

                Tree tree = new Tree(name, price, stock, height);
                tree.setId(Integer.toString(id));
                trees.add(tree);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al leer elementos en la base de datos", e);
        }
        return trees;
    }

    @Override
    public void updateStock(String id, int stockDiff) throws Exception {
        Tree tree = read(id);
        if (tree == null) {
            throw new Exception("La id introducida no corresponde a ningún elemento"); //TODO: usar excepción personalizada!
        }
        int stock = tree.getStock();
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
    public boolean exists(Tree tree) throws SQLException {
        String sql = "SELECT COUNT(*) FROM product p JOIN tree t " +
                "ON p.id_product=t.id_product WHERE p.name = ? AND t.height = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tree.getName());
            stmt.setDouble(2, tree.getHeight());
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
                     "FROM product WHERE type='TREE'")) {
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
                     "FROM product WHERE type='TREE'")) {
            if (rs.next()) {
                totalValue = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al leer stock en la base de datos", e);
        }
        return totalValue;
    }
}
