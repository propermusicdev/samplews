package data.core;

import data.LogEntry;
import data.MailItem;

import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public interface ILoggerRepository {
    List<LogEntry> GetAll();
    LogEntry Find(int id);
    int Add(LogEntry log);
    boolean sendEmail(MailItem htmlEmail);
}
