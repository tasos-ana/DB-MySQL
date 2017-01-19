/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs360db.model;

import java.io.Serializable;
import java.text.ParseException;

/**
 *
 * @author Tasos
 */
public class User implements Serializable {

    private enum accountTypes {
        CIVILIAN,
        E_CIVILIAN,
        MERCHANT,
        E_MERCHANT,
        COMPANY,
        UNKNOWN
    };

    private String email; // (unique)
    private String name;
    private CreditCard card;
    private accountTypes type;

    public User(String type) throws ParseException {
        this.email = "";
        this.name = "";
        initCard(type, false);
        this.type = convertString2Type(type);
    }

    public User(String email, String type) throws ParseException {
        this.email = email;
        this.name = "";
        initCard(type, true);
        this.type = convertString2Type(type);
    }

    public User(String email, String name, String type) throws ParseException {
        this.email = email;
        this.name = name;
        initCard(type, true);
        this.type = convertString2Type(type);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return convertType2String(this.type);
    }

    public void setType(String type) {
        this.type = convertString2Type(type);
    }

    public CreditCard getCard() {
        return card;
    }

    public accountTypes convertString2Type(String type) {
        switch (type) {
            case "civilian":
                return accountTypes.CIVILIAN;
            case "employee_civilian":
                return accountTypes.E_CIVILIAN;
            case "merchant":
                return accountTypes.MERCHANT;
            case "employee_merchant":
                return accountTypes.E_MERCHANT;
            case "company":
                return accountTypes.COMPANY;
            default:
                assert (false);
        }
        return null;
    }

    public String convertType2String(accountTypes type) {
        if (type == accountTypes.CIVILIAN) {
            return "civilian";
        }

        if (type == accountTypes.E_CIVILIAN) {
            return "employee_civilian";
        }

        if (type == accountTypes.MERCHANT) {
            return "merchant";
        }

        if (type == accountTypes.E_MERCHANT) {
            return "employee_merchant";
        }

        if (type == accountTypes.COMPANY) {
            return "company";
        }

        return null;
    }
    
    private void initCard(String type, boolean create) throws ParseException{
        switch (type) {
            case "merchant":
            case "employee_merchant":
                this.card = new MerchantCreditCard(create);
                break;
            case "civilian":
            case "employee_civilian":
            case "company":
                this.card = new CreditCard(create);
                break;
            default:
                assert (false);
        }
    }

    /**
     * Method that checks that all mandatory fields are set
     *
     * @throws Exception
     */
    public void checkFields() throws Exception {
        // Check that everything is ok
        if ((email == null || email.trim().isEmpty())) {
            throw new Exception("Missing fields! (email)");  // Something went wrong with the fields
        }
        if ((name == null || name.trim().isEmpty())) {
            throw new Exception("Missing fields! (name)");  // Something went wrong with the fields
        }
        if (card == null) {
            throw new Exception("Missing fields! (card)");  // Something went wrong with the fields
        }
        if (type == accountTypes.UNKNOWN) {
            throw new Exception("Missing fields! (type)");  // Something went wrong with the fields
        }
    }
    
    /**
     * Returns a string representation of this object
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Email: ").append(this.email).append("\n");
        sb.append("Name: ").append(this.name).append("\n");
        sb.append("Card details: ").append(this.card.toString()).append("\n");
        sb.append("Type: ").append(this.type).append("\n");
        return sb.toString();
    }
}
