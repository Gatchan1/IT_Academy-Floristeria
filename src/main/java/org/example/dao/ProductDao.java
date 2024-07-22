package org.example.dao;

//SI LA ENTIDAD O MODELO NO TIENE ID GENERICO(SERVIRA O NO?):
//public interface ProductDao extends GenericDao<Product> {
//public interface ProductDao extends GenericDao<Product, Integer> {
public interface ProductDao<ID> extends GenericDao<Product<ID>, ID> {
    //pensar metodos propios

    // int getTotalStockForProducts();

    // double getTotalValueForProducts();
}
