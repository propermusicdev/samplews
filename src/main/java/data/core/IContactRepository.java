package data.core;

import data.Contact;

import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public interface IContactRepository {
    List<Contact> GetAll();
    Contact Find(int id);
    int Add(Contact entry);
}
