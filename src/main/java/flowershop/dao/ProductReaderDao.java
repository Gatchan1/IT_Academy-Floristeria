package flowershop.dao;

import flowershop.entities.Product;

public interface ProductReaderDao {
    Product read(String id);
}
