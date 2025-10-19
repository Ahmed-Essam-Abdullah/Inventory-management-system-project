
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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

    public void readFromFile() throws FileNotFoundException {
        File file = new File(this.filename);
        Scanner scan = new Scanner(file);
        String line;

        while (scan.hasNextLine()) {
            line = scan.nextLine();
            Product elementProduct = createRecordFrom(line);
            this.records.add(elementProduct);
        }
        scan.close();

    }

    public boolean contains(String key) {
        for (Product elemenProduct : records) {
            if (elemenProduct.getSearchKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public Product getRecord(String key) {
        for (Product elemenProduct : records) {
            if (elemenProduct.getSearchKey().equals(key)) {
                return elemenProduct;
            }
        }
        return null;
    }

    public void insertRecord(Product record) {
        this.records.add(record);
    }

    public void deleteRecord(String key) {
        Product recodProduct = getRecord(key);
        if (recodProduct != null) {
            this.records.remove(recodProduct);
        }
    }

    public void saveToFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(this.filename);
        for (Product record : records) {
            writer.print(record.lineRepresentation());
        }
        writer.close();
    }

    public Product createRecordFrom(String line) {
        String[] recordsString = line.split(",");
        String productID = recordsString[0];
        String productName = recordsString[1];
        String manufacturerName = recordsString[2];
        String supplierName = recordsString[3];
        int quantity = Integer.parseInt(recordsString[4]);
        float price = Float.parseFloat(recordsString[5]);
        Product elementProduct = new Product(productID, productName, manufacturerName, supplierName, quantity, price);
        return elementProduct;
    }
}
