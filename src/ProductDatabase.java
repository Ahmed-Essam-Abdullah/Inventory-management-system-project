
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author AliAl
 */
public class ProductDatabase {

    private String filename;
    private ArrayList<Product> records;

    public ProductDatabase(String filename) {
        this.filename = filename;
        this.records = new ArrayList();
    }

    public Product createRecordFrom(String line) {
        String[] recordsString = line.split(",");
        String productID=recordsString[0];
        String productName=recordsString[1];
        String manufacturerName=recordsString[2];
        String supplierName=recordsString[3];
        int quantity =Integer.parseInt(recordsString[4]);
        float price=Float.parseFloat(recordsString[5]);
        Product elementProduct=new Product(productID, productName, manufacturerName, supplierName, quantity, price);
        return elementProduct;
    }
}
