package flowershop.dao;

//SI LA ENTIDAD O MODELO NO TIENE ID GENERICO(SERVIRA O NO?):
//public interface DecorationDao extends GenericDao<DecorationDao> {
//public interface DecorationDao extends GenericDao<DecorationDao, Integer> {
public interface DecorationDao<ID> extends ProductDao<Decoration<ID>, ID>{

    int getTotalStockDecorations();

    double getTotalValueDecorations();
}
