package data.Repositories;

import data.core.IRepository;

import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public class Repository<T> implements IRepository {
    @Override
    public List GetAll() {
        return null;
    }

    @Override
    public Object Find(int id) {
        return null;
    }

    @Override
    public int Add(Object entry) {
        return 0;
    }
}
