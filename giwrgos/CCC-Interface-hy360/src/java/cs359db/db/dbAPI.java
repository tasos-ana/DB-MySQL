/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs359db.db;

import cs359db.model.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giwrgos
 */
public class dbAPI {

    private static final String URL = "jdbc:mysql://localhost";
    private static final String DATABASE = "hy359test";
    private static final int PORT = 3306;
    private static final String UNAME = "root";
    private static final String PASSWD = "";

    /**
     * Attempts to establish a database connection using mySql
     *
     * @return a connection to the database
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("---------------->>  Connection attemp");
        return DriverManager.getConnection(URL + ":" + PORT + "/" + DATABASE
                + "?zeroDateTimeBehavior=convertToNull&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", UNAME, PASSWD);
    }

    public static void addEntity(String type, String email)
            throws ClassNotFoundException {
        switch (type) {
            case "customer":
                CustomerDB.addCustomer(new Customer(email));
            case "merchant":

            case "company":

            default:
                assert (false);
        }
    }

    // FIXME tha elegxei olous tou pinakes gia to email
    public static String checkEmail(String email) throws ClassNotFoundException {
        String exists= "not exist";
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM customer ")
                        .append(" WHERE ")
                        .append(" EMAIL = ").append("'").append(email).append("';");

                stmt.execute(insQuery.toString());
                if (stmt.getResultSet().next() == true) {
                    System.out.println("#DB: The member alreadyExists");
                    exists = "customer";
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return exists;
    }

}
