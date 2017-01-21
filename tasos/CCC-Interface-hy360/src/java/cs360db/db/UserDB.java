package cs360db.db;

import cs360db.model.Civilian;
import cs360db.model.Company;
import cs360db.model.Merchant;
import cs360db.model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class UserDB {

    protected static boolean existUser(String email) throws ClassNotFoundException {
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

    protected static boolean existUser(String email, String table) throws ClassNotFoundException {
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

    /**
     * Get user
     *
     * @param email
     * @param type
     * @return
     * @throws ClassNotFoundException
     * @throws java.text.ParseException
     */
    protected static User getUser(String email, String type)
            throws ClassNotFoundException, ParseException {
        User user = null;
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                String insQuery = Queries.getUser(email, type);
                stmt.execute(insQuery);
                ResultSet res = stmt.getResultSet();

                if (res.next() == true) {
                    user = exportUser(res, type);
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

    protected static String getMerchantType(String email) throws ClassNotFoundException {
        String type;

        if (existUser(email, "merchant")) {
            type = "merchant";
        } else {
            type = "employee_merchant";
        }
        return type;
    }

    protected static ArrayList<String> getBuyMerchants() throws ClassNotFoundException {
        ArrayList<String> merchantsIDs = new ArrayList<>();
        try (Connection con = dbAPI.getConnection();
                Statement stmt = con.createStatement()) {
            String insQuery;
            insQuery = Queries.getAllMerchants2Buy();

            stmt.execute(insQuery);
            ResultSet res = stmt.getResultSet();

            while (res.next() == true) {
                merchantsIDs.add(res.getString("ID"));
            }

            // Close connection
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return merchantsIDs;
    }

    protected static ArrayList<String> getRefundMerchants(String email, String type) throws ClassNotFoundException {
        ArrayList<String> merchantsIDs = new ArrayList<>();
        try (Connection con = dbAPI.getConnection();
                Statement stmt = con.createStatement()) {
            String insQuery;
            insQuery = Queries.getAllMerchants2Refund(email, type);

            stmt.execute(insQuery);
            ResultSet res = stmt.getResultSet();

            while (res.next() == true) {
                merchantsIDs.add(res.getString("ID"));
            }

            // Close connection
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return merchantsIDs;
    }

    protected static ArrayList<String> getCompanyEmployee(String email) throws ClassNotFoundException {
        ArrayList<String> employeeIDs = new ArrayList<>();
        try (Connection con = dbAPI.getConnection();
                Statement stmt = con.createStatement()) {
            String insQuery;
            insQuery = Queries.getAllCompanyEmployee(email);

            stmt.execute(insQuery);
            ResultSet res = stmt.getResultSet();

            while (res.next() == true) {
                employeeIDs.add(res.getString("ID"));
            }

            // Close connection
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employeeIDs;
    }

    protected static ArrayList<String> getCoopedCivilian(String email, String type) throws ClassNotFoundException {
        ArrayList<String> civilianIDs = new ArrayList<>();
        try (Connection con = dbAPI.getConnection();
                Statement stmt = con.createStatement()) {
            String insQuery;
            insQuery = Queries.getAllCoopedCivilian(email, type);

            stmt.execute(insQuery);
            ResultSet res = stmt.getResultSet();

            while (res.next() == true) {
                civilianIDs.add(res.getString("ID"));
            }

            // Close connection
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return civilianIDs;
    }

    protected static ArrayList<String> getArray(String insQuery) throws ClassNotFoundException {
        ArrayList<String> data = new ArrayList<>();
        try (Connection con = dbAPI.getConnection();
                Statement stmt = con.createStatement()) {

            stmt.execute(insQuery);
            ResultSet res = stmt.getResultSet();

            while (res.next() == true) {
                data.add(res.getString("ID"));
                try {
                    data.add(res.getString("Debt"));
                } catch (SQLException e) {
                }
            }

            // Close connection
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    protected static String getCompany(String email, String type) throws ClassNotFoundException {
        String companyID = null;
        try (Connection con = dbAPI.getConnection();
                Statement stmt = con.createStatement()) {
            String insQuery;
            insQuery = Queries.getCompany(email, type);

            stmt.execute(insQuery);
            ResultSet res = stmt.getResultSet();

            if (res.next() == true) {
                companyID = res.getString("Company_ID");
            }
            // Close connection
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return companyID;
    }

    /**
     * Establish a database connection and add the member in the database.
     * member can be company,civilian or merchant
     *
     * @param user
     * @throws ClassNotFoundException
     */
    protected static void addUser(User user) throws ClassNotFoundException {
        assert (user.isCivilian() || user.isCompany() || user.isMerchant());
        // Check that we have all we need
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {
                String table, insQuery;
                if (user.isCivilian()) {
                    Civilian u = user.getCivilian();
                    table = "civilian";
                    insQuery = Queries.insertCivilian(table, u.getName(), u.getAccountNumber(),
                            u.getValidThru(), u.getId());

                } else if (user.isCompany()) {
                    Company u = user.getCompany();
                    table = "company";
                    insQuery = Queries.insertCompany(table, u.getName(), u.getAccountNumber(),
                            u.getValidThru(), u.getId());

                } else {
                    Merchant u = user.getMerchant();
                    table = "merchant";
                    insQuery = Queries.insertMerchant(table, u.getName(), u.getAccountNumber(), u.getId());
                }
                stmt.executeUpdate(insQuery);
                System.out.println("#DB: The user was successfully added in [ " + table + " ] of the database.");

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
     * Establish a database connection and add the member in the database.
     * member can be company,civilian or merchant
     *
     * @param user
     * @throws ClassNotFoundException
     */
    protected static void addUser(User user, String companyId) throws ClassNotFoundException {
        assert ((user.isCivilian() || user.isMerchant()) && user.isValidEmployee());
        // Check that we have all we need
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                String table, insQuery;
                if (user.isCivilian()) {
                    Civilian u = user.getCivilian();
                    table = "employee_civilian";
                    insQuery = Queries.insertECivilian(table, u.getName(),
                            u.getId(), companyId);
                } else {
                    Merchant u = user.getMerchant();
                    table = "employee_merchant";
                    insQuery = Queries.insertEMerchant(table, u.getName(),
                            u.getId(), companyId);
                }
                stmt.executeUpdate(insQuery);
                System.out.println("#DB: The user was successfully added in [ " + table + " ] of the database.");
                // Close connection
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static boolean executeUpdate(String updateCivilianQuery, String updateMerchantQuery)
            throws ClassNotFoundException {
        boolean state = false;
        int succeed;
//        Savepoint startOfTransaction = null;
        Connection con = null;
        Statement stmt = null;
        try {
            con = dbAPI.getConnection();
            stmt = con.createStatement();
            //Stage 1 disable auto commit
            con.setAutoCommit(false);
//            startOfTransaction = con.setSavepoint();
            //stage 2 execute the first update
            succeed = stmt.executeUpdate(updateCivilianQuery);
            if (succeed > 0) {
                //stage 3 execute the second update
                succeed = stmt.executeUpdate(updateMerchantQuery);
                if (succeed <= 0) {
                    //Stage 4 Roll back if second update fail
                    con.rollback();
                } else {
                    //stage 4 commit after 2 succeed updates
                    con.commit();
                    state = true;
                }
            }
        } catch (SQLException ex) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }//end try
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            // Close connection
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return state;
    }

    private static User exportUser(ResultSet res, String type)
            throws SQLException, ParseException {
        User u = null;
        switch (type) {
            case "civilian":
                u = exportCivilian(res);
                break;
            case "merchant":
                u = exportMerchant(res);
                break;
            case "company":
                u = exportCompany(res);
                break;
            case "employee_civilian":
                u = exportECivilian(res);
                break;
            case "employee_merchant":
                u = exportEMerchant(res);
                break;
            default:
                assert (false);
        }
        return u;
    }

    private static User exportCivilian(ResultSet res) throws SQLException, ParseException {
        String name = res.getString("Name");
        double debt = Double.parseDouble(res.getString("Debt"));
        double creditBalance = Double.parseDouble(res.getString("Credit_balance"));
        double creditLimit = Double.parseDouble(res.getString("Credit_limit"));
        int accountNumber = Integer.parseInt(res.getString("Account_number"));
        Date validThru = convertValidThru(res.getString("Valid_thru"));
        String id = res.getString("ID");

        return new User(new Civilian(name, debt, creditBalance, creditLimit,
                accountNumber, validThru, id));
    }

    private static User exportMerchant(ResultSet res) throws SQLException {
        String name = res.getString("Name");
        double totalProfit = Double.parseDouble(res.getString("Total_profit"));
        double debt = Double.parseDouble(res.getString("Debt"));
        double commission = Double.parseDouble(res.getString("Commission"));
        int accountNumber = Integer.parseInt(res.getString("Account_number"));
        String id = res.getString("ID");

        return new User(new Merchant(id, name, accountNumber, commission, totalProfit, debt));
    }

    private static User exportCompany(ResultSet res) throws SQLException, ParseException {
        String name = res.getString("Name");
        double debt = Double.parseDouble(res.getString("Debt"));
        double creditBalance = Double.parseDouble(res.getString("Credit_balance"));
        double creditLimit = Double.parseDouble(res.getString("Credit_limit"));
        int accountNumber = Integer.parseInt(res.getString("Account_number"));
        Date validThru = convertValidThru(res.getString("Valid_thru"));
        String id = res.getString("ID");

        return new User(new Company(name, debt, creditBalance, creditLimit,
                accountNumber, validThru, id));
    }

    private static User exportECivilian(ResultSet res) throws SQLException, ParseException {
        assert (res.getString(3).equals(res.getString(10)));
        //Civilian export
        String Name = res.getString(1);
        String ID = res.getString(2);
        Civilian employee = new Civilian(ID, Name, "employee_civilian");

        //Company export
        Name = res.getString(4);
        ID = res.getString(10);
        double debt = Double.parseDouble(res.getString("Debt"));
        double creditBalance = Double.parseDouble(res.getString("Credit_balance"));
        double creditLimit = Double.parseDouble(res.getString("Credit_limit"));
        int accountNumber = Integer.parseInt(res.getString("Account_number"));
        Date validThru = convertValidThru(res.getString("Valid_thru"));
        Company company = new Company(Name, debt, creditBalance, creditLimit, accountNumber, validThru, ID);

        return new User(employee, company);
    }

    private static User exportEMerchant(ResultSet res) throws SQLException, ParseException {
        assert (res.getString(3).equals(res.getString(13)));
        //merchant export
        String Name = res.getString(1);
        String ID = res.getString(2);
        double totalProfit = Double.parseDouble(res.getString("Total_profit"));
        double commission = Double.parseDouble(res.getString("Commission"));
        double debt = Double.parseDouble(res.getString(6));
        Merchant employee = new Merchant(ID, Name, totalProfit, commission, debt);

        //Company export
        Name = res.getString(7);
        ID = res.getString(13);
        debt = Double.parseDouble(res.getString(8));
        double creditBalance = Double.parseDouble(res.getString("Credit_balance"));
        double creditLimit = Double.parseDouble(res.getString("Credit_limit"));
        int accountNumber = Integer.parseInt(res.getString("Account_number"));
        Date validThru = convertValidThru(res.getString("Valid_thru"));
        Company company = new Company(Name, debt, creditBalance, creditLimit, accountNumber, validThru, ID);

        return new User(employee, company);
    }

    private static Date convertValidThru(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = formatter.parse(date);
        return new java.sql.Date(d.getTime());
    }

    /**
     * Delete information for specific user
     *
     * @param email
     * @throws ClassNotFoundException
     */
    protected static boolean deleteUser(String email, String type) throws ClassNotFoundException {
        boolean exist = false;
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {
                String insQuery = Queries.deleteUser(email, type);

                int state = stmt.executeUpdate(insQuery);

                if (state > 0) {
                    exist = true;
                    System.out.println("#DB: The user was successfully deleted from the database.");
                } else {
                    System.out.println("#DB: Something goes wrong while deleting a user: '" + email + "'. User don't exist.");
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

    protected static ArrayList<ArrayList<String>> getSearchResults(String insQuery) throws ClassNotFoundException {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {

                stmt.execute(insQuery);
                ResultSet res = stmt.getResultSet();
                int i = 1;
                ArrayList<String> tmp = new ArrayList<>();
                try {
                    while (res.getString(i) != null) {
                        tmp.add(res.getString(i));
                        i++;
                    }
                } catch (SQLException e) {
                }

                while (res.next() == true) {
                    tmp = new ArrayList<>();
                    i = 1;
                    try {
                        while (res.getString(i) != null) {
                            tmp.add(res.getString(i));
                            i++;
                        }
                    } catch (SQLException ex) {
                    }
                    data.add(tmp);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

}
