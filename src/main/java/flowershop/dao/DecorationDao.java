package flowershop.dao;

public interface DecorationDao<ID> extends GenericDao<Decoration<ID>, ID>,
        ProductDaoMethods<Decoration<ID>, ID>{
}
