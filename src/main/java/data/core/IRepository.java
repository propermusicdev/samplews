package data.core;

import java.util.List;

/**
 * Created by Lebel on 31/03/2014.
 */
public interface IRepository<T> {
    List<T> GetAll();
    T Find(int id);
    int Add(T entry);
    //int Add(Class<?> entry);
}
