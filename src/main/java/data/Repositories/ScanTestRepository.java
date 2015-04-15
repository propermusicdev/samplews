package data.Repositories;

import data.Helpers.ConnectionHelper;
import data.ScanTest;
import data.core.IScanTestRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public class ScanTestRepository implements IScanTestRepository {
    private ConnectionHelper connHelper = new ConnectionHelper();

    @Override
    public ScanTest GetScanById(int transId) {
        String qry = String.format("SELECT s.ProductId, s.BSSID, s.Channel, s.QueryTime, s.OriginatedBin, " +
                "s.EndPointLocation FROM DBA.tblScannerTest s WHERE s.TransactionId = %s", transId);
        ScanTest test = new ScanTest();
        Connection dbCon = connHelper.getConnection();
        if (dbCon != null) {
            try {
                Statement stmt = dbCon.createStatement();
                stmt.setCursorName("Cur_Test");
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    test.setTransactionId(rs.getLong("TransactionId"));
                    test.setProductId(rs.getInt("ProductId"));
                    test.setBssId(rs.getString("BSSID"));
                    test.setChannel(rs.getInt("Channel"));
                    test.setQueryTime(rs.getLong("QueryTime"));
                    test.setOriginatedBin(rs.getString("OriginatedBin"));
                    test.setEndPointLocation(rs.getString("EndPointLocation"));
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
        return test;
    }

    @Override
    public ScanTest AddScan(ScanTest test) {
        int addedcount = 0;
        String qry = String.format("INSERT INTO DBA.tblScannerTest (ProductId, BSSID, Channel, QueryTime, " +
             "OriginatedBin, EndPointLocation) VALUES (%s, '%s', %s, %s, '%s', '%s')", test.getProductId(),
             test.getBssId(), test.getChannel(), test.getQueryTime(), test.getOriginatedBin(),
             test.getEndPointLocation());
        String qry1 = "SELECT @@IDENTITY AS NewId";
        String qry2 = "";
        ScanTest retScan = new ScanTest();
        Connection dbCon = connHelper.getConnection();
        if (dbCon != null) {
            try {
                Statement stmt = dbCon.createStatement();
                addedcount = stmt.executeUpdate(qry);

                //return ID
                Long insertedId = (long) 0;
                ResultSet rs = stmt.executeQuery(qry1);
                while (rs.next()) {
                    insertedId = rs.getLong("NewId");
                }

                if (addedcount > 1) {addedcount ++;}
                if (addedcount > 2) {addedcount --;}

                //select newly inserted scan
                qry2 = String.format("SELECT s.TransactionId, s.ProductId, s.BSSID, s.Channel, " +
                        "s.QueryTime, s.OriginatedBin, s.EndPointLocation FROM DBA.tblScannerTest s " +
                        "WHERE s.TransactionId = %s", insertedId);

                ResultSet rs1 = stmt.executeQuery(qry2);
                while (rs1.next()) {
                    retScan.setTransactionId(rs1.getLong("TransactionId"));
                    retScan.setProductId(rs1.getInt("ProductId"));
                    retScan.setBssId(rs1.getString("BSSID"));
                    retScan.setChannel(rs1.getInt("Channel"));
                    retScan.setQueryTime(rs1.getLong("QueryTime"));
                    retScan.setOriginatedBin(rs1.getString("OriginatedBin"));
                    retScan.setEndPointLocation(rs1.getString("EndPointLocation"));
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
        return retScan;
    }

    @Override
    public List<ScanTest> GetTop20Scans() {
        String qry = "SELECT TOP 20 s.TransactionId, s.ProductId, s.BSSID, s.Channel, s.QueryTime, " +
                "s.OriginatedBin, s.EndPointLocation FROM DBA.tblScannerTest s ORDER BY s.TransactionId ASC";
        List<ScanTest> list = new ArrayList<ScanTest>();
        Connection con = connHelper.getConnection();
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                stmt.setCursorName("Cur_Test");
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    ScanTest test = new ScanTest();
                    test.setTransactionId(rs.getLong("TransactionId"));
                    test.setProductId(rs.getInt("ProductId"));
                    test.setBssId(rs.getString("BSSID"));
                    test.setChannel(rs.getInt("Channel"));
                    test.setQueryTime(rs.getLong("QueryTime"));
                    test.setOriginatedBin(rs.getString("OriginatedBin"));
                    test.setEndPointLocation(rs.getString("EndPointLocation"));
                    list.add(test);
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
}
