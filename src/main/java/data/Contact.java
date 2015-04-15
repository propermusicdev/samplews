package data;

import java.io.Serializable;

/**
 * Created by Lebel on 01/04/2014.
 */
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    private int ContactId;
    private String Firstname;
    private String Lastname;
    private String Email;

    public Contact() {
    }

    public Contact(String firstname, String lastname, String email) {
        Firstname = firstname;
        Lastname = lastname;
        Email = email;
    }

    public int getContactId() {
        return ContactId;
    }

    public void setContactId(int contactId) {
        ContactId = contactId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return Firstname + Lastname.substring(0,1).toUpperCase() + ".";
    }
}

