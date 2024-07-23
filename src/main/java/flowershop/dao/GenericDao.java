package flowershop.dao;

import java.sql.SQLException;
import java.util.List;
//QUIZAS ELIMINAR GENERIC DAO, CON PRODUCTDAO NO HABRÁ QUE IMPLEMENTAR METODOS CRUD EN FLOWERDAO ETC.
// SI ES QUE HACEN FALTA FLOWERDAO ETC,
// EL createTicket no se como son los modelos y entidades, si existe un ticket y un item ticket o seguramente no?
// si en modelos existiera un item ticket el create quizás necesitaria dos parametros de entrada, diferente de este crud
//Raquel - No, solo habrá una entidad de ticket; no habrá entidad de item ticket. Con lo que este crud sí que sirve.
//Raquel - El que no usaremos de momento es ProductDao, a no ser que nos sobre tiempo para intentar refactorizar.
public interface GenericDao<T, ID> {

    // CRUD
    void create(T entity) throws SQLException;

    T read(ID id);

    void update(T entity);

    void deleteById(ID id);

    List<T> findAll();
    //List<T> getAll(); como queda mejor???
}
