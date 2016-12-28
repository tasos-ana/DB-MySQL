package cs359db.model;

import java.io.Serializable;

/**
 *
 * @author papadako
 */
public class Customer implements Serializable {

    private String email; // (unique)

    /**
     * Default Constructor
     *
     */
    public Customer() {
        this.email = "";
    }

    /**
     * Constructor
     *
     * @param email
     */
    public Customer(String email) {
        this.email = email;
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
     * Returns a string representation of this object
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("email: ").append(email).append("\n");

        return sb.toString();

    }

}
