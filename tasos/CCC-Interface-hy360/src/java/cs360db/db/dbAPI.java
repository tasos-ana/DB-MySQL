/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs360db.db;

import cs360db.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giwrgos
 */
public class dbAPI {

    private static final String URL = "jdbc:mysql://localhost";
    private static final String DATABASE = "hy360";
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

    //company, merchant, civilian
    public static void addEntity(String email, String name, String type)
            throws ClassNotFoundException, ParseException {
        UserDB.addUser(new User(email, name, type));
    }

    //employee in { merchant, civilian }
    public static void addEntity(String email, String name, String type, String companyID)
            throws ClassNotFoundException, ParseException {
        User user = new User(email, name, type);
        UserDB.addUser(user, companyID);
    }

    public static User getUser(String email, String type) throws ParseException, ClassNotFoundException {
        return UserDB.getUser(email, type);
    }

//    public static boolean deleteUser(String type, String email) throws ParseException, ClassNotFoundException {
//        return UserDB.deleteUser(email);
//    }
    
    //done
    public static boolean existID(String email) throws ClassNotFoundException {
        boolean exist = false;
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                stmt.execute(Queries.exists(email));

                if (stmt.getResultSet().next() == true) {
                    System.out.println("#DB: The member alreadyExists");
                    exist = true;
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return exist;
    }
    
    //done
    public static boolean existID(String email, String table) throws ClassNotFoundException {
        boolean exist = false;
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                stmt.execute(Queries.exists(email, table));
                
                if (stmt.getResultSet().next() == true) {
                    System.out.println("#DB: The member alreadyExists");
                    exist = true;
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return exist;
    }
}
