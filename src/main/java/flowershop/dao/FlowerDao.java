package flowershop.dao;

public interface FlowerDao<ID> extends GenericDao<Flower<ID>, ID> {
    void updateStock(ID id, int stockDiff) throws Exception;
    int getTotalStockFlowers();
    double getTotalValueFlowers();
}
