package flowershop.dao;
//TODO: import Product

//SI LA ENTIDAD O MODELO NO TIENE ID GENERICO(SERVIRA O NO?):
//public interface ProductDao extends GenericDao<Product> {
//public interface ProductDao extends GenericDao<Product, Integer> {
public interface ProductDao<T, ID> {
    T read(ID id);
}
