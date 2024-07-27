package flowershop.configdb.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitialStockMysql {

    private static final Logger logger = Logger.getLogger(InitialStockMysql.class.getName());

    private static final String INSERT_INITIAL_STOCK = """
            SET FOREIGN_KEY_CHECKS = 0;
                        
            START TRANSACTION;
                        
            INSERT INTO product (name, stock, price, type) VALUES ('Pino', 50, 19.99, 'TREE');
            SET @product_id_tree1 = LAST_INSERT_ID();
                        
            INSERT INTO tree (id_product, height) VALUES (@product_id_tree1, 2.5);
            
            INSERT INTO product (name, stock, price, type) VALUES ('Abeto', 25, 29.99, 'TREE');
            SET @product_id_tree2 = LAST_INSERT_ID();
                        
            INSERT INTO tree (id_product, height) VALUES (@product_id_tree2, 2.2);
            
            INSERT INTO product (name, stock, price, type) VALUES ('Secuoya', 25, 99.99, 'TREE');
            SET @product_id_tree3 = LAST_INSERT_ID();
                        
            INSERT INTO tree (id_product, height) VALUES (@product_id_tree3, 11.69);
                        
            INSERT INTO product (name, stock, price, type) VALUES ('Rosa', 30, 9.99, 'FLOWER');
            SET @product_id_flower1 = LAST_INSERT_ID();
                        
            INSERT INTO flower (id_product, color) VALUES (@product_id_flower1, 'Negro');
            
            INSERT INTO product (name, stock, price, type) VALUES ('Amapola', 25, 9.99, 'FLOWER');
            SET @product_id_flower2 = LAST_INSERT_ID();
                        
            INSERT INTO flower (id_product, color) VALUES (@product_id_flower2, 'Rojo');
            
            INSERT INTO product (name, stock, price, type) VALUES ('Adormidera', 45, 19.99, 'FLOWER');
            SET @product_id_flower3 = LAST_INSERT_ID();
                        
            INSERT INTO flower (id_product, color) VALUES (@product_id_flower3, 'Violáceo');
                        
            INSERT INTO product (name, stock, price, type) VALUES ('Cesta', 20, 14.99, 'DECORATION');
            SET @product_id_decoration1 = LAST_INSERT_ID();
                        
            INSERT INTO decoration (id_product, material) VALUES (@product_id_decoration1, 'WOOD');
                        
            INSERT INTO product (name, stock, price, type) VALUES ('Cesta', 15, 7.99, 'DECORATION');
            SET @product_id_decoration2 = LAST_INSERT_ID();
                        
            INSERT INTO decoration (id_product, material) VALUES (@product_id_decoration2, 'PLASTIC');
                        
            COMMIT;
                        
            SET FOREIGN_KEY_CHECKS = 1;
            """;

    public static void insertInitialStock() {
        Connection connection = null;
        try {
            connection = ConnectionMysql.getDbConnectionMultiQueries();
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            stmt.execute(INSERT_INITIAL_STOCK);
            connection.commit();
            logger.info("Stock inicial introducido correctamente");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error introduciendo stock inicial: " + e.getMessage(), e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    logger.log(Level.SEVERE, "Error al hacer rollback: " + rollbackException.getMessage(), rollbackException);
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error al cerrar la conexión: " + e.getMessage(), e);
                }
            }
        }
    }
}
