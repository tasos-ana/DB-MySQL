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
                .append("'").append(companyId).append("',");

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
            String companyId, double totalProfit, double commission) {
        StringBuilder insQuery = new StringBuilder();

        insQuery.append("INSERT INTO ").append(table)
                .append(" (NAME, ID, COMPANY_ID, TOTAL_PROFIT, COMMISSION)")
                .append(" VALUES (")
                .append("'").append(name).append("',")
                .append("'").append(id).append("',")
                .append("'").append(companyId).append("')")
                .append("'").append(totalProfit).append("')")
                .append("'").append(commission).append("')");

        return insQuery.toString();
    }

    public static String getUser(String table, String id) {
        StringBuilder insQuery = new StringBuilder();

        if (table.contains("employee")) {
            insQuery.append("SELECT * FROM ").append(table).append(" e ,company c")
                    .append(" WHERE e.id = '").append(id).append("' && c.ID = e.Company_ID");
        } else {
            insQuery.append("SELECT * FROM ").append(table)
                    .append(" WHERE ID = '").append(id).append("'");
        }

        System.out.println(insQuery.toString());
        return insQuery.toString();
    }
    /*
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
     */
}
