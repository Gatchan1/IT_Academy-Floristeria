package flowershop.dao;

//SI LA ENTIDAD O MODELO NO TIENE ID GENERICO(SERVIRA O NO?):
//public interface DecorationDao extends GenericDao<DecorationDao> {
//public interface DecorationDao extends GenericDao<DecorationDao, Integer> {
public interface DecorationDao<ID> extends GenericDao<Decoration<ID>, ID>{
    void updateStock(ID id, int stockDiff) throws Exception;
    void deleteById(ID id) throws Exception;
    int getTotalStockDecorations();
    double getTotalValueDecorations();
}
