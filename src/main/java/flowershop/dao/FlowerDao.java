package flowershop.dao;

import flowershop.entities.Flower;

public interface FlowerDao extends GenericDao<Flower>,
        ProductDaoMethods<Flower> {
}
