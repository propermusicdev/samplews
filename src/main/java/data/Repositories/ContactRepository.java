package data.Repositories;

import data.Helpers.ConnectionHelper;
import data.Contact;
import data.core.IContactRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public class ContactRepository implements IContactRepository {
    private ConnectionHelper connHelper =  new ConnectionHelper();

    @Override
    public List<Contact> GetAll() {
        String qry = "SELECT c.CONTACTID, c.FIRSTNAME, c.LASTNAME, c.EMAILADDRESS " +
                "FROM DBA.Contacts c WHERE c.CONTACTTYPE = 4 AND c.CONTACTFUNCTION = 0 AND " +
                "NOT c.FIRSTNAME LIKE '%Proper%' AND NOT c.LASTNAME LIKE '%Warehouse%' ORDER BY c.FIRSTNAME ASC";
        List<Contact> list = new ArrayList<Contact>();
        Connection con = connHelper.getConnection();
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                stmt.setCursorName("Cursor_Contact");
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    Contact person = new Contact();
                    person.setContactId(rs.getInt("CONTACTID"));
                    person.setFirstname(rs.getString("FIRSTNAME"));
                    person.setLastname(rs.getString("LASTNAME"));
                    person.setEmail(rs.getString("EMAILADDRESS"));
                    list.add(person);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (!con.isClosed()) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    @Override
    public Contact Find(int id) {
        String qry = String.format("SELECT c.CONTACTID, c.FIRSTNAME, c.LASTNAME, c.EMAILADDRESS FROM DBA.Contacts c WHERE c.CONTACTID = %s", id);
        Contact contact = new Contact();
        Connection dbCon = connHelper.getConnection();
        if (dbCon != null) {
            try {
                Statement stmt = dbCon.createStatement();
                stmt.setCursorName("Cursor_Contact");
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    contact.setContactId(rs.getInt("CONTACTID"));
                    contact.setFirstname(rs.getString("FIRSTNAME"));
                    contact.setLastname(rs.getString("LASTNAME"));
                    contact.setEmail(rs.getString("EMAILADDRESS"));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (!dbCon.isClosed()) {
                        dbCon.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return contact;
    }

    @Override
    public int Add(Contact entry) {
        return 0;
    }

}

