package flowershop.application;

import flowershop.helpers.Input;
import flowershop.dao.*;
import flowershop.entities.*;

import java.util.logging.Logger;

public class ProductController {

    private static ProductController instance;

    //private final ProductDao<ID> productDao;
    private final FlowerDao<ID> flowerDao;
    private final DecorationDao<ID> decorationDao;
    private final TreeDao<ID> treeDao;

    //Falta revisar el tema de las excepciones, y si son necesarios bloques try catch
    //private static final Logger logger = Logger.getLogger(ProductController.class.getName());

    public ProductController(DaoManager daoManager) {
        //this.productDao = daoManager.getProductDao();
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

        switch (category) {
            case 1:
                double height = Input.readDouble("Introduce altura del árbol: ");
                Tree<ID> tree = new Tree<>(name, price, stock, height);
                treeDao.create(tree);
                break;
            case 2:
                String color = Input.readString("Introduce color de la flor: ");
                Flower<ID> flower = new Flower<>(name, price, stock, color);
                flowerDao.create(flower);
                break;
            case 3:
                Decoration.Material material = selectMaterial();
                Decoration<ID> decoration = new Decoration<>(name, price, stock, material);
                decorationDao.create(decoration);
                break;
        }
        System.out.println("Producto añadido correctamente");
    }

    public void deleteProduct() {

        Map<Integer, Product<ID>> productMap = getAllProductsMap();

        if (productMap.isEmpty()) {
            System.out.println("No hay productos disponibles.");
        } else {
            //Es necesario mostrar todos los productos antes de eliminar uno??
            showAllProducts(productMap);

            int userIndex = Input.readIntInRange(
                    "Introduce el índice del producto que deseas eliminar: ",
                    1,
                    productMap.size()
            );

            Product<ID> productToDelete = productMap.get(userIndex);

            if (productToDelete instanceof Flower) {
                flowerDao.deleteById(productToDelete.getId());
            } else if (productToDelete instanceof Tree) {
                treeDao.deleteById(productToDelete.getId());
            } else if (productToDelete instanceof Decoration) {
                decorationDao.deleteById(productToDelete.getId());
            }
            System.out.println("Producto eliminado correctamente.");
        }
    }

    public void updateProductStock() {

        Map<Integer, Product<ID>> productMap = getAllProductsMap();

        if (productMap.isEmpty()) {
            System.out.println("No hay productos disponibles.");
        } else {
            //Es necesario mostrar todos los productos antes de eliminar uno??
            showAllProducts(productMap);

            int userIndex = Input.readIntInRange(
                    "Introduce el índice del producto cuyo stock deseas actualizar: ",
                    1,
                    productMap.size()
            );

            Product<ID> productToUpdate = productMap.get(userIndex);

            int stockDiff = Input.readInt("Introduce la cantidad de stock a actualizar (puede ser positiva o negativa): ");

            if (productToUpdate instanceof Flower) {
                flowerDao.updateStock(productToUpdate.getId(), stockDiff);
            } else if (productToUpdate instanceof Tree) {
                treeDao.updateStock(productToUpdate.getId(), stockDiff);
            } else if (productToUpdate instanceof Decoration) {
                decorationDao.updateStock(productToUpdate.getId(), stockDiff);
            }
            System.out.println("Stock actualizado correctamente.");
        }
    }

    //Si la lista es muy larga, se puede investigar o quien lo conozca implemetar lo de la paginación
    public void showAllProducts(Map<Integer, Product<ID>> productMap) {

        final String HEADER_FORMAT = "%-7s %-15s %-10s %-10s %-10s %-20s %n";
        final String PRODUCT_FORMAT = "%-7d %-15s %-10.2f %-10d %-10s %-20s %n";

        if (productMap.isEmpty()) {
            System.out.println("No hay productos disponibles");
        } else {
            System.out.println("***** Productos en stock *****");
            System.out.printf(HEADER_FORMAT, "Índice", "Nombre", "Precio", "Stock", "Detalle", "ID");

            for (Map.Entry<Integer, Product<ID>> entry : productMap.entrySet()) {
                int index = entry.getKey();
                Product<ID> product = entry.getValue();

                if (product instanceof Flower) {
                    Flower<ID> flower = (Flower<ID>) product;
                    System.out.printf(PRODUCT_FORMAT, index, flower.getName(), flower.getPrice(), flower.getStock(), flower.getColor(), flower.getId().toString());
                } else if (product instanceof Tree) {
                    Tree<ID> tree = (Tree<ID>) product;
                    System.out.printf(PRODUCT_FORMAT, index, tree.getName(), tree.getPrice(), tree.getStock(), String.valueOf(tree.getHeight()), tree.getId().toString());
                } else if (product instanceof Decoration) {
                    Decoration<ID> decoration = (Decoration<ID>) product;
                    System.out.printf(PRODUCT_FORMAT, index, decoration.getName(), decoration.getPrice(), decoration.getStock(), decoration.getMaterial().toString, decoration.getId().toString());
                }
            }
        }
    }

    private int selectCategory() {

        final int MIN_OPTION = 1;
        final int MAX_OPTION = 3;

        int category = Input.readIntInRange(
                "***Categorias de productos***\n" +
                        "1. Árbol\n" +
                        "2. Flor\n" +
                        "3. Decoración\n" +
                        "Introduce un número para seleccionar categoría: ",
                MIN_OPTION, MAX_OPTION);

        return category;
    }

    private String materialDecoration() {
        final int MIN_OPTION = 1;
        final int MAX_OPTION = 2;

        int option = Input.readIntInRange(
                "***Materiales de decoracion***\n" +
                        "1. Madera\n" +
                        "2. Plástico\n" +
                        "Introduce un número para seleccionar material de decoracion: ",
                MIN_OPTION, MAX_OPTION);

        switch (option) {
            case 1:
                return Decoration.Material.FUSTA;
            case 2:
                return Decoration.Material.PLASTIC;;
            default:
                return null;
        }
    }

    public Map<Integer, Product<ID>> getAllProductsMap() {

        List<Flower<ID>> flowers = flowerDao.findAll();
        List<Tree<ID>> trees = treeDao.findAll();
        List<Decoration<ID>> decorations = decorationDao.findAll();

        Map<Integer, Product<ID>> productMap = new HashMap<>();
        int index = 1;

        if (flowers != null && !flowers.isEmpty()) {
            for (Flower<ID> flower : flowers) {
                productMap.put(index++, flower);
            }
        }
        if (trees != null && !trees.isEmpty()) {
            for (Tree<ID> tree : trees) {
                productMap.put(index++, tree);
            }
        }
        if (decorations != null && !decorations.isEmpty()) {
            for (Decoration<ID> decoration : decorations) {
                productMap.put(index++, decoration);
            }
        }
        return productMap;
    }
}

