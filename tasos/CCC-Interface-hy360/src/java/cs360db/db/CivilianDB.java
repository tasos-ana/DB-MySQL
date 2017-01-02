package cs360db.db;

import cs360db.model.Civilian;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giwrgos
 */
public class CivilianDB {

    /**
     * Get Civilian
     *
     * @return
     * @throws ClassNotFoundException
     * @throws java.text.ParseException
     */
    public static List<Civilian> getCivilians() throws ClassNotFoundException, ParseException {
        List<Civilian> civilians = new ArrayList<>();

        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM civilian;");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                while (res.next() == true) {
                    Civilian civilian = new Civilian();
                    civilian.setEmail(res.getString("email"));
                    civilians.add(civilian);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CivilianDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return civilians;
    }

    /**
     * Get user
     *
     * @param email
     * @return
     * @throws ClassNotFoundException
     * @throws java.text.ParseException
     */
    public static Civilian getCivilian(String email) throws ClassNotFoundException, ParseException {
        Civilian user = new Civilian();
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM civilian ")
                        .append(" WHERE ")
                        .append(" ID = ").append("'").append(email).append("';");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                if (res.next() == true) {
                    user.setEmail(res.getString("ID"));
                    System.out.println(res.getString("ID"));
                    user.setName(res.getString("Name"));
                    System.out.println(res.getString("Name"));
                    user.getCard().setAccountNumber(Integer.parseInt(res.getString("Account_number")));
                    System.out.println(res.getString("Account_number"));
                    user.getCard().setAvailableCreditBalance(Integer.parseInt(res.getString("Available_credit_balance")));
                    System.out.println(res.getString("Available_credit_balance"));
                    user.getCard().setCreditLimit(Integer.parseInt(res.getString("Credit_limit")));
                    System.out.println(res.getString("Credit_limit"));
                    user.getCard().setCurrentDebt(Integer.parseInt(res.getString("Current_debt")));
                    System.out.println(res.getString("Current_debt"));
                    user.getCard().setValidThru(res.getString("Valid_thru"));
                    System.out.println(res.getString("Valid_thru"));
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CivilianDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    /**
     * Establish a database connection and add the member in the database.
     *
     * @param civilian
     * @throws ClassNotFoundException
     */
    public static void addCivilian(Civilian civilian) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            civilian.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(CivilianDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("INSERT INTO ")
                        .append(" civilian (ID, NAME, ACCOUNT_NUMBER, VALID_THRU,CREDIT_LIMIT, CURRENT_DEBT, AVAILABLE_CREDIT_BALANCE)")
                        .append(" VALUES (")
                        .append("'").append(civilian.getEmail()).append("',")
                        .append("'").append(civilian.getName()).append("',")
                        .append("'").append(civilian.getCard().getAccountNumber()).append("',")
                        .append("'").append(civilian.getCard().getValidThru()).append("',")
                        .append("'").append(civilian.getCard().getCreditLimit()).append("',")
                        .append("'").append(civilian.getCard().getCurrentDebt()).append("',")
                        .append("'").append(civilian.getCard().getAvailableCreditBalance()).append("');");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The civilian was successfully added in the database.");

                // Close connection
                stmt.close();
                con.close();

            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CivilianDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates information for specific civilian
     *
     * @param civilian
     * @throws ClassNotFoundException
     */
    public static void updateCivilian(Civilian civilian) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            civilian.checkFields();
        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(CivilianDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("UPDATE civilian ")
                        .append(" SET ")
                        .append(" NAME = ").append("'").append(civilian.getName()).append("',")
                        .append(" ACCOUNT_NUMBER = ").append("'").append(civilian.getCard().getAccountNumber()).append("',")
                        .append(" VALID_THRU = ").append("'").append(civilian.getCard().getValidThru()).append("',")
                        .append(" CREDIT_LIMIT = ").append("'").append(civilian.getCard().getCreditLimit()).append("',")
                        .append(" CURRENT_DEBT = ").append("'").append(civilian.getCard().getCurrentDebt()).append("',")
                        .append(" AVAILABLE_CREDIT_BALANCE = ").append("'").append(civilian.getCard().getAvailableCreditBalance()).append("',")
                        .append(" WHERE ID = ").append("'").append(civilian.getEmail()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The civilian was successfully updated in the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CivilianDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete information for specific Civilian
     *
     * @param civilian
     * @throws ClassNotFoundException
     */
    public static void deleteCivilian(Civilian civilian) throws ClassNotFoundException {

        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM civilian ")
                        .append(" WHERE ")
                        .append(" ID = ").append("'").append(civilian.getEmail()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The civilian was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CivilianDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete information for specific Civilian
     *
     * @param email
     * @throws ClassNotFoundException
     */
    public static void deleteCivilian(String email) throws ClassNotFoundException {

        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM civilian ")
                        .append(" WHERE ")
                        .append(" ID = ").append("'").append(email).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The civilian was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CivilianDB.class.getName()).log(Level.SEVERE, null, ex);
        }
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
