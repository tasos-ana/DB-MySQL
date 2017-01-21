/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs360db.model;

import java.sql.Date;
import util.AccountNumberGenerator;

/**
 *
 * @author Tasos198
 */
public class Civilian {

    protected String id;
    protected String name;

    protected int accountNumber;
    protected Date validThru;

    protected double creditLimit;
    protected double creditBalance;
    protected double debt;

    public Civilian(String id, String name) {
        this.id = id;
        this.name = name;
        initAccountNumber();
        initValidThru();
    }

    public Civilian(String id, String name, String type) {
        this.id = id;
        this.name = name;
        if (type.contains("employee")) {
            this.accountNumber = -1;
            this.validThru = null;
            this.creditLimit = -1;
            this.creditBalance = -1;
        } else {
            initAccountNumber();
            initValidThru();
        }
    }

    public Civilian(String id, String name, double debt) {
        this.id = id;
        this.name = name;
        this.debt = debt;
    }

    public Civilian(String name, double debt, double creditBalance, double creditLimit, int accountNumber, Date validThru, String id) {
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
        this.validThru = validThru;
        this.creditLimit = creditLimit;
        this.creditBalance = creditBalance;
        this.debt = debt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public Date getValidThru() {
        return validThru;
    }

    public void setValidThru(Date validThru) {
        this.validThru = validThru;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(double creditBalance) {
        this.creditBalance = creditBalance;
    }

    public boolean isValidEmployee() {
        return (this.accountNumber == -1 && this.validThru == null
                && this.creditLimit == -1 && this.creditBalance == -1);
    }

    /**
     * Returns a string representation of this object
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(this.id).append("\n");
        sb.append("Name: ").append(this.name).append("\n");
        sb.append("accountNumber: ").append(this.accountNumber).append("\n");
        sb.append("validThru: ").append(this.validThru.toString()).append("\n");
        sb.append("creditLimit: ").append(this.creditLimit).append("\n");
        sb.append("creditBalance: ").append(this.creditBalance).append("\n");
        sb.append("debt: ").append(this.debt).append("\n");
        return sb.toString();
    }

    /*      PRIVATE SECTION         */
    private void initAccountNumber() {
        this.accountNumber = Integer.parseInt(new AccountNumberGenerator().generate("1908", 10));
    }

    private void initValidThru() {
        java.util.Date utilDate = new java.util.Date();
        String oneYear = "31556952000";
        long oneYearMS = Long.parseLong(oneYear);

        this.validThru = new java.sql.Date(utilDate.getTime() + oneYearMS * 8);
    }

}
