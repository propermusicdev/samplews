package data.core;

import data.Product;

import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public interface IProductRepository {
    Product GetProductByBarcode(String barcode);
    List<Product> GetTop20Products();
}
