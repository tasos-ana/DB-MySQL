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
        System.out.println(insQuery.toString());
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

    public static String getAllMerchants() {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append(" SELECT ID FROM merchant")
                .append(" UNION")
                .append(" SELECT ID FROM employee_merchant");

        return insQuery.toString();
    }

    public static String updateCivilian_credit(String civilianID,
            String table, double value, Date currDate) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append(" UPDATE ").append(table)
                .append(" SET")
                .append(" Credit_balance = Credit_balance + ").append(value)
                .append(" , Debt = Debt - ").append(value)
                .append(" WHERE")
                .append(" ID = '").append(civilianID).append("'")
                .append(" AND Debt > ").append(value)
                .append(" AND Valid_thru > '").append(currDate).append("'");

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
                .append(" AND Total_profil > ").append(value);

        return insQuery.toString();
    }

    public static String updateCivilian_charge(String civilianID,
            String table, double value, Date currDate) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append(" UPDATE ").append(table)
                .append(" SET")
                .append(" Credit_balance = Credit_balance - ").append(value)
                .append(" , Debt = Debt + ").append(value)
                .append(" WHERE ")
                .append(" ID = '").append(civilianID).append("'")
                .append(" AND Credit_balance > ").append(value)
                .append(" AND Valid_thru > '").append(currDate).append("'");

        return insQuery.toString();
    }

    public static String updateMerchant_charge(String merchantID,
            String table, double value) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append(" UPDATE ").append(table)
                .append(" SET")
                .append(" Total_profit = Total_profit + ").append(value)
                .append(" , Debt = Debt + ").append(value).append("*Commission/100")
                .append(" WHERE ")
                .append(" ID = '").append(merchantID).append("'");

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

}