package data.Repositories;

//import org.apache.commons.mail.HtmlEmail;
import data.Helpers.ConnectionHelper;
import data.LogEntry;
import data.MailItem;
import data.core.ILoggerRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public class LoggerRepository implements ILoggerRepository {
    private ConnectionHelper connHelper = new ConnectionHelper();
    private java.sql.Timestamp today = null;
    private java.util.Date utilDate = java.util.Calendar.getInstance().getTime();

    public LoggerRepository() {
    }

    @Override
    public List<LogEntry> GetAll() {
        String qry = "SELECT l.TransactionId, l.ApplicationName, l.MethodName, l.DeviceIMEI, " +
                "l.ExceptionName, l.LogMessage, l.LogDate FROM DBA.tblLogWarehouseSupport l " +
                "ORDER BY l.TransactionId ASC";
        List<LogEntry> list = new ArrayList<LogEntry>();
        Connection con = connHelper.getConnection();
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                stmt.setCursorName("Cursor_Log");
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    LogEntry log = new LogEntry();
                    log.setTransactionId(rs.getLong("TransactionId"));
                    log.setApplicationName(rs.getString("ApplicationName"));
                    log.setMethodName(rs.getString("MethodName"));
                    log.setDeviceIMEI(rs.getString("DeviceIMEI"));
                    log.setExceptionName(rs.getString("ExceptionName"));
                    log.setLogMessage(rs.getString("LogMessage"));
                    log.setLogDate(rs.getTimestamp("LogDate"));
                    list.add(log);
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
    public LogEntry Find(int id) {
        String qry = String.format("SELECT l.TransactionId, l.ApplicationName, l.MethodName, l.DeviceIMEI, " +
                "l.ExceptionName, l.LogMessage, l.LogDate FROM DBA.tblLogWarehouseSupport l " +
                "WHERE l.TransactionId = %s", id);
        LogEntry log = new LogEntry();
        Connection con = connHelper.getConnection();
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                stmt.setCursorName("Cursor_Log");
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    log.setTransactionId(rs.getLong("TransactionId"));
                    log.setApplicationName(rs.getString("ApplicationName"));
                    log.setMethodName(rs.getString("MethodName"));
                    log.setDeviceIMEI(rs.getString("DeviceIMEI"));
                    log.setExceptionName(rs.getString("ExceptionName"));
                    log.setLogMessage(rs.getString("LogMessage"));
                    log.setLogDate(rs.getTimestamp("LogDate"));
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
        return log;
    }

    @Override
    public int Add(LogEntry log) {
        int addedcount = 0;
        String qry = String.format("INSERT INTO DBA.tblLogWarehouseSupport (ApplicationName, MethodName, DeviceIMEI, " +
                        "ExceptionName, LogMessage, LogDate) VALUES ('%s', '%s', '%s', '%s', '%s', %s)", log.getApplicationName(), log.getMethodName(),
                log.getDeviceIMEI(), log.getExceptionName(), log.getLogMessage(), log.getLogDate()
        );
        Connection dbCon = connHelper.getConnection();
        if (dbCon != null) {
            try {
                Statement stmt = dbCon.createStatement();
                addedcount = stmt.executeUpdate(qry);
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
        return addedcount;
    }

    @Override
    public boolean sendEmail(MailItem htmlEmail) {
        // Send an email, - REFERENCE: http://commons.apache.org/proper/commons-email/userguide.html
        final String USER_LOGIN = "propermusicdev@gmail.com";
        final String USER_PWD = "propermus1c";
        boolean success = false;

        try {

//            //receiving: imap.gmail.com, sending: smtp.gmail.com
//            // Create the email message
//            HtmlEmail email = new HtmlEmail();
//            email.setHostName("smtp.gmail.com");
//            email.setSmtpPort(465);
//            //email.setAuthenticator(new DefaultAuthenticator(USER_LOGIN, USER_PWD));
//            email.setAuthentication(USER_LOGIN, USER_PWD);
//            email.setSSLOnConnect(true);
//            //email.addTo(htmlEmail.getRecepients()[0]);
//            email.addTo(htmlEmail.getRecepients().toString());
//            email.addCc("lebel.fuayuku@propermusicgroup.com");
//            email.setFrom(htmlEmail.getSender());
//            email.setSubject(htmlEmail.getSubject());
//
//            // set the html message
//            email.setHtmlMsg(htmlEmail.getMessage());
//
//            // set the alternative message
//            email.setTextMsg("Your email client does not support HTML messages");
//
//            // send the email
//            email.send();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            today = new java.sql.Timestamp(utilDate.getTime());
            LogEntry log1 = new LogEntry(1L, "", "sendEmail",
                    "", e.getClass().getSimpleName(), e.getMessage(), today);
            Add(log1);
        }
        return success;
    }

}

