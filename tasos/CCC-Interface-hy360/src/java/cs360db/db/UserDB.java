/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs360db.db;

import cs360db.model.MerchantCreditCard;
import cs360db.model.User;
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
 * @author Tasos
 */
class UserDB {

    /**
     * Get Civilian
     *
     * @param type
     * @return
     * @throws ClassNotFoundException
     * @throws java.text.ParseException
     */
    protected static List<User> getUsers(String type) throws ClassNotFoundException, ParseException {

        List<User> users = new ArrayList<>();

        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM ").append(type).append(";");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                while (res.next() == true) {
                    User user = new User(type);
                    user.setEmail(res.getString("ID"));
                    user.setName(res.getString("Name"));
                    user.setType(type);
                    user.getCard().setAccountNumber(res.getInt("Account_number"));
                    CharSequence str = "merchant";
                    if (type.contains(str)) {//it's merchant or employee_merchant
                        if (user.getCard() instanceof MerchantCreditCard) {
                            MerchantCreditCard cc = (MerchantCreditCard) user.getCard();
                            cc.setCurrentDebt(res.getDouble("Debt_to_ccc"));
                            cc.setSupply(res.getFloat("Supply"));
                            cc.setTotalProfit(res.getDouble("Total_profit"));
                        } else {
                            assert (false);
                        }
                    } else {//it's company or civilian
                        user.getCard().setAvailableCreditBalance(res.getDouble("Available_credit_balance"));
                        user.getCard().setCreditLimit(res.getDouble("Credit_limit"));
                        user.getCard().setCurrentDebt(res.getDouble("Cuurent_debt"));
                        user.getCard().setValidThru(res.getString("Valid_thru"));
                    }
                    users.add(user);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    /**
     * Get user
     *
     * @param email
     * @param type
     * @return
     * @throws ClassNotFoundException
     * @throws java.text.ParseException
     */
    protected static User getUser(String email, String type) throws ClassNotFoundException, ParseException {
        User user = new User(type);
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM ").append(type).append(";");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                if (res.next() == true) {
                    user.setEmail(res.getString("ID"));
                    user.setName(res.getString("Name"));
                    user.setType(type);
                    user.getCard().setAccountNumber(res.getInt("Account_number"));
                    CharSequence str = "merchant";
                    if (type.contains(str)) {//it's merchant or employee_merchant
                        if (user.getCard() instanceof MerchantCreditCard) {
                            MerchantCreditCard cc = (MerchantCreditCard) user.getCard();
                            cc.setCurrentDebt(res.getDouble("Debt_to_ccc"));
                            cc.setSupply(res.getFloat("Supply"));
                            cc.setTotalProfit(res.getDouble("Total_profit"));
                        } else {
                            assert (false);
                        }
                    } else {//it's company or civilian
                        user.getCard().setAvailableCreditBalance(res.getDouble("Available_credit_balance"));
                        user.getCard().setCreditLimit(res.getDouble("Credit_limit"));
                        user.getCard().setCurrentDebt(res.getDouble("Current_debt"));
                        user.getCard().setValidThru(res.getString("Valid_thru"));
                    }
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    /**
     * Establish a database connection and add the member in the database.
     *
     * @param user
     * @throws ClassNotFoundException
     */
    protected static void addUser(User user) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            user.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                CharSequence str = "merchant";
                String table = user.getType();
                if (table.contains(str)) {//if it's merchant or employee merchant
                    MerchantCreditCard card;
                    if (user.getCard() instanceof MerchantCreditCard) {
                        card = (MerchantCreditCard) user.getCard();
                    } else {
                        card = null;
                        assert (false);
                    }

                    insQuery.append("INSERT INTO ")
                            .append(table).append(" (ID, NAME, ACCOUNT_NUMBER, TOTAL_PROFIT, DEBT_TO_CCC, SUPPLY)")
                            .append(" VALUES (")
                            .append("'").append(user.getEmail()).append("',")
                            .append("'").append(user.getName()).append("',")
                            .append("'").append(card.getAccountNumber()).append("',")
                            .append("'").append(card.getTotalProfit()).append("',")
                            .append("'").append(card.getCurrentDebt()).append("',")
                            .append("'").append(card.getSupply()).append("');");
                } else {//otherwise the rest table's
                    insQuery.append("INSERT INTO ")
                            .append(table).append(" (ID, NAME, ACCOUNT_NUMBER, VALID_THRU,CREDIT_LIMIT, CURRENT_DEBT, AVAILABLE_CREDIT_BALANCE)")
                            .append(" VALUES (")
                            .append("'").append(user.getEmail()).append("',")
                            .append("'").append(user.getName()).append("',")
                            .append("'").append(user.getCard().getAccountNumber()).append("',")
                            .append("'").append(user.getCard().getValidThru()).append("',")
                            .append("'").append(user.getCard().getCreditLimit()).append("',")
                            .append("'").append(user.getCard().getCurrentDebt()).append("',")
                            .append("'").append(user.getCard().getAvailableCreditBalance()).append("');");
                }
                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The user was successfully added in the database.");

                // Close connection
                stmt.close();
                con.close();

            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates information for specific user
     *
     * @param user
     * @throws ClassNotFoundException
     */
    protected static void updateUser(User user) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            user.checkFields();
        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                CharSequence str = "merchant";
                String table = user.getType();
                if (table.contains(str)) {//if it's merchant or employee merchant
                    MerchantCreditCard card;
                    if (user.getCard() instanceof MerchantCreditCard) {
                        card = (MerchantCreditCard) user.getCard();
                    } else {
                        card = null;
                        assert (false);
                    }
                    insQuery.append("UPDATE ").append(table)
                            .append(" SET ")
                            .append(" NAME = ").append("'").append(user.getName()).append("',")
                            .append(" ACCOUNT_NUMBER = ").append("'").append(card.getAccountNumber()).append("',")
                            .append(" TOTAL_PROFIT = ").append("'").append(card.getTotalProfit()).append("',")
                            .append(" DEBT_TO_CCC = ").append("'").append(card.getCurrentDebt()).append("',")
                            .append(" SUPPLY = ").append("'").append(card.getSupply()).append("',")
                            .append(" WHERE ID = ").append("'").append(user.getEmail()).append("';");
                } else {//otherwise the rest table's
                    insQuery.append("UPDATE ").append(table)
                            .append(" SET ")
                            .append(" NAME = ").append("'").append(user.getName()).append("',")
                            .append(" ACCOUNT_NUMBER = ").append("'").append(user.getCard().getAccountNumber()).append("',")
                            .append(" VALID_THRU = ").append("'").append(user.getCard().getValidThru()).append("',")
                            .append(" CREDIT_LIMIT = ").append("'").append(user.getCard().getCreditLimit()).append("',")
                            .append(" CURRENT_DEBT = ").append("'").append(user.getCard().getCurrentDebt()).append("',")
                            .append(" AVAILABLE_CREDIT_BALANCE = ").append("'").append(user.getCard().getAvailableCreditBalance()).append("',")
                            .append(" WHERE ID = ").append("'").append(user.getEmail()).append("';");
                }

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The user was successfully updated in the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete information for specific user
     *
     * @param user
     * @throws ClassNotFoundException
     */
    protected static void deleteUser(User user) throws ClassNotFoundException {
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                String table = user.getType();

                insQuery.append("DELETE FROM ").append(table)
                        .append(" WHERE ")
                        .append(" ID = ").append("'").append(user.getEmail()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The user was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete information for specific user
     *
     * @param email
     * @throws ClassNotFoundException
     */
    protected static boolean deleteUser(String email) throws ClassNotFoundException {
        boolean exist = false;
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                if (innerExistID(stmt, "civilian", email)) {
                    innerDeleteUser(stmt, "civilian", email);
                    exist = true;
                } else if (innerExistID(stmt, "company", email)) {
                    innerDeleteUser(stmt, "company", email);
                    exist = true;
                } else if (innerExistID(stmt, "employee_civilian", email)) {
                    innerDeleteUser(stmt, "employee_civilian", email);
                    exist = true;
                } else if (innerExistID(stmt, "employee_merchant", email)) {
                    innerDeleteUser(stmt, "employee_merchant", email);
                    exist = true;
                } else if (innerExistID(stmt, "merchant", email)) {
                    innerDeleteUser(stmt, "merchant", email);
                    exist = true;
                } else {
                    exist = false;
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

    private static void innerDeleteUser(Statement stmt, String table, String email) throws SQLException {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append("DELETE FROM ").append(table)
                .append(" WHERE ")
                .append(" ID = ").append("'").append(email).append("';");

        stmt.executeUpdate(insQuery.toString());
        System.out.println("#DB: The civilian was successfully deleted from the database.");
    }

    protected static boolean existID(String email) throws ClassNotFoundException {
        boolean exist = false;
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                if (innerExistID(stmt, "civilian", email)) {
                    exist = true;
                }else if(innerExistID(stmt, "company", email)){
                    exist = true;
                }else if(innerExistID(stmt, "company", email)){
                    exist = true;
                }else if(innerExistID(stmt, "employee_civilian", email)){
                    exist = true;
                }else if(innerExistID(stmt, "employee_merchant", email)){
                    exist = true;
                }else if(innerExistID(stmt, "merchant", email)){
                    exist = true;
                }else{
                    exist = false;
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
