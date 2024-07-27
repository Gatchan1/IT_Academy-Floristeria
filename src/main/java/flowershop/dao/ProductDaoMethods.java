package flowershop.dao;

public interface ProductDaoMethods<T> {
    void updateStock(String id, int stockDiff) throws Exception;
    void deleteById(String id) throws Exception;
    boolean exists(T product) throws Exception;
    int getTotalStock();
    double getTotalValue();
}
