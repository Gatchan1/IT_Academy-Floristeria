package flowershop.application;

import flowershop.dao.*;
import flowershop.entities.Product;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockController{

    private static StockController instance;
    private final FlowerDao flowerDao;
    private final DecorationDao decorationDao;
    private final TreeDao treeDao;

    public StockController(DaoManager daoManager){
        this.flowerDao = daoManager.getFlowerDao();
        this.decorationDao = daoManager.getDecorationDao();
        this.treeDao = daoManager.getTreeDao();
    }

    public static StockController getInstance(DaoManager daoManager) {
        if (instance == null) {
            instance = new StockController(daoManager);
        }
        return instance;
    }

    public void stockCategory() {
        int treeStock = treeDao.getTotalStock();
        int flowerStock = flowerDao.getTotalStock();
        int decorationStock = decorationDao.getTotalStock();
        System.out.println("-----------------------------------------");
        System.out.println("           Stock por categoría           ");
        System.out.println("-----------------------------------------");
        System.out.printf(" %-25s %7d \n", "Stock de árboles:", treeStock);
        System.out.printf(" %-25s %7d \n", "Stock de flores:", flowerStock);
        System.out.printf(" %-25s %7d \n", "Stock de decoración:", decorationStock);
        System.out.println("-----------------------------------------");
    }

    public void stockTotalValue() {
        double totalValue = flowerDao.getTotalValue() + treeDao.getTotalValue() + decorationDao.getTotalValue();
        System.out.println("\n->El valor total del stock es: " + totalValue + "€.");
    }

    public void showStockProducts(ProductController ProductController) {
        Map<Integer, Product> productMap = productController.getAllProductsMap();
        if (productMap.isEmpty()) {
            System.out.println("No hay productos disponibles.");
        } else {
            productController.showAllProducts(productMap);
        }
    }
}
