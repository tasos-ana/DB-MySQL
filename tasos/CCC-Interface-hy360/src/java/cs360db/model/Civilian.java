package cs360db.model;

import java.io.Serializable;
import java.text.ParseException;

/**
 *
 * @author papadako
 */
public class Civilian implements Serializable {

    private String email; // (unique)
    private String name;
    private CreditCard card;

    /**
     * Default Constructor
     *
     * @throws java.text.ParseException
     */
    public Civilian() throws ParseException {
        this.email = "";
        this.name = "";
        this.card = new CreditCard(false);
    }

    /**
     * Constructor
     *
     * @param email
     * @throws java.text.ParseException
     */
    public Civilian(String email) throws ParseException {
        this.email = email;
        this.name = "";
        this.card = new CreditCard(true);
    }

    /**
     * Constructor
     *
     * @param email
     * @param name
     * @throws java.text.ParseException
     */
    public Civilian(String email, String name) throws ParseException {
        this.email = email;
        this.name = name;
        this.card = new CreditCard(true);
    }

    /**
     * Method that checks that all mandatory fields are set
     *
     * @throws Exception
     */
    public void checkFields() throws Exception {
        // Check that everything is ok
        if ((email == null || email.trim().isEmpty())) {
            throw new Exception("Missing fields!");  // Something went wrong with the fields
        }
        if ((name == null || name.trim().isEmpty())) {
            throw new Exception("Missing fields!");  // Something went wrong with the fields
        }
        if (card == null){
            throw new Exception("Missing fields!");  // Something went wrong with the fields
        }
    }

    /**
     * Get the email
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public CreditCard getCard() {
        return card;
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
        
        return sb.toString();
    }

}
