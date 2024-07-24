package flowershop.application;

public class ProductController {

    private static ProductController instance;

    private final ProductDao<ID> productDao;
    private final FlowerDao<ID> flowerDao;
    private final DecorationDao<ID> decorationDao;
    private final TreeDao<ID> treeDao;

    public ProductController(DaoManager daoManager) {
        this.productDao = daoManager.getProductDao();
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
        float price = Input.readFloat("Introduce precio del producto: ");
        int stock = Input.readInt("Introduce stock del producto: ");

        switch (category) {
            case 1:
                float height = Input.readFloat("Introduce altura del árbol: ");
                Tree<ID> tree = new Tree<>(name, price, stock, height);
                treeDao.create(tree);
                break;
            case 2:
                String colour = Input.readString("Introduce color de la flor: ");
                Flower<ID> flower = new Flower<>(name, price, stock, colour);
                flowerDao.create(flower);
                break;
            case 3:
                String material = materialDecoration();
                Decoration<ID> decoration = new Decoration<>(name, price, stock, material);
                decorationDao.create(decoration);
                break;
        }
        System.out.println("Producto añadido correctamente");
    }

    public void deleteProduct() {
        final String HEADER_FORMAT = "%-7s %-15s %-10s %-10s %-10s %-20s %n";
        final String PRODUCT_FORMAT = "%-7d %-15s %-10.2f %-10d %-10s %-20s %n";

        // Obtener todos los productos
        List<Flower<ID>> flowers = flowerDao.findAll();
        List<Tree<ID>> trees = treeDao.findAll();
        List<Decoration<ID>> decorations = decorationDao.findAll();

        // Comprobar si hay productos disponibles
        if ((flowers == null || flowers.isEmpty()) &&
                (trees == null || trees.isEmpty()) &&
                (decorations == null || decorations.isEmpty())) {
            System.out.println("No hay productos disponibles para eliminar.");
            return;
        }

        // Unir todas las listas de productos en una lista común
        List<Product<ID>> allProducts = new ArrayList<>();
        allProducts.addAll(flowers);
        allProducts.addAll(trees);
        allProducts.addAll(decorations);

        // Mostrar todos los productos con índice
        System.out.println("***** Productos en stock *****");
        System.out.printf(HEADER_FORMAT, "Índice", "Nombre", "Precio", "Stock", "Detalle", "ID");

        Map<Integer, Product<ID>> productMap = new HashMap<>();
        int index = 1;

        for (Product<ID> product : allProducts) {
            productMap.put(index, product);

            if (product instanceof Flower) {
                Flower<ID> flower = (Flower<ID>) product;
                System.out.printf(PRODUCT_FORMAT, index, flower.getName(), flower.getPrice(), flower.getStock(), flower.getColour(), flower.getId().toString());
            } else if (product instanceof Tree) {
                Tree<ID> tree = (Tree<ID>) product;
                System.out.printf(PRODUCT_FORMAT, index, tree.getName(), tree.getPrice(), tree.getStock(), String.valueOf(tree.getHeight()), tree.getId().toString());
            } else if (product instanceof Decoration) {
                Decoration<ID> decoration = (Decoration<ID>) product;
                System.out.printf(PRODUCT_FORMAT, index, decoration.getName(), decoration.getPrice(), decoration.getStock(), decoration.getMaterial(), decoration.getId().toString());
            }
            index++;
        }

        // Leer índice del usuario con validación
        int minIndex = 1;
        int maxIndex = allProducts.size();
        int userIndex = Input.readIntInRange(
                "Introduce el índice del producto que deseas eliminar:",
                minIndex,
                maxIndex
        );

        Product<ID> productToDelete = productMap.get(userIndex);

        if (productToDelete != null) {
            ID productId = productToDelete.getId();
            if (productToDelete instanceof Flower) {
                flowerDao.deleteById(productId);  // Llamar a deleteById pasando el ID
            } else if (productToDelete instanceof Tree) {
                treeDao.deleteById(productId);  // Llamar a deleteById pasando el ID
            } else if (productToDelete instanceof Decoration) {
                decorationDao.deleteById(productId);  // Llamar a deleteById pasando el ID
            }
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void updateProduct() {

    }

    public void stockProducts() {

        //mostrar todos los productos
        //List<Product<ID>> products = productDao.findAll();  si existe un productDao

        final String HEADER_FORMAT = "%-7s %-15s %-10s %-10s %-10s %-20s %n";
        final String PRODUCT_FORMAT = "%-7d %-15s %-10.2f %-10d %-10s %-20s %n";

        List<Flower<ID>> flowers = flowerDao.findAll(); //o getAllFlowers
        List<Tree<ID>> trees = treeDao.findAll();
        List<Decoration<ID>> decorations = decorationDao.findAll();

        if ((flowers == null || flowers.isEmpty()) &&
                (trees == null || trees.isEmpty()) &&
                (decorations == null || decorations.isEmpty())) {
            System.out.println("No hay productos disponible");
        } else {
            List<Product<ID>> allProducts = new ArrayList<>();
            allProducts.addAll(flowers);
            allProducts.addAll(trees);
            allProducts.addAll(decorations);

            System.out.println("***** Productos en stock *****");
            System.out.printf(HEADER_FORMAT, "Índice", "Nombre", "Precio", "Stock", "Detalle", "ID");

            int index = 1;

            for (Product<ID> product : allProducts) {
                if (product instanceof Flower) {
                    Flower<ID> flower = (Flower<ID>) product;
                    System.out.printf(PRODUCT_FORMAT, index, flower.getName(), flower.getPrice(), flower.getStock(), flower.getColour(), flower.getId().toString());
                } else if (product instanceof Tree) {
                    Tree<ID> tree = (Tree<ID>) product;
                    System.out.printf(PRODUCT_FORMAT, index, tree.getName(), tree.getPrice(), tree.getStock(), String.valueOf(tree.getHeight()), tree.getId().toString());
                } else if (product instanceof Decoration) {
                    Decoration<ID> decoration = (Decoration<ID>) product;
                    System.out.printf(PRODUCT_FORMAT, index, decoration.getName(), decoration.getPrice(), decoration.getStock(), decoration.getMaterial(), decoration.getId().toString());
                }
                index++;
            }
        }
    }

    public void stockByCategory() {

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
                return "Madera";
            case 2:
                return "Plástico";
            default:
                return null;
        }
    }
}


