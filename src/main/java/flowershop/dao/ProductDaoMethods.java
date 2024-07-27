package flowershop.dao;

public interface ProductDaoMethods<ID> {
    void updateStock(ID id, int stockDiff) throws Exception;
    void deleteById(ID id) throws Exception;
    int getTotalStock();
    double getTotalValue();
}
