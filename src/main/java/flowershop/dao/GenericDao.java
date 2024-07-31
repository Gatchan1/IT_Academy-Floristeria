package flowershop.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {

    public void create(T entity) throws Exception;

    public T read(String id);

    public List<T> findAll();
}
