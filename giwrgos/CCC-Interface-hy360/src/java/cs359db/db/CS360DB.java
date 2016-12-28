/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs359db.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Giwrgos
 */
public class CS360DB {

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

}
