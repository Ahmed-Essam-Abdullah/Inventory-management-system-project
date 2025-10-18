import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeUserDatabase {
    private ArrayList<EmployeeUser> records;
    private String filename;

    public EmployeeUserDatabase(String filename){
        this.filename = filename;
        this.records = new ArrayList<>();
    }
    public void readFromFile(){
        records.clear();
        try{
            File f= new File(filename);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    EmployeeUser emp = createRecordFrom(line);
                    if (emp != null) {
                        records.add(emp);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    public EmployeeUser createRecordFrom(String line){
            String [] parts= line.split(",");
            if(parts.length !=5) return null;
            return  new EmployeeUser(parts[0],parts[1],parts[2],parts[3],parts[4]);
    }
    public void insertRecord(EmployeeUser record){}
    public boolean contains(String key ){return true;}
    public void saveToFile(){}
    public ArrayList<EmployeeUser> returnAllRecords(){return null;}
    public void deleteRecord(String key){}

}
