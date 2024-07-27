package flowershop.dao;

public interface TreeDao<ID> extends GenericDao<Tree<ID>, ID> {
    void updateStock(ID id, int stockDiff) throws Exception;
    void deleteById(ID id) throws Exception;
    int getTotalStockTrees();
    double getTotalValueTrees();
}
