package flowershop.dao;

import flowershop.exceptions.NothingDeletedException;
import flowershop.exceptions.WrongIdException;

public interface ProductDaoMethods<T> {
    public void updateStock(String id, int stockDiff) throws WrongIdException;
    public void deleteById(String id) throws NothingDeletedException;
    public boolean exists(T product);
    public int getTotalStock();
    public double getTotalValue();
}
