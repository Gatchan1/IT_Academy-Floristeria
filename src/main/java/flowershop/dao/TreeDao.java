package flowershop.dao;

public interface TreeDao<ID> extends GenericDao<Tree<ID>, ID> {
    void updateStock(ID id, int stockDiff) throws Exception;
    int getTotalStockTrees();
    double getTotalValueTrees();
}
