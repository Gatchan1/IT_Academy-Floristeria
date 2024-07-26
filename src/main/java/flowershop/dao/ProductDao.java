package flowershop.dao;

//SI LA ENTIDAD O MODELO NO TIENE ID GENERICO(SERVIRA O NO?):
//public interface ProductDao extends GenericDao<Product> {
//public interface ProductDao extends GenericDao<Product, Integer> {
public interface ProductDao<T, ID> extends GenericDao<T, ID> {

    void updateStock(ID id, int stockDiff) throws Exception;
}
