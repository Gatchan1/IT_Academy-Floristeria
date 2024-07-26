package flowershop.dao;

public interface TreeDao<ID> extends ProductDao<Tree<ID>, ID> {

    int getTotalStockTrees();

    double getTotalValueTrees();
}
