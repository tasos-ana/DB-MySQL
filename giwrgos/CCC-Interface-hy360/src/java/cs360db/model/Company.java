/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs360db.model;

import java.sql.Date;

/**
 *
 * @author Tasos198
 */
public class Company extends Civilian {

    public Company(String id, String name) {
        super(id, name);
    }

    public Company(String name, double debt, double creditBalance, double creditLimit, int accountNumber, Date validThru, String id) {
        super(name, debt, creditBalance, creditLimit, accountNumber, validThru, id);
    }

}
