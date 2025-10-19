import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class EmployeeRole {
    private ProductDatabase productsDatabase;
    private CustomerProductDatabase customerProductDatabase;
    public EmployeeRole(){
        this.productsDatabase = new ProductDatabase("Products.txt");
        productsDatabase.readFromFile();
        this.customerProductDatabase = new CustomerProductDatabase("CustomersProducts.txt");
        customerProductDatabase.readFromFile();
    }
    public void addProduct(String productID, String productName, String
            manufacturerName, String supplierName, int quantity){
        if(productsDatabase.contains(productID)){
            System.out.println("Product already exists");
            return;
        }
        Product p = new Product(productID,productName,manufacturerName,supplierName,quantity);
        productsDatabase.insertRecord(p);
        productsDatabase.saveToFile();
        System.out.println("Product is added successfully");
    }
    public Product[] getListOfProducts() {
        ArrayList<Product> records = productsDatabase.returnAllRecords();
        return records.toArray(new Product[0]);
    }

    public CustomerProduct[] getListOfPurchasingOperations() {
        ArrayList<CustomerProduct> records = customerProductDatabase.returnAllRecords();
        return records.toArray(new CustomerProduct[0]);
    }
    public boolean purchaseProduct(String customerSSN, String
            productID, LocalDate purchaseDate){
        if(!productsDatabase.contains(productID)){
            System.out.println("The product is not found");
            return false;
        }
        Product p = productsDatabase.getRecord(productID);
        if(p.getQuantity()== 0){
            System.out.println("The product is out of stock");
            return false;
        }
        p.setQuantity(p.getQuantity()-1);
        productsDatabase.saveToFile();

        CustomerProduct c = new CustomerProduct(customerSSN,productID,purchaseDate);
        customerProductDatabase.insertRecord(c);
        customerProductDatabase.saveToFile();
        return true;
    }
    private String formatDate(LocalDate date) {
        return String.format("%02d-%02d-%04d", date.getDayOfMonth(),
                date.getMonthValue(),
                date.getYear());
    }

    public double returnProduct(String customerSSN, String productID,
                                LocalDate purchaseDate ,LocalDate returnDate)
    {
        if(returnDate.isBefore(purchaseDate)){
            System.out.println("Return date can't be before purchase date");
            return -1;
        }
        if(!productsDatabase.contains(productID)){
            System.out.println("We don't sell this product");
            return -1;
        }
        String search = customerSSN + "," +productID+ ","+ formatDate(purchaseDate);
        if(!customerProductDatabase.contains(search)){
            System.out.println("Sorry, You didn't buy that product");
            return -1;
        }
        long daysBetween = ChronoUnit.DAYS.between(purchaseDate, returnDate);
        if (daysBetween > 14) {
            System.out.println("Sorry, you can't return a product after 14 days of purchase date");
            return -1;
        }

        Product p = productsDatabase.getRecord(productID);
        p.setQuantity(p.getQuantity()+1);
        customerProductDatabase.deleteRecord(search);
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
        System.out.println("Product returned successfully");
        return p.getPrice();
    }
    public boolean applyPayment(String customerSSN, LocalDate purchaseDate)
    {
        ArrayList<CustomerProduct> purchases = customerProductDatabase.returnAllRecords();
        for(CustomerProduct c:purchases)
        {
            String ssn= c.getCustomerSSN();
            LocalDate d= c.getPurchaseDate();
            if (ssn.equals(customerSSN) && d.equals(purchaseDate))
            {
                if(!c.isPaid()) {
                    c.setPaid(true);
                    System.out.println("Payment is applied successfully!");
                    customerProductDatabase.saveToFile();
                    return true;
                }
                else{
                    System.out.println("paid is already done");
                    return false;
                }
            }
        }
        System.out.println("Sorry purchase record is not found!");
        return false;
    }
    public void logout(){
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
        System.out.println("Entire data is saved successfully");
    }
}