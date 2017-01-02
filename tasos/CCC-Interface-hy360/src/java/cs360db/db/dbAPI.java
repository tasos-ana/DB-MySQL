/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs360db.db;

import cs360db.model.Civilian;
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

    public static void addEntity(String type, String email, String name)
            throws ClassNotFoundException, ParseException {
        switch (type) {
            case "customer":
                CivilianDB.addCivilian(new Civilian(email, name));
                break;
            case "merchant":

                break;
            case "company":

                break;
            default:
                assert (false);
        }
    }

    public static Object getUser(String type, String email) throws ParseException, ClassNotFoundException {
        switch (type) {
            case "customer":
                return CivilianDB.getCivilian(email);
            case "merchant":
                break;
            case "company":
                break;
            default:
                assert (false);
        }
        return null;
    }
    
    public static boolean deleteUser(String type, String email) throws ParseException, ClassNotFoundException {
        switch (type) {
            case "customer":
                CivilianDB.deleteCivilian(email);
            case "merchant":
                break;
            case "company":
                break;
            default:
                assert (false);
        }
        return false;
    }

    public static boolean existID(String email) throws ClassNotFoundException {
        boolean exist = false;
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                exist = innerExistID(stmt, "civilian", email);
                if (exist) {
                    return exist;
                }

                exist = innerExistID(stmt, "company", email);
                if (exist) {
                    return exist;
                }

                exist = innerExistID(stmt, "employee_civilian", email);
                if (exist) {
                    return exist;
                }

                exist = innerExistID(stmt, "employee_merchant", email);
                if (exist) {
                    return exist;
                }

                exist = innerExistID(stmt, "merchant", email);
                if (exist) {
                    return exist;
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CivilianDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return exist;
    }

    public static boolean existID(String email, String type) throws ClassNotFoundException {
        boolean exist = false;
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                switch (type) {
                    case "customer":
                        exist = innerExistID(stmt, "civilian", email);
                        if (exist) {
                            return exist;
                        }

                        exist = innerExistID(stmt, "employee_civilian", email);
                        if (exist) {
                            return exist;
                        }

                        break;
                    case "merchant":
                        exist = innerExistID(stmt, "merchant", email);
                        if (exist) {
                            return exist;
                        }

                        exist = innerExistID(stmt, "employee_merchant", email);
                        if (exist) {
                            return exist;
                        }
                        break;
                    case "company":
                        exist = innerExistID(stmt, "company", email);
                        if (exist) {
                            return exist;
                        }
                        break;
                    default:
                        assert (false);
                }
                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CivilianDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return exist;
    }

    private static boolean innerExistID(Statement stmt, String table, String email) throws SQLException {
        boolean exist = false;
        /*
                 * Search if email exist on civilian table
         */
        StringBuilder insQuery = new StringBuilder();
        insQuery.append("SELECT * FROM ").append(table)
                .append(" WHERE ")
                .append(" ID = ").append("'").append(email).append("';");
        stmt.execute(insQuery.toString());
        if (stmt.getResultSet().next() == true) {
            System.out.println("#DB: The member alreadyExists");
            exist = true;
        }
        return exist;
    }

}
