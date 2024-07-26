package flowershop.dao;

public interface FlowerDao<ID> extends ProductDao<Flower<ID>, ID> {

    int getTotalStockFlowers();

    double getTotalValueFlowers();
}
