package flowershop.dao;

import java.util.List;
//QUIZAS ELIMINAR GENERIC DAO, CON PRODUCTDAO NO HABRÁ QUE IMPLEMENTAR METODOS CRUD EN FLOWERDAO ETC.
// SI ES QUE HACEN FALTA FLOWERDAO ETC,
// EL createTicket no se como son los modelos y entidades, si existe un ticket y un item ticket o seguramente no?
// si en modelos existiera un item ticket el create quizás necesitaria dos parametros de entrada, diferente de este crud
public interface GenericDao<T, ID> {

    // CRUD
    void create(T entity);

    T read(ID id);

    void update(T entity);

    void deleteById(ID id);

    List<T> findAll();
    //List<T> getAll(); como queda mejor???
}
