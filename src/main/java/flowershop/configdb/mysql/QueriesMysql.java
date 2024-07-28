package flowershop.configdb.mysql;

public class QueriesMysql {

    private static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS " + ConnectionMysql.DB_NAME + " DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;";

    private static final String CREATE_TABLE_PRODUCT = """
            CREATE TABLE IF NOT EXISTS product (
                id_product INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                stock INT NOT NULL DEFAULT 0,
                price DECIMAL(10, 2) NOT NULL,
                type ENUM('TREE', 'FLOWER', 'DECORATION') NULL DEFAULT NULL
            ) ENGINE = InnoDB;""";
    private static final String CREATE_TABLE_FLOWER = """
            CREATE TABLE IF NOT EXISTS flower (
                id_product INT NOT NULL PRIMARY KEY,
                color VARCHAR(50) NOT NULL,
                FOREIGN KEY (id_product) REFERENCES product(id_product) ON UPDATE CASCADE ON DELETE CASCADE
            ) ENGINE = InnoDB;
            """;
    private static final String CREATE_TABLE_TREE = """
            CREATE TABLE IF NOT EXISTS tree (
                id_product INT NOT NULL PRIMARY KEY,
                height DECIMAL(10, 2) NOT NULL,
                FOREIGN KEY (id_product) REFERENCES product(id_product) ON UPDATE CASCADE ON DELETE CASCADE
            ) ENGINE = InnoDB;""";
    private static final String CREATE_TABLE_DECORATION = """
                CREATE TABLE IF NOT EXISTS decoration (
                id_product INT NOT NULL PRIMARY KEY,
                material ENUM('WOOD', 'PLASTIC') NOT NULL,
                FOREIGN KEY (id_product) REFERENCES product(id_product) ON UPDATE CASCADE ON DELETE CASCADE
                ) ENGINE = InnoDB;
            """;
    private static final String CREATE_TABLE_TICKET = """
            CREATE TABLE IF NOT EXISTS ticket (
            id_ticket INT AUTO_INCREMENT PRIMARY KEY,
            sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            total_price DECIMAL(10, 2) NOT NULL
            ) ENGINE = InnoDB;""";
    private static final String CREATE_TABLE_TICKET_DETAIL = """
            CREATE TABLE IF NOT EXISTS ticket_detail (
            id_ticket_detail INT AUTO_INCREMENT PRIMARY KEY,
            id_ticket INT NOT NULL,
            id_product INT,
            quantity INT NOT NULL,
            FOREIGN KEY (id_ticket) REFERENCES ticket(id_ticket) ON UPDATE CASCADE ON DELETE CASCADE,
            FOREIGN KEY (id_product) REFERENCES product(id_product) ON UPDATE SET NULL ON DELETE SET NULL
            ) ENGINE = InnoDB;
            """;

    public static String createDatabaseQuery() {
        return CREATE_DB;
    }

    public static String TableProductQuery() {
        return CREATE_TABLE_PRODUCT;
    }

    public static String TableFlowerQuery() {
        return CREATE_TABLE_FLOWER;
    }

    public static String TableTreeQuery() {
        return CREATE_TABLE_TREE;
    }

    public static String TableDecorationQuery() {
        return CREATE_TABLE_DECORATION;
    }

    public static String TableTicketQuery() {
        return CREATE_TABLE_TICKET;
    }

    public static String TableTicketDetailQuery() {
        return CREATE_TABLE_TICKET_DETAIL;
    }
}
