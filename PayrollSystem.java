import java.io.*;
import java.util.*;

// 1. ABSTRACT BASE CLASS
abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String designation;

    public Employee(int id, String name, String designation) {
        this.id = id;
        this.name = name;
        this.designation = designation;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    
    // Abstract methods for Polymorphism
    public abstract double calculateGross();
    public abstract double calculateDeductions();
    public abstract double calculateNet();
    
    public void displaySummary() {
        System.out.printf("ID: %-5d | Name: %-15s | Gross: %-10.2f | Net: %-10.2f%n", 
                          id, name, calculateGross(), calculateNet());
    }
}

// 2. PERMANENT EMPLOYEE SUBCLASS
class PermanentEmployee extends Employee {
    private double basicSalary;

    public PermanentEmployee(int id, String name, String desig, double basicSalary) {
        super(id, name, desig);
        this.basicSalary = basicSalary;
    }

    @Override
    public double calculateGross() {
        return basicSalary + (basicSalary * 0.20); // Basic + 20% HRA
    }

    @Override
    public double calculateDeductions() {
        return calculateGross() * 0.12; // 12% PF Deduction
    }

    @Override
    public double calculateNet() {
        return calculateGross() - calculateDeductions();
    }
}

// 3. CONTRACT EMPLOYEE SUBCLASS
class ContractEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public ContractEmployee(int id, String name, String desig, double rate, int hours) {
        super(id, name, desig);
        this.hourlyRate = rate;
        this.hoursWorked = hours;
    }

    @Override
    public double calculateGross() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public double calculateDeductions() {
        return calculateGross() * 0.05; // 5% Service Tax
    }

    @Override
    public double calculateNet() {
        return calculateGross() - calculateDeductions();
    }
}

// 4. MAIN APPLICATION CLASS
public class PayrollSystem {
    private static List<Employee> employeeList = new ArrayList<>();
    private static final String FILE_NAME = "payroll_data.txt";
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();
        while (true) {
            System.out.println("\n--- PAYROLL MANAGEMENT SYSTEM ---");
            System.out.println("1. Add Permanent Employee\n2. Add Contract Employee");
            System.out.println("3. Display All Records\n4. Search & Payslip\n5. Save & Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: addEmployee(true); break;
                case 2: addEmployee(false); break;
                case 3: displayAll(); break;
                case 4: searchEmployee(); break;
                case 5: saveData(); System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void addEmployee(boolean isPermanent) {
        System.out.print("Enter ID: "); int id = sc.nextInt();
        System.out.print("Enter Name: "); String name = sc.next();
        System.out.print("Enter Designation: "); String desig = sc.next();

        if (isPermanent) {
            System.out.print("Enter Basic Salary: ");
            employeeList.add(new PermanentEmployee(id, name, desig, sc.nextDouble()));
        } else {
            System.out.print("Enter Hourly Rate: "); double rate = sc.nextDouble();
            System.out.print("Enter Hours Worked: "); int hours = sc.nextInt();
            employeeList.add(new ContractEmployee(id, name, desig, rate, hours));
        }
        System.out.println("Employee Added Successfully!");
    }

    private static void displayAll() {
        if (employeeList.isEmpty()) System.out.println("No records found.");
        for (Employee e : employeeList) e.displaySummary();
    }

    private static void searchEmployee() {
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();
        for (Employee e : employeeList) {
            if (e.getId() == id) {
                System.out.println("\n--- PAYSLIP ---");
                System.out.println("Name: " + e.getName());
                System.out.println("Gross Pay: " + e.calculateGross());
                System.out.println("Deductions: " + e.calculateDeductions());
                System.out.println("Net Salary: " + e.calculateNet());
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employeeList);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Save Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadData() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            employeeList = (List<Employee>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Load Error: " + e.getMessage());
        }
    }
}