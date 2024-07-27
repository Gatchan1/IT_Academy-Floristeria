package flowershop.dao;

public interface ProductDaoMethods<T, ID> {
    void updateStock(ID id, int stockDiff) throws Exception;
    void deleteById(ID id) throws Exception;
    boolean exists(T product) throws Exception;
    int getTotalStock();
    double getTotalValue();
}
