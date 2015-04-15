package data.Repositories;

import data.Helpers.ConnectionHelper;
import data.Helpers.ResponseHelper;
import data.LogEntry;
import data.Message;
import data.core.IMessageRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public class MessageRepository implements IMessageRepository {
    private ConnectionHelper connHelper = new ConnectionHelper();
    private LoggerRepository logger = new LoggerRepository();
    private ResponseHelper responseHelper = new ResponseHelper();
    private java.sql.Timestamp today = null;
    private java.util.Date utilDate = java.util.Calendar.getInstance().getTime();

    @Override
    public List<Message> GetAll() {
        String sql = "SELECT m.MESSAGEID, m.SOURCE, m.MESSAGETYPE, m.MESSAGESTATUS, m.INCOMINGSTATUS, " +
                "m.INCOMINGMESSAGE, m.OUTGOINGSTATUS, m.OUTGOINGMESSAGE, m.INSERTEDTIMESTAMP " +
                "FROM MessageExchange m ORDER BY m.MESSAGEID ASC";
        List<Message> list = new ArrayList<Message>();
        Connection con = connHelper.getConnection();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                stmt.setCursorName("Message_Cur");
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Message msg = new Message();
                    msg.setMessageId(rs.getInt("MESSAGEID"));
                    msg.setSource(rs.getString("SOURCE"));
                    msg.setMessageType(rs.getString("MESSAGETYPE"));
                    msg.setIncomingStatus(rs.getInt("INCOMINGSTATUS"));
                    byte[] incomingMsg = rs.getBytes("INCOMINGMESSAGE");
                    msg.setIncomingMessage(incomingMsg.toString());
                    msg.setOutgoingStatus(rs.getInt("OUTGOINGMESSAGE"));
                    byte[] outgoingMessage = rs.getBytes("OUTGOINGMESAGE");
                    msg.setOutgoingMessage(outgoingMessage.toString());
                    msg.setInsertedTimeStamp(rs.getTimestamp("INSERTEDTIMESTAMP"));
                    msg.setTTL(rs.getInt("TTL"));
                    list.add(msg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (!con.isClosed()) {
                        con.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return list;
    }

    @Override
    public Message Find(int id) {
        String sql = String.format("SELECT m.MESSAGEID, m.SOURCE, m.MESSAGETYPE, " +
                "m.MESSAGESTATUS, m.INCOMINGSTATUS, m.INCOMINGMESSAGE, m.OUTGOINGSTATUS, " +
                "m.OUTGOINGMESSAGE, m.INSERTEDTIMESTAMP FROM MessageExchange " +
                "WHERE m.MESSAGEID = %S", id);
        Message msg = new Message();
        Connection con = connHelper.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.setCursorName("Message_cur");
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                msg.setMessageId(rs.getInt("MESSAGEID"));
                msg.setSource(rs.getString("SOURCE"));
                msg.setMessageType(rs.getString("MESSAGETYPE"));
                msg.setIncomingStatus(rs.getInt("INCOMINGSTATUS"));
                byte[] incomingMsg = rs.getBytes("INCOMINGMESSAGE");
                msg.setIncomingMessage(incomingMsg.toString());
                msg.setOutgoingStatus(rs.getInt("OUTGOINGMESSAGE"));
                byte[] outgoingMessage = rs.getBytes("OUTGOINGMESAGE");
                msg.setOutgoingMessage(outgoingMessage.toString());
                msg.setInsertedTimeStamp(rs.getTimestamp("INSERTEDTIMESTAMP"));
                msg.setTTL(rs.getInt("TTL"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally  {
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return msg;
    }

    @Override
    public int Add(Message msg) {
        int addedCount = 0;
        int InsertedId = 0;
        String sql =  String.format("INSERT INTO DBA.MessageExchange (SOURCE, MESSAGETYPE, " +
                        "INCOMINGSTATUS, INCOMINGMESSAGE, OUTGOINGSTATUS, OUTGOINGMESSAGE, INSERTEDTIMESTAMP, " +
                        "TTL) VALUES ('%s', '%s', %s, '%s', %s, '%s', '%s', %s)", msg.getSource(), msg.getMessageType(),
                msg.getIncomingStatus(), msg.getIncomingMessage().getBytes(), msg.getOutgoingStatus(),
                msg.getOutgoingMessage().getBytes(), msg.getInsertedTimeStamp(), msg.getTTL());
        String sqlIdentity = "SELECT @@IDENTITY AS NewId";
        Connection con = connHelper.getConnection();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                addedCount = stmt.executeUpdate(sql);

                //Retrieve newly Inserted id
                ResultSet rs = stmt.executeQuery(sqlIdentity);
                while (rs.next()) {
                    InsertedId = rs.getInt("NewId");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (!con.isClosed()) {
                        con.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return addedCount;
    }

    @Override
    public String queryBarcodeFromQueuePartDeux(Message msg) {
        int insertedId = 0, someValue = 0, someValue2 = 0;
        Message findMsg = new Message();
        String retValue = "";
        today = new java.sql.Timestamp(utilDate.getTime());
        String sqlAdd =  String.format("INSERT INTO DBA.MessageExchange (SOURCE, MESSAGETYPE, " +
                        "INCOMINGSTATUS, INCOMINGMESSAGE, OUTGOINGSTATUS, OUTGOINGMESSAGE, INSERTEDTIMESTAMP, " +
                        "TTL) VALUES ('%s', '%s', %s, '%s', %s, '%s', '%s', %s)", msg.getSource(), msg.getMessageType(),
                msg.getIncomingStatus(), msg.getIncomingMessage(), msg.getOutgoingStatus(),
                null, msg.getInsertedTimeStamp()!=null?msg.getInsertedTimeStamp():today,
                msg.getTTL()<1?100:msg.getTTL());
        String sqlIdentity = "SELECT @@IDENTITY AS NewId";

        Connection con = connHelper.getConnection();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                //********************	Add	**************************
                someValue = stmt.executeUpdate(sqlAdd);
                someValue2 = someValue + 1;
                someValue2 ++;

                //********************	Retrieve newly inserted identity	********************
                ResultSet rsIdentity = stmt.executeQuery(sqlIdentity);
                while (rsIdentity.next()) {
                    insertedId = rsIdentity.getInt("NewId");
                }
                Thread.sleep(2000); //sleep for 2 seconds giving msg exchange time to generate an output
                //********************	Find	********************
                String sqlFind = String.format("SELECT m.MESSAGEID, m.SOURCE, m.MESSAGETYPE, " +
                        "m.INCOMINGSTATUS, m.INCOMINGMESSAGE, m.OUTGOINGSTATUS, " +
                        "m.OUTGOINGMESSAGE, m.INSERTEDTIMESTAMP, m.TTL FROM MessageExchange m " +
                        "WHERE m.MESSAGEID = %s", insertedId);
                ResultSet rsFind  = stmt.executeQuery(sqlFind);
                while (rsFind.next()) {
                    findMsg.setMessageId(rsFind.getInt("MESSAGEID"));
                    findMsg.setSource(rsFind.getString("SOURCE"));
                    findMsg.setMessageType(rsFind.getString("MESSAGETYPE"));
                    findMsg.setIncomingStatus(rsFind.getInt("INCOMINGSTATUS"));
                    byte[] incomingMsg = rsFind.getBytes("INCOMINGMESSAGE");
                    findMsg.setIncomingMessage(incomingMsg.toString());
                    findMsg.setOutgoingStatus(rsFind.getInt("OUTGOINGSTATUS"));
                    byte[] outgoingMsg = rsFind.getBytes("OUTGOINGMESSAGE");
                    findMsg.setOutgoingMessage(new String(outgoingMsg));
                    findMsg.setInsertedTimeStamp(rsFind.getTimestamp("INSERTEDTIMESTAMP"));
                    findMsg.setTTL(rsFind.getInt("TTL"));
                }
                retValue = findMsg.getOutgoingMessage();
            } catch (Exception ex) {
                ex.printStackTrace();
                today = new java.sql.Timestamp(utilDate.getTime());
                LogEntry log = new LogEntry(1L, "", "queryBarcodeFromQueue", "", ex.getClass().getSimpleName(), ex.getMessage(), today);
                logger.Add(log);
            } finally {
                try {
                    if (!con.isClosed()) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    today = new java.sql.Timestamp(utilDate.getTime());
                    LogEntry log = new LogEntry(1L, "", "queryBarcodeFromQueue", "", e.getClass().getSimpleName(), e.getMessage(), today);
                    logger.Add(log);
                }
            }
        }

        return retValue;
    }

}

