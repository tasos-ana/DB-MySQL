package cs359db.db;

import cs359db.model.Customer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giwrgos
 */
public class CustomerDB {

    public static void openAccount(String email) {
        

    }

    /**
     * Get customer
     *
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Customer> getCustomers() throws ClassNotFoundException {
        List<Customer> customers = new ArrayList<>();

        try {
            try (Connection con = CS360DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM customer;");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                while (res.next() == true) {
                    Customer customer = new Customer();
                    customer.setEmail(res.getString("email"));
                    customers.add(customer);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return customers;
    }

    /**
     * Get user
     *
     * @param email
     * @return
     * @throws ClassNotFoundException
     */
    public static Customer getCustomer(String email) throws ClassNotFoundException {
        Customer user = new Customer();
        try {
            try (Connection con = CS360DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM customer ")
                        .append(" WHERE ")
                        .append(" EMAIL = ").append("'").append(email).append("';");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                if (res.next() == true) {
                    user.setEmail(res.getString("email"));
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    /**
     * Establish a database connection and add the member in the database.
     *
     * @param customer
     * @throws ClassNotFoundException
     */
    public static void addCustomer(Customer customer) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            customer.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = CS360DB.getConnection();
                    Statement stmt = con.createStatement()) {

                Date date = new Date();
                Timestamp timestamp = new Timestamp(date.getTime());

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("INSERT INTO ")
                        .append(" customer (EMAIL) ")
                        .append(" VALUES (")
                        .append("'").append(customer.getEmail()).append("');");
                //.append("'").append(timestamp).append("');");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The member was successfully added in the database.");

                // Close connection
                stmt.close();
                con.close();

            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates information for specific customer
     *
     * @param customer
     * @throws ClassNotFoundException
     */
    public static void updateCustomer(Customer customer) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            customer.checkFields();
        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = CS360DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("UPDATE customer ")
                        .append(" SET ")
                        .append(" EMAIL = ").append("'").append(customer.getEmail()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The member was successfully updated in the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete information for specific customer
     *
     * @param customer
     * @throws ClassNotFoundException
     */
    public static void deleteCustomer(Customer customer) throws ClassNotFoundException {

        try {
            try (Connection con = CS360DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM customer ")
                        .append(" WHERE ")
                        .append(" EMAIL = ").append("'").append(customer.getEmail()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The member was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete information for specific customer
     *
     * @param email
     * @throws ClassNotFoundException
     */
    public static void deleteCustomer(String email) throws ClassNotFoundException {

        try {
            try (Connection con = CS360DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM customer ")
                        .append(" WHERE ")
                        .append(" EMAIL = ").append("'").append(email).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The member was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean checkValidEmail(String email) throws ClassNotFoundException {
        boolean valid = true;
        try {
            try (Connection con = CS360DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM customer ")
                        .append(" WHERE ")
                        .append(" EMAIL = ").append("'").append(email).append("';");

                stmt.execute(insQuery.toString());
                if (stmt.getResultSet().next() == true) {
                    System.out.println("#DB: The member alreadyExists");
                    valid = false;
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return valid;
    }
}
