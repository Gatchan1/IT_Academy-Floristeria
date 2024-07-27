package flowershop.dao;

public interface TreeDao<ID> extends GenericDao<Tree<ID>, ID>,
        ProductDaoMethods<Tree<ID>,ID> {
}
