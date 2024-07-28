package flowershop.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {

    void create(T entity) throws SQLException;

    T read(String id);

    List<T> findAll();
}
