package flowershop.dao;

import flowershop.entities.Product;

//SI LA ENTIDAD O MODELO NO TIENE ID GENERICO(SERVIRA O NO?):
//public interface ProductDao extends GenericDao<Product> {
//public interface ProductDao extends GenericDao<Product, Integer> {
public interface ProductReaderDao<T extends Product> {
    T read(String id);
}
