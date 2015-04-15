package data.core;

import data.BinMove;

import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public interface IMoveRepository {
    List<BinMove> GetAll();
    BinMove Find(int id);
    int Add(BinMove move);
}
