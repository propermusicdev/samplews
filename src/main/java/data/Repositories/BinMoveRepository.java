package data.Repositories;

import data.BinMove;
import data.Helpers.ConnectionHelper;
import data.core.IMoveRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public class BinMoveRepository implements IMoveRepository {
    private ConnectionHelper connHelper = new ConnectionHelper();

    @Override
    public List<BinMove> GetAll() {
        String qry = "SELECT m.TransactionId, m.SourceBin, m.DestinationBin, m.Quantity " +
                "m.MovedItem, m.MovedDate FROM DBA.tblBinMove m ORDER BY m.TransactionId ASC";
        List<BinMove> list = new ArrayList<BinMove>();
        Connection con = connHelper.getConnection();
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                stmt.setCursorName("Cursor_Move");
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    BinMove move = new BinMove();
                    move.setTransactionId(rs.getLong("TransactionId"));
                    move.setSourceBin(rs.getString("SourceBin"));
                    move.setDestinationBin(rs.getString("DestinationBin"));
                    move.setQuantity(rs.getInt("Quantity"));
                    move.setMovedItem(rs.getBytes("MovedItem"));
                    move.setMovedDate(rs.getTimestamp("MovedDate"));
                    list.add(move);
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
    public BinMove Find(int id) {
        String qry = String.format("SELECT m.TransactionId, m.SourceBin, m.DestinationBin, m.MoveType " +
                "FROM BinMovePrepared m WHERE m.TransactionId = %s", id);
        BinMove move = new BinMove();
        //Connection dbCon = getConnection();
        Connection dbCon = connHelper.getConnection();
        if (dbCon != null) {
            try {
                Statement stmt = dbCon.createStatement();
                stmt.setCursorName("Cursor_Move");
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    move.setTransactionId(rs.getLong("TransactionId"));
                    move.setSourceBin(rs.getString("SourceBin"));
                    move.setDestinationBin(rs.getString("DestinationBin"));
                    move.setQuantity(rs.getInt("Quantity"));
                    move.setMovedItem(rs.getBytes("MovedItem"));
                    move.setMovedDate(rs.getTimestamp("MovedDate"));
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
        return move;
    }

    @Override
    public int Add(BinMove move) {
        int addedcount = 0;
        String qry = String.format("INSERT INTO DBA.tblBinMove (SourceBin, DestinationBin, Quantity, " +
                        "MovedItem, MovedDate) VALUES ('%s', '%s', %s, %s, '%s')", move.getSourceBin(),
                move.getDestinationBin(), move.getQuantity(), move.getMovedItem(), move.getMovedDate());
        String qry1 = "SELECT @@IDENTITY AS NewId";
        Connection conn = connHelper.getConnection();

        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                addedcount = stmt.executeUpdate(qry);

                //return ID
                Long insertedId = (long) 0;
                ResultSet rs = stmt.executeQuery(qry1);
                while (rs.next()) {
                    insertedId = rs.getLong("NewId");
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (!conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return addedcount;
    }

}

