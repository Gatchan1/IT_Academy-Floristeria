package flowershop.dao;

import flowershop.exceptions.NothingDeletedException;
import flowershop.exceptions.WrongIdException;

public interface ProductDaoMethods<T> {
    void updateStock(String id, int stockDiff) throws WrongIdException;
    void deleteById(String id) throws NothingDeletedException;
    boolean exists(T product);
    int getTotalStock();
    double getTotalValue();
}
