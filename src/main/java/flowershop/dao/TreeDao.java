package flowershop.dao;

public interface TreeDao<ID> extends GenericDao<Tree<ID>, ID> {

    int getTotalStockTrees();

    double getTotalValueTrees();
}
