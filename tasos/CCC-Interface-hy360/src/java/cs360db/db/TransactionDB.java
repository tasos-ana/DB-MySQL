package cs360db.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDB {

    public static void recordTransaction(String merchantID, String civilianID, int transID,
            double value, String transType, Date currDate, String table) throws ClassNotFoundException {
        String merchantCompany = null, civilianCompany = null;
        boolean emerchant, ecivilian;
        String insQuery;
        emerchant = table.contains("emerchant");
        ecivilian = table.contains("ecivilian");

        if (ecivilian && emerchant) {
            merchantCompany = UserDB.getCompany(merchantID, "employee_merchant");
            civilianCompany = UserDB.getCompany(civilianID, "employee_civilian");
            insQuery = Queries.insert_emerchant_transaction_ecivilian(
                    merchantID, civilianID, transID, value, transType,
                    table, currDate, merchantCompany, civilianCompany);

        } else if (ecivilian) {
            civilianCompany = UserDB.getCompany(civilianID, "employee_civilian");
            insQuery = Queries.insert_merchant_transaction_ecivilian(merchantID,
                    civilianID, transID, value, transType, table, currDate, civilianCompany);

        } else if (emerchant) {
            merchantCompany = UserDB.getCompany(merchantID, "employee_merchant");
            insQuery = Queries.insert_emerchant_transaction_civilian(
                    merchantID, civilianID, transID, value, transType,
                    table, currDate, merchantCompany);

        } else {
            insQuery = Queries.insert_merchant_transaction_civilian(
                    merchantID, civilianID, transID, value, transType, table, currDate);
        }

        try {
            try (Connection con = dbAPI.getConnection();
                    Statement stmt = con.createStatement()) {
                stmt.executeUpdate(insQuery);
                // Close connection
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
