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

public class StockController{

    private static StockController instance;
    private final FlowerDao flowerDao;
    private final DecorationDao decorationDao;
    private final TreeDao treeDao;
    private final ProductReaderDao productReaderDao;
    private static final Logger logger = Logger.getLogger(ProductController.class.getName());



    public StockController(DaoManager daoManager){
        this.flowerDao = daoManager.getFlowerDao();
        this.decorationDao = daoManager.getDecorationDao();
        this.treeDao = daoManager.getTreeDao();
        this.productReaderDao = daoManager.getProductReaderDao();
    }

    public static StockController getInstance(DaoManager daoManager) {
        if (instance == null) {
            instance = new StockController(daoManager);
        }
        return instance;
    }

    public void stockCategory(){
        System.out.println("Ha seleccionado mostrar el stock por categoría: ");
        try {
            int treeStock = treeDao.getTotalStock();
            int flowerStock = flowerDao.getTotalStock();
            int decorationStock = decorationDao.getTotalStock();
            System.out.println("Stock árboles: " + treeStock +
                    "\nStock flores: " + flowerStock +
                    "\nStock deocración " + decorationStock + ". ")
        } catch (Exception e){
            System.out.println("Error al tratar de recuperar las unidades de stock. ")
        }

    }

    public void stockProducts(){
        System.out.println("Ha seleccionado mostrar stock por producto. ");
        List<Product> productList = ProductReaderDao.findAll();

        if (productList.size() != 0) {
            for (Product product : productList) {
                if (product instanceof Flower) {
                    System.out.println(product.getId() + ". " + product.getName() + " " + product.getColor() + ": " + product.getStock());
                } else if (productToDelete instanceof Tree) {
                    System.out.println(product.getId() + ". " + product.getName() + " " + product.getHeight() + ": " + product.getStock);
                } else if (productToDelete instanceof Decoration) {
                    System.out.println(product.getId() + ". " + product.getName() + " " + product.getMaterial() + ": " + product.getStock());
                }
            }
        }
    }

    public  void stockTotalValue(){
        System.out.println("Ha seleccionado mostrar stock por producto. ");
        List<Product> productList = ProductReaderDao.findAll();
        double stockValue;

        if (productList.size() != 0) {
            for (Product product : productList){
                stockValue += product.getStock*product.getPrice();
            }
        }
        System.out.println("El valor del stock es: "+ stockValue + "€.");

        //test productDaoMethods.getTotalValue;

    }




}

