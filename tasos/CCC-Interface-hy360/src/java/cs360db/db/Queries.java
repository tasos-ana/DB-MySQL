package cs360db.db;

import java.sql.Date;

/**
 *
 * @author Tasos198
 */
public class Queries {

    public static String exists(String id) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append(exists(id, "civilian"))
                .append(" UNION ")
                .append(exists(id, "merchant"))
                .append(" UNION ")
                .append(exists(id, "company"))
                .append(" UNION ")
                .append(exists(id, "employee_civilian"))
                .append(" UNION ")
                .append(exists(id, "employee_merchant"));

        return insQuery.toString();
    }

    public static String exists(String id, String table) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append("SELECT ID FROM ").append(table)
                .append(" WHERE ")
                .append(" ID = ").append("'").append(id).append("'");

        return insQuery.toString();
    }

    public static String existEmployee(String id, String companyID, String table) {
        assert (table.contains("employee"));
        StringBuilder insQuery = new StringBuilder();
        insQuery.append(" SELECT c.id FROM company c, ").append(table).append(" e")
                .append(" WHERE e.Company_ID = c.ID and e.ID = '").append(id).append("'")
                .append(" and e.Company_ID = '").append(companyID).append("'");

        return insQuery.toString();
    }

    public static String insertCivilian(String table, String name, int accountNumber,
            Date validThru, String id) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append("INSERT INTO ").append(table)
                .append(" (NAME, ACCOUNT_NUMBER, VALID_THRU, ID)")
                .append(" VALUES (")
                .append("'").append(name).append("',")
                .append("'").append(accountNumber).append("',")
                .append("'").append(validThru).append("',")
                .append("'").append(id).append("')");

        return insQuery.toString();
    }

    public static String insertECivilian(String table, String name, String id,
            String companyId) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append("INSERT INTO ").append(table)
                .append(" (NAME, ID, COMPANY_ID)")
                .append(" VALUES (")
                .append("'").append(name).append("',")
                .append("'").append(id).append("',")
                .append("'").append(companyId).append("')");

        return insQuery.toString();
    }

    public static String insertCompany(String table, String name, int accountNumber, Date validThru, String id) {
        return insertCivilian(table, name, accountNumber, validThru, id);//insert sto company 
    }

    public static String insertMerchant(String table, String name, int accountNumber, String id) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append("INSERT INTO ").append(table)
                .append(" (NAME, ACCOUNT_NUMBER, ID)")
                .append(" VALUES (")
                .append("'").append(name).append("',")
                .append("'").append(accountNumber).append("',")
                .append("'").append(id).append("')");

        return insQuery.toString();
    }

    public static String insertEMerchant(String table, String name, String id,
            String companyId) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append("INSERT INTO ").append(table)
                .append(" (NAME, ID, COMPANY_ID)")
                .append(" VALUES (")
                .append("'").append(name).append("',")
                .append("'").append(id).append("',")
                .append("'").append(companyId).append("')");

        return insQuery.toString();
    }

    public static String getUser(String id, String table) {
        StringBuilder insQuery = new StringBuilder();

        if (table.contains("employee")) {
            insQuery.append("SELECT * FROM ").append(table).append(" e ,company c")
                    .append(" WHERE e.id = '").append(id).append("' && c.ID = e.Company_ID");
        } else {
            insQuery.append("SELECT * FROM ").append(table)
                    .append(" WHERE ID = '").append(id).append("'");
        }

        return insQuery.toString();
    }

    public static String getCompany(String id, String table) {
        assert (table.contains("employee"));
        StringBuilder insQuery = new StringBuilder();

        insQuery.append("SELECT company_id FROM ").append(table)
                .append(" WHERE ID = '").append(id).append("'");

        return insQuery.toString();
    }

    public static String getAllMerchants2Refund(String civilianID, String table) {
        StringBuilder insQuery = new StringBuilder();
        String t1, t2, civilian;
        if (table.contains("employee")) {
            t1 = "emerchant_transaction_ecivilian";
            t2 = "merchant_transaction_ecivilian";
            civilian = "employee_Civilian_id";
        } else {
            t1 = "emerchant_transaction_civilian";
            t2 = "merchant_transaction_civilian";
            civilian = "Civilian_id";
        }
        insQuery.append(" SELECT employee_merchant_id AS ID FROM ").append(t1)
                .append(" WHERE ").append(civilian).append(" = '").append(civilianID).append("'")
                .append(" UNION")
                .append(" SELECT merchant_id AS ID FROM ").append(t2)
                .append(" WHERE ").append(civilian).append(" = '").append(civilianID).append("'");

        return insQuery.toString();
    }

    public static String getGoodCustomers() {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append(" SELECT ID FROM civilian WHERE Debt = 0")
                .append(" UNION")
                .append(" SELECT ID FROM merchant WHERE Debt = 0")
                .append(" UNION")
                .append(" SELECT ID FROM company WHERE Debt = 0");

        return insQuery.toString();
    }

    public static String getBadCustomers() {//fix
        StringBuilder insQuery = new StringBuilder();
        insQuery.append(" SELECT ID,Debt FROM civilian WHERE Debt <> 0")
                .append(" UNION")
                .append(" SELECT ID,Debt FROM merchant WHERE Debt <> 0")
                .append(" UNION")
                .append(" SELECT ID,Debt FROM company WHERE Debt <> 0")
                .append(" ORDER BY debt");
        return insQuery.toString();
    }

    public static String getMonthMerchants() {//fix
        StringBuilder insQuery = new StringBuilder();
        insQuery.append(" SELECT ID FROM civilian WHERE Debt = 0")
                .append(" UNION")
                .append(" SELECT ID FROM merchant WHERE Debt = 0")
                .append(" UNION")
                .append(" SELECT ID FROM company WHERE Debt = 0");
        return insQuery.toString();
    }

    public static String updateCivilian_credit(String civilianID,
            String table, double value, Date currDate) throws ClassNotFoundException {
        StringBuilder insQuery = new StringBuilder();
        if (table.contains("employee")) {
            civilianID = UserDB.getCompany(civilianID, table);
            table = "company";
        }
        insQuery.append(" UPDATE ").append(table)
                .append(" SET")
                .append(" Credit_balance = Credit_balance + ").append(value)
                .append(" , Debt = Debt - ").append(value)
                .append(" WHERE")
                .append(" ID = '").append(civilianID).append("'")
                .append(" AND Debt >= ").append(value)
                .append(" AND Valid_thru >= '").append(currDate).append("'");

        return insQuery.toString();
    }

    public static String updateMerchant_credit(String merchantID,
            String table, double value) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append(" UPDATE ").append(table)
                .append(" SET")
                .append(" Total_profit = Total_profit - ").append(value)
                .append(" + ").append(value).append("*Commission/100")
                .append(" WHERE ")
                .append(" ID = '").append(merchantID).append("'")
                .append(" AND Total_profit >= ").append(value);

        return insQuery.toString();
    }

    public static String updateCivilian_charge(String civilianID,
            String table, double value, Date currDate) throws ClassNotFoundException {
        StringBuilder insQuery = new StringBuilder();
        if (table.contains("employee")) {
            civilianID = UserDB.getCompany(civilianID, table);
            table = "company";
        }

        insQuery.append(" UPDATE ").append(table)
                .append(" SET")
                .append(" Credit_balance = Credit_balance - ").append(value)
                .append(" , Debt = Debt + ").append(value)
                .append(" WHERE ")
                .append(" ID = '").append(civilianID).append("'")
                .append(" AND Credit_balance >= ").append(value)
                .append(" AND Valid_thru >= '").append(currDate).append("'");

        return insQuery.toString();
    }

    public static String updateMerchant_charge(String merchantID,
            String table, double value) throws ClassNotFoundException {
        StringBuilder insQuery = new StringBuilder();
        if (table.contains("employee")) {
            String companyID = UserDB.getCompany(merchantID, table);
            insQuery.append(" UPDATE company co, ").append(table).append(" em")
                    .append(" SET")
                    .append(" co.Debt = co.Debt + ").append(value).append("*em.Commission/100")
                    .append(" , em.Debt = em.Debt + ").append(value).append("*em.Commission/100")
                    .append(" , em.Total_profit = em.Total_profit + ").append(value)
                    .append(" WHERE")
                    .append(" co.ID = '").append(companyID).append("'")
                    .append(" AND em.ID = '").append(merchantID).append("'");
        } else {
            insQuery.append(" UPDATE ").append(table)
                    .append(" SET")
                    .append(" Total_profit = Total_profit + ").append(value)
                    .append(" , Debt = Debt + ").append(value).append("*Commission/100")
                    .append(" WHERE ")
                    .append(" ID = '").append(merchantID).append("'");
        }

        return insQuery.toString();
    }

    public static String deleteUser(String id, String table) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append("DELETE FROM ").append(table)
                .append(" WHERE ")
                .append(" ID = ").append("'").append(id).append("'");

        return insQuery.toString();
    }

    public static String insert_emerchant_transaction_ecivilian(String merchantID,
            String civilianID, int transID, double value, String type, String transTable,
            Date currDate, String merchantCompany, String civilianCompany) throws ClassNotFoundException {

        StringBuilder insQuery = new StringBuilder();

        insQuery.append("INSERT INTO ").append(transTable)
                .append(" (EMPLOYEE_MERCHANT_ID, EMPLOYEE_CIVILIAN_ID,")
                .append(" TRANSACTION_ID, VALUE, TYPE, DATE,")
                .append(" CIVILIAN_COMPANY_ID, MERCHANT_COMPANY_ID)")
                .append(" VALUES (")
                .append("'").append(merchantID).append("',")
                .append("'").append(civilianID).append("',")
                .append("'").append(transID).append("',")
                .append("'").append(value).append("',")
                .append("'").append(type).append("',")
                .append("'").append(currDate).append("',")
                .append("'").append(civilianCompany).append("',")
                .append("'").append(merchantCompany).append("')");

        return insQuery.toString();
    }

    public static String insert_merchant_transaction_ecivilian(String merchantID,
            String civilianID, int transID, double value, String type, String transTable,
            Date currDate, String civilianCompany) {

        StringBuilder insQuery = new StringBuilder();

        insQuery.append("INSERT INTO ").append(transTable)
                .append(" (MERCHANT_ID, EMPLOYEE_CIVILIAN_ID,")
                .append(" TRANSACTION_ID, VALUE, TYPE, DATE,")
                .append(" CIVILIAN_COMPANY_ID)")
                .append(" VALUES (")
                .append("'").append(merchantID).append("',")
                .append("'").append(civilianID).append("',")
                .append("'").append(transID).append("',")
                .append("'").append(value).append("',")
                .append("'").append(type).append("',")
                .append("'").append(currDate).append("',")
                .append("'").append(civilianCompany).append("')");

        return insQuery.toString();
    }

    public static String insert_emerchant_transaction_civilian(String merchantID,
            String civilianID, int transID, double value, String type, String transTable,
            Date currDate, String merchantCompany) {

        StringBuilder insQuery = new StringBuilder();

        insQuery.append("INSERT INTO ").append(transTable)
                .append(" (EMPLOYEE_MERCHANT_ID, CIVILIAN_ID,")
                .append(" TRANSACTION_ID, VALUE, TYPE, DATE,")
                .append(" MERCHANT_COMPANY_ID)")
                .append(" VALUES (")
                .append("'").append(merchantID).append("',")
                .append("'").append(civilianID).append("',")
                .append("'").append(transID).append("',")
                .append("'").append(value).append("',")
                .append("'").append(type).append("',")
                .append("'").append(currDate).append("',")
                .append("'").append(merchantCompany).append("')");

        return insQuery.toString();
    }

    public static String insert_merchant_transaction_civilian(String merchantID,
            String civilianID, int transID, double value, String type, String transTable,
            Date currDate) {

        StringBuilder insQuery = new StringBuilder();

        insQuery.append("INSERT INTO ").append(transTable)
                .append(" (MERCHANT_ID, CIVILIAN_ID,")
                .append(" TRANSACTION_ID, VALUE, TYPE, DATE)")
                .append(" VALUES (")
                .append("'").append(merchantID).append("',")
                .append("'").append(civilianID).append("',")
                .append("'").append(transID).append("',")
                .append("'").append(value).append("',")
                .append("'").append(type).append("',")
                .append("'").append(currDate).append("')");

        return insQuery.toString();
    }

    public static String payDebtEmployeeMerchant(String merchantID, String table, double value) throws ClassNotFoundException {
        StringBuilder insQuery = new StringBuilder();
        String companyID = UserDB.getCompany(merchantID, table);
        insQuery.append(" UPDATE company co, ").append(table).append(" em")
                .append(" SET")
                .append(" co.Debt = co.Debt - ").append(value)
                .append(" , co.Credit_balance = co.Credit_balance + ").append(value)
                .append(" , em.Debt = em.Debt - ").append(value)
                .append(" , em.Total_profit = em.Total_profit - ").append(value)
                .append(" WHERE")
                .append(" co.ID = '").append(companyID).append("'")
                .append(" AND em.ID = '").append(merchantID).append("'")
                .append(" AND co.Debt > ").append(value)
                .append(" AND em.Debt > ").append(value)
                .append(" AND em.Total_profit > ").append(value);

        return insQuery.toString();
    }

    public static String payDebtMerchant(String merchantID, String table, double value) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append(" UPDATE ").append(table)
                .append(" SET")
                .append(" Total_profit = Total_profit - ").append(value)
                .append(" , Debt = Debt - ").append(value)
                .append(" WHERE ")
                .append(" ID = '").append(merchantID).append("'")
                .append(" AND Debt > ").append(value)
                .append(" AND Total_profit > ").append(value);

        return insQuery.toString();
    }

    public static String payDebtCivilian(String civilianID, String table, double value) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append(" UPDATE ").append(table)
                .append(" SET")
                .append(" Credit_balance = Credit_balance + ").append(value)
                .append(" , Debt = Debt - ").append(value)
                .append(" WHERE ")
                .append(" ID = '").append(civilianID).append("'")
                .append(" AND Credit_limit >= Credit_balance + ").append(value)
                .append(" AND Debt >= ").append(value);

        return insQuery.toString();
    }

    public static String getAllCompanyEmployee(String companyID) {
        StringBuilder insQuery = new StringBuilder();
        insQuery.append(" SELECT id FROM employee_civilian")
                .append(" WHERE company_id = '").append(companyID).append("'")
                .append(" UNION")
                .append(" SELECT id FROM employee_merchant")
                .append(" WHERE company_id = '").append(companyID).append("'");

        return insQuery.toString();
    }

    public static String getAllCoopedCivilian(String email, String type) {
        StringBuilder insQuery = new StringBuilder();

        if (type.contains("employee")) {
            insQuery.append(" SELECT employee_civilian_id as ID from emerchant_transaction_ecivilian")
                    .append(" WHERE employee_merchant_id = '").append(email).append("'")
                    .append(" UNION")
                    .append(" SELECT civilian_id from emerchant_transaction_civilian")
                    .append(" WHERE employee_merchant_id = '").append(email).append("'");
        } else {
            insQuery.append(" SELECT employee_civilian_id as ID from merchant_transaction_ecivilian")
                    .append(" WHERE merchant_id = '").append(email).append("'")
                    .append(" UNION")
                    .append(" SELECT civilian_id from merchant_transaction_civilian")
                    .append(" WHERE merchant_id = '").append(email).append("'");
        }

        return insQuery.toString();
    }

    public static String getAllCustomers(String table) {
        StringBuilder insQuery = new StringBuilder();
        if (table.equals("company")) {
            insQuery.append(" SELECT id FROM ").append(table);
        } else {
            insQuery.append(" SELECT id FROM ").append(table)
                    .append(" UNION ")
                    .append(" SELECT id FROM ").append("employee_").append(table);
        }

        return insQuery.toString();
    }

}
