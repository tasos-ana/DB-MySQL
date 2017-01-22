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
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.AccountNumberGenerator;

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

    public static ArrayList<String> getBuyMerchants() throws ClassNotFoundException {
        return UserDB.getBuyMerchants();
    }

    public static ArrayList<String> getRefundMerchants(String email, String type) throws ClassNotFoundException {
        return UserDB.getRefundMerchants(email, type);
    }

    public static ArrayList<String> getCompanyEmployee(String email) throws ClassNotFoundException {
        return UserDB.getCompanyEmployee(email);
    }

    public static ArrayList<String> getGoodCustomers() throws ClassNotFoundException {
        return UserDB.getArray(Queries.getGoodCustomers());
    }

    public static ArrayList<String> getBadCustomers() throws ClassNotFoundException {
        return UserDB.getArray(Queries.getBadCustomers());
    }

    public static ArrayList<String> getMonthMerchants() throws ClassNotFoundException {
        return UserDB.getArray(Queries.getMonthMerchants());
    }

    public static boolean deleteUser(String email, String type) throws ParseException, ClassNotFoundException {
        return UserDB.deleteUser(email, type);
    }

    public static boolean existID(String email) throws ClassNotFoundException {
        return UserDB.existUser(email);
    }

    public static boolean existID(String email, String table) throws ClassNotFoundException {
        return UserDB.existUser(email, table);
    }

    public static boolean existEmployee(String email, String companyID, String table) throws ClassNotFoundException {
        boolean exist = false;
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                stmt.execute(Queries.existEmployee(email, companyID, table));

                if (stmt.getResultSet().next() == true) {
                    System.out.println("#DB: The member: '" + email + "' is employee of company: '" + companyID + "'");
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

    public static boolean makeTransaction(String civilianID, String merchantID,
            String civilianType, String transType, double value) throws ClassNotFoundException {
        boolean state = false, executeSucceed = false;
        String updateCivilianQuery, updateMerchantQuery;
        String merchantType = UserDB.getMerchantType(merchantID);

        java.util.Date dateStr = new java.util.Date();
        java.sql.Date currDate = new java.sql.Date(dateStr.getTime());
        if (transType.equals("credit")) {
            updateCivilianQuery = Queries.updateCivilian_credit(civilianID, civilianType, value, currDate);
            updateMerchantQuery = Queries.updateMerchant_credit(merchantID, merchantType, value);
        } else {
            assert (transType.equals("charge"));
            updateCivilianQuery = Queries.updateCivilian_charge(civilianID, civilianType, value, currDate);
            updateMerchantQuery = Queries.updateMerchant_charge(merchantID, merchantType, value);
        }
        executeSucceed = UserDB.executeUpdate(updateCivilianQuery, updateMerchantQuery);

        if (executeSucceed) {
            StringBuilder transTable = new StringBuilder();
            transTable.append(merchantType.replace("employee_", "e"))
                    .append("_transaction_")
                    .append(civilianType.replace("employee_", "e"));
            int transID = Integer.parseInt(new AccountNumberGenerator().generate("3", 9));

            TransactionDB.recordTransaction(merchantID, civilianID, transID, value,
                    transType, currDate, transTable.toString());

            state = true;
        }
        return state;
    }

    public static ArrayList<ArrayList<String>> getSearchResults(String insQuery) throws ClassNotFoundException {
        return UserDB.getSearchResults(insQuery);
    }

    public static ArrayList<String> getCoopedCivilian(String id, String type) throws ClassNotFoundException {
        return UserDB.getCoopedCivilian(id, type);
    }

    public static ArrayList<String> getAllCustomers(String type) throws ClassNotFoundException {
        return UserDB.getAllCustomers(type);
    }

    public static ArrayList<String> getHalfCustomers(String type,String companyID) throws ClassNotFoundException {
        return UserDB.getHalfCustomers(type,companyID);
    }
}
