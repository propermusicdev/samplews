package data.Repositories;

import data.Helpers.ConnectionHelper;
import data.Product;
import data.core.IProductRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public class ProductRepository implements IProductRepository {
    private ConnectionHelper connHelper = new ConnectionHelper();

    @Override
    public Product GetProductByBarcode(String barcode) {
        final String qry = new String(String.format("SELECT TOP 1 p.ProductId, p.SuppCode, p.SupplierCat, p.Format, " +
                "p.Artist, p.Title, p.ShortDesc, p.Barcode, p.OnHand, p.DealerPrice, p.BinNo, " +
                "p.Price1, p.OutOfStock FROM Product p WHERE p.SuppCode = 'PROP' AND p.EAN = '%s' " +
                "ORDER BY p.ProductId ASC", barcode));
        Product prod = new Product();
        Connection dbcon = connHelper.getConnection();

        if (dbcon != null) {
            try {
                Statement stmt = dbcon.createStatement();
                stmt.setCursorName("Cur_Product");
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    prod.setProductId(rs.getInt("ProductId"));
                    prod.setSuppCode(rs.getString("SuppCode"));
                    prod.setSupplierCat(rs.getString("SupplierCat"));
                    prod.setFormat(rs.getString("Format"));
                    prod.setArtist(rs.getString("Artist"));
                    prod.setTitle(rs.getString("Title"));
                    prod.setShortDescription(rs.getString("ShortDesc"));
                    prod.setBarcode(rs.getString("Barcode"));
                    prod.setOnHand(rs.getInt("OnHand"));
                    prod.setDealerPrice(rs.getFloat("DealerPrice"));
                    prod.setBinNo(rs.getString("BinNo"));
                    prod.setPrice1(rs.getFloat("Price1"));
                    prod.setOutOfStock(rs.getInt("OutOfStock"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (!dbcon.isClosed()) {
                        dbcon.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return prod;
    }

    @Override
    public List<Product> GetTop20Products() {
        final String qry = new String("SELECT TOP 20 p.ProductId, p.SuppCode, p.SupplierCat, p.Format, " +
                "p.Artist, p.Title, p.ShortDesc, p.Barcode, p.OnHand, p.DealerPrice, p.BinNo, " +
                "p.Price1, p.OutOfStock FROM Product p WHERE p.SuppCode = 'PROP' AND " +
                "p.Artist <> '' AND p.Title <> '' AND p.Barcode <> '' AND p.Price1 > 0 " +
                "AND p.OnHand > 0 ORDER BY p.ProductId ASC");
        List<Product> prodList = new ArrayList<Product>();
        Connection dbcon = connHelper.getConnection();

        if (dbcon != null) {
            try {
                Statement stmt = dbcon.createStatement();
                stmt.setCursorName("Cur_Product");
                ResultSet rs = stmt.executeQuery(qry);
                while (rs.next()) {
                    Product prod = new Product();
                    prod.setProductId(rs.getInt("ProductId"));
                    prod.setSuppCode(rs.getString("SuppCode"));
                    prod.setSupplierCat(rs.getString("SupplierCat"));
                    prod.setFormat(rs.getString("Format"));
                    prod.setArtist(rs.getString("Artist"));
                    prod.setTitle(rs.getString("Title"));
                    prod.setShortDescription(rs.getString("ShortDesc"));
                    prod.setBarcode(rs.getString("Barcode"));
                    prod.setOnHand(rs.getInt("OnHand"));
                    prod.setDealerPrice(rs.getFloat("DealerPrice"));
                    prod.setBinNo(rs.getString("BinNo"));
                    prod.setPrice1(rs.getFloat("Price1"));
                    prod.setOutOfStock(rs.getInt("OutOfStock"));
                    prodList.add(prod);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (!dbcon.isClosed()) {
                        dbcon.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return prodList;
    }
}
