package flowershop.dao;

public interface FlowerDao<ID> extends GenericDao<Flower<ID>, ID>,
        ProductDaoMethods<Flower<ID>,ID> {
}
