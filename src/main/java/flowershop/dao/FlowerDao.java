package flowershop.dao;

public interface FlowerDao extends GenericDao<Flower<ID>, Integer> {

    int getTotalStockFlowers();

    double getTotalValueFlowers();
}
