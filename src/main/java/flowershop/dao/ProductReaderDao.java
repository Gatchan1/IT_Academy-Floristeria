package flowershop.dao;

import flowershop.entities.Product;

public interface ProductReaderDao {
    public Product read(String id);
}
