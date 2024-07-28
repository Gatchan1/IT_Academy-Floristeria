package flowershop.dao;

import flowershop.entities.Product;

public interface ProductReaderDao<T extends Product> {
    T read(String id);
}
