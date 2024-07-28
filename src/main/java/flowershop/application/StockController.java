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
    private final ProductReaderDao productReaderDao;
    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

}

public StockController(DaoManager daoManager){
    this.productReaderDao = daoManager.getProductReaderDao();
}