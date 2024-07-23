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

    }

    public void updateProduct() {

    }

    public void stockProducts() {

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


