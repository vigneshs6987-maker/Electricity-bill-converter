import java.sql.*;
import java.util.Scanner;

class Customer {
    private int customerId;
    private String name;
    private int units;

    public Customer(int customerId, String name, int units) {
        this.customerId = customerId;
        this.name = name;
        this.units = units;
    }
    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public int getUnits() { return units; }
}

public class Main {

    public static double calculateEnergyCharge(int units) {
        if (units <= 100) return units * 1.50;
        else if (units <= 200) return (100 * 1.50) + (units - 100) * 2.50;
        else if (units <= 300) return (100 * 1.50) + (100 * 2.50) + (units - 200) * 4.00;
        else return (100 * 1.50) + (100 * 2.50) + (100 * 4.00) + (units - 300) * 5.00;
    }

    public static void saveBill(Customer c, double energy, double fixed, double gst, double total) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO electricity_bill VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, c.getCustomerId());
            ps.setString(2, c.getName());
            ps.setInt(3, c.getUnits());
            ps.setDouble(4, energy);
            ps.setDouble(5, fixed);
            ps.setDouble(6, gst);
            ps.setDouble(7, total);

            ps.executeUpdate();
            System.out.println("✔ Bill Saved to Database");

            con.close();
        } catch (SQLException e) {
            System.out.println("SQL Error while saving bill");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Other Error while saving bill");
            e.printStackTrace();
        }
    }

    public static void printBill(Customer customer) {

        double energy = calculateEnergyCharge(customer.getUnits());
        double fixed = 50.0;
        double gst = (energy + fixed) * 0.18;
        double total = energy + fixed + gst;

        System.out.println("\n========== ELECTRICITY BILL ==========");
        System.out.println("Customer ID    : " + customer.getCustomerId());
        System.out.println("Customer Name  : " + customer.getName());
        System.out.println("Units          : " + customer.getUnits());
        System.out.println("--------------------------------------");
        System.out.println("Energy Charge  : ₹" + energy);
        System.out.println("Fixed Charge   : ₹" + fixed);
        System.out.println("GST (18%)      : ₹" + gst);
        System.out.println("--------------------------------------");
        System.out.println("TOTAL BILL     : ₹" + total);
        System.out.println("======================================");

        saveBill(customer, energy, fixed, gst, total);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Customer ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Units: ");
        int units = sc.nextInt();

        if (units < 0) {
            System.out.println("Units cannot be negative!");
            return;
        }

        Customer customer = new Customer(id, name, units);
        printBill(customer);

        sc.close();
    }
}
