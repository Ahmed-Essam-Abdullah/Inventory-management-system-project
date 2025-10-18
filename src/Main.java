public class Main {
    public static void main(String[] args) {
        EmployeeUser emp = new EmployeeUser("123", "Ahmed", "ahmedessam@gmail.com", "Cleopatra", "01554515739");
        System.out.println(emp.lineRepresentation());
        System.out.println(emp);
    }
}