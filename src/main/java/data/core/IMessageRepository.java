package data.core;

import data.Message;

import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public interface IMessageRepository {
    List<Message> GetAll();
    Message Find(int id);
    int Add(Message msg);
    String queryBarcodeFromQueuePartDeux(Message msg);
}
