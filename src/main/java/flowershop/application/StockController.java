package flowershop.application;

import flowershop.dao.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockController{

    private static StockController instance;
    private final FlowerDao flowerDao;
    private final DecorationDao decorationDao;
    private final TreeDao treeDao;
    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

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

    public void stockCategory(){
        System.out.println("\n-> Ha seleccionado mostrar el stock por categoría: ");
        try {
            int treeStock = treeDao.getTotalStock();
            int flowerStock = flowerDao.getTotalStock();
            int decorationStock = decorationDao.getTotalStock();
            System.out.println("\nStock árboles: " + treeStock +
                    "\nStock flores: " + flowerStock +
                    "\nStock deocración " + decorationStock + ". ");
        } catch (Exception e){
            logger.log(Level.SEVERE,"Error al tratar de recuperar las unidades de stock.", e );
        }
    }

    public  void stockTotalValue(){
        double totalValue = flowerDao.getTotalValue() + treeDao.getTotalValue() + decorationDao.getTotalValue();
        System.out.println("\n->El valor del stock es: "+ totalValue + "€.");
    }
}
