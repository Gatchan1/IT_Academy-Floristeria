package flowershop.application;

import flowershop.dao.*;
import flowershop.entities.*;
import flowershop.helpers.Input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ProductController {

    private static ProductController instance;
    private final FlowerDao flowerDao;
    private final DecorationDao decorationDao;
    private final TreeDao treeDao;
    private final ProductReaderDao productReaderDao;
    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

    public ProductController(DaoManager daoManager) {
        this.flowerDao = daoManager.getFlowerDao();
        this.decorationDao = daoManager.getDecorationDao();
        this.treeDao = daoManager.getTreeDao();
        this.productReaderDao = daoManager.getProductReaderDao();
    }

    public static ProductController getInstance(DaoManager daoManager) {
        if (instance == null) {
            instance = new ProductController(daoManager);
        }
        return instance;
    }

    public void addProduct() {
        int category = selectCategory();
        String name = Input.readString("Introduce el nombre del producto a añadir: ");
        double price = Input.readDouble("Introduce precio del producto: ");
        int stock = Input.readInt("Introduce stock del producto: ");
        try {
            switch (category) {
                case 1 -> addTree(name, price, stock);
                case 2 -> addFlower(name, price, stock);
                case 3 -> addDecoration(name, price, stock);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al añadir producto: ", e);
        }
    }

    public void deleteProduct() {
        System.out.println("\n-> Ha seleccionado eliminar un producto.");
        Product productToDelete = getSelectedProduct();
        if (productToDelete != null) {
            try {
                if (productToDelete instanceof Flower) {
                    flowerDao.deleteById(productToDelete.getId());
                } else if (productToDelete instanceof Tree) {
                    treeDao.deleteById(productToDelete.getId());
                } else if (productToDelete instanceof Decoration) {
                    decorationDao.deleteById(productToDelete.getId());
                }
                System.out.println(productToDelete);
                System.out.println("Producto eliminado correctamente.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error al eliminar producto: ", e);
            }
        }
    }

    public void updateProductStock() {
        System.out.println("\n-> Ha seleccionado actualizar stock de un producto.");
        Product productToUpdate = getSelectedProduct();
        if (productToUpdate != null) {
            int stockDiff = Input.readInt("Introduce la cantidad de stock a actualizar (puede ser positiva o negativa): ");
            try {
                if (productToUpdate instanceof Flower) {
                    flowerDao.updateStock(productToUpdate.getId(), stockDiff);
                } else if (productToUpdate instanceof Tree) {
                    treeDao.updateStock(productToUpdate.getId(), stockDiff);
                } else if (productToUpdate instanceof Decoration) {
                    decorationDao.updateStock(productToUpdate.getId(), stockDiff);
                }
                Product productNewStock = (Product) productReaderDao.read(productToUpdate.getId());
                System.out.println("Stock actualizado correctamente: " + productNewStock);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error al actualizar stock del producto: ", e);
            }
        }
    }

    public Product getSelectedProduct() {
        Map<Integer, Product> productMap = getAllProductsMap();
        if (productMap.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return null;
        } else {
            showAllProducts(productMap);
            int userIndex = Input.readIntInRange(
                    "Introduce el índice del producto: ",
                    1,
                    productMap.size()
            );
            return productMap.get(userIndex);
        }
    }

    public void showAllProducts(Map<Integer, Product> productMap) {
        final String HEADER_FORMAT = "%-7s %-17s %-10s %-10s %-12s %-24s %n";
        final String PRODUCT_FORMAT = "%-7d %-17s %-10.2f %-10d %-12s %-24s %n";
        if (productMap.isEmpty()) {
            System.out.println("No hay productos disponibles");
        } else {
            System.out.println("-------------------------------------------------------------------------");
            System.out.printf(HEADER_FORMAT, "ÍNDICE", "NOMBRE", "PRECIO", "STOCK", "DETALLE*", "ID");
            System.out.println("-------------------------------------------------------------------------");
            productMap.forEach((index, product) -> {
                if (product instanceof Flower flower) {
                    System.out.printf(PRODUCT_FORMAT, index, flower.getName(), flower.getPrice(), flower.getStock(), flower.getColor(), flower.getId());
                } else if (product instanceof Tree tree) {
                    System.out.printf(PRODUCT_FORMAT, index, tree.getName(), tree.getPrice(), tree.getStock(), String.valueOf(tree.getHeight()), tree.getId());
                } else if (product instanceof Decoration decoration) {
                    System.out.printf(PRODUCT_FORMAT, index, decoration.getName(), decoration.getPrice(), decoration.getStock(), decoration.getMaterial().toString(), decoration.getId());
                }
            });
            System.out.println("* color/altura(m)/material");
            System.out.println("-------------------------------------------------------------------------");
        }
    }

    public Map<Integer, Product> getAllProductsMap() {
        List<Flower> flowers = flowerDao.findAll();
        List<Tree> trees = treeDao.findAll();
        List<Decoration> decorations = decorationDao.findAll();
        Map<Integer, Product> productMap = new HashMap<>();
        Stream.of(flowers, trees, decorations)
                .flatMap(list -> (list != null ? list.stream() : Stream.empty()))
                .forEach(product -> productMap.put(productMap.size() + 1, product));
        return productMap;
    }

    private int selectCategory() {
        final int MIN_OPTION = 1;
        final int MAX_OPTION = 3;
        return Input.readIntInRange(
                "---Categorias de productos---\n" +
                        "1. Árbol\n" +
                        "2. Flor\n" +
                        "3. Decoración\n" +
                        "Introduce un número para seleccionar categoría: ",
                MIN_OPTION, MAX_OPTION);
    }

    private Decoration.Material selectMaterial() {
        final int MIN_OPTION = 1;
        final int MAX_OPTION = 2;
        int option = Input.readIntInRange(
                "---Materiales de decoracion---\n" +
                        "1. Madera\n" +
                        "2. Plástico\n" +
                        "Introduce un número para seleccionar material de decoracion: ",
                MIN_OPTION, MAX_OPTION);
        return switch (option) {
            case 1 -> Decoration.Material.WOOD;
            case 2 -> Decoration.Material.PLASTIC;
            default -> null;
        };
    }

    private void addTree(String name, double price, int stock) throws Exception {
        double height = Input.readDouble("Introduce altura del árbol (m.cm): ");
        Tree newTree = new Tree(name, price, stock, height);
        if (treeDao.exists(newTree)) {
            System.out.println("El árbol ya existe y no se añadirá.");
        } else {
            treeDao.create(newTree);
            System.out.println("Árbol añadido correctamente: " + newTree);
        }
    }

    private void addFlower(String name, double price, int stock) throws Exception {
        String color = Input.readString("Introduce color de la flor: ");
        Flower newFlower = new Flower(name, price, stock, color);
        if (flowerDao.exists(newFlower)) {
            System.out.println("La flor ya existe y no se añadirá.");
        } else {
            flowerDao.create(newFlower);
            System.out.println("Flor añadida correctamente: " + newFlower);
        }
    }

    private void addDecoration(String name, double price, int stock) throws Exception {
        Decoration.Material material = selectMaterial();
        Decoration newDecoration = new Decoration(name, price, stock, material);
        if (decorationDao.exists(newDecoration)) {
            System.out.println("El producto de decoración ya existe y no se añadirá.");
        } else {
            decorationDao.create(newDecoration);
            System.out.println("Producto de decoración añadido correctamente: " + newDecoration);
        }
    }
}
