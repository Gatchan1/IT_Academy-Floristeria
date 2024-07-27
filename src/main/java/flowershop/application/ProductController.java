package flowershop.application;

import flowershop.dao.DaoManager;
import flowershop.dao.DecorationDao;
import flowershop.dao.FlowerDao;
import flowershop.dao.TreeDao;
import flowershop.entities.Decoration;
import flowershop.entities.Flower;
import flowershop.entities.Product;
import flowershop.entities.Tree;
import flowershop.helpers.Input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductController {

    private static ProductController instance;
    private final FlowerDao flowerDao;
    private final DecorationDao decorationDao;
    private final TreeDao treeDao;
    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

    public ProductController(DaoManager daoManager) {
        this.flowerDao = daoManager.getFlowerDao();
        this.decorationDao = daoManager.getDecorationDao();
        this.treeDao = daoManager.getTreeDao();
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
                case 1:
                    addTree(name, price, stock);
                    break;
                case 2:
                    addFlower(name, price, stock);
                    break;
                case 3:
                    addDecoration(name, price, stock);
                    break;
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
                System.out.println(productToDelete.toString());
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
                System.out.println("Stock actualizado correctamente.");
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

            for (Map.Entry<Integer, Product> entry : productMap.entrySet()) {
                int index = entry.getKey();
                Product product = entry.getValue();

                if (product instanceof Flower) {
                    Flower flower = (Flower) product;
                    System.out.printf(PRODUCT_FORMAT, index, flower.getName(), flower.getPrice(), flower.getStock(), flower.getColor(), flower.getId());
                } else if (product instanceof Tree) {
                    Tree tree = (Tree) product;
                    System.out.printf(PRODUCT_FORMAT, index, tree.getName(), tree.getPrice(), tree.getStock(), String.valueOf(tree.getHeight()), tree.getId());
                } else if (product instanceof Decoration) {
                    Decoration decoration = (Decoration) product;
                    System.out.printf(PRODUCT_FORMAT, index, decoration.getName(), decoration.getPrice(), decoration.getStock(), decoration.getMaterial().toString(), decoration.getId());
                }
            }
            System.out.println("* color/altura(m)/material");
            System.out.println("-------------------------------------------------------------------------");

        }
    }

    public Map<Integer, Product> getAllProductsMap() {
        List<Flower> flowers = flowerDao.findAll();
        List<Tree> trees = treeDao.findAll();
        List<Decoration> decorations = decorationDao.findAll();
        Map<Integer, Product> productMap = new HashMap<>();
        int index = 1;

        if (flowers != null && !flowers.isEmpty()) {
            for (Flower flower : flowers) {
                productMap.put(index++, flower);
            }
        }
        if (trees != null && !trees.isEmpty()) {
            for (Tree tree : trees) {
                productMap.put(index++, tree);
            }
        }
        if (decorations != null && !decorations.isEmpty()) {
            for (Decoration decoration : decorations) {
                productMap.put(index++, decoration);
            }
        }
        return productMap;
    }

    private int selectCategory() {
        final int MIN_OPTION = 1;
        final int MAX_OPTION = 3;

        int category = Input.readIntInRange(
                "---Categorias de productos---\n" +
                        "1. Árbol\n" +
                        "2. Flor\n" +
                        "3. Decoración\n" +
                        "Introduce un número para seleccionar categoría: ",
                MIN_OPTION, MAX_OPTION);

        return category;
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

        switch (option) {
            case 1:
                return Decoration.Material.WOOD;
            case 2:
                return Decoration.Material.PLASTIC;
            default:
                return null;
        }
    }

    private void addTree(String name, double price, int stock) throws Exception {
        double height = Input.readDouble("Introduce altura del árbol (m.cm): ");
        Tree newTree = new Tree(name, price, stock, height);
        if (treeDao.exists(newTree)) {
            System.out.println("El árbol ya existe y no se añadirá.");
        } else {
            treeDao.create(newTree);
            System.out.printf("Nombre: %s, Altura: %.2f, Precio: %.2f, Stock: %d%n",
                    newTree.getName(), newTree.getHeight(), newTree.getPrice(), newTree.getStock());
            System.out.println("Árbol añadido correctamente.");
        }
    }

    private void addFlower(String name, double price, int stock) throws Exception {
        String color = Input.readString("Introduce color de la flor: ");
        Flower newFlower = new Flower(name, price, stock, color);
        if (flowerDao.exists(newFlower)) {
            System.out.println("La flor ya existe y no se añadirá.");
        } else {
            flowerDao.create(newFlower);
            System.out.printf("Nombre: %s, Color: %s, Precio: %.2f, Stock: %d%n",
                    newFlower.getName(), newFlower.getColor(), newFlower.getPrice(), newFlower.getStock());
            System.out.println("Flor añadida correctamente.");
        }
    }

    private void addDecoration(String name, double price, int stock) throws Exception {
        Decoration.Material material = selectMaterial();
        Decoration newDecoration = new Decoration(name, price, stock, material);
        if (decorationDao.exists(newDecoration)) {
            System.out.println("El producto de decoración ya existe y no se añadirá.");
        } else {
            decorationDao.create(newDecoration);
            System.out.printf("Nombre: %s, Material: %s, Precio: %.2f, Stock: %d%n",
                    newDecoration.getName(), newDecoration.getMaterial(), newDecoration.getPrice(), newDecoration.getStock());
            System.out.println("Producto de decoración añadido correctamente.");
        }
    }
}
