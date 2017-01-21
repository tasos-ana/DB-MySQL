/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs360db.model;

import util.AccountNumberGenerator;

/**
 *
 * @author Tasos198
 */
public class Merchant {

    private String id;
    private String name;
    private int accountNumber;
    private double commission;
    private double totalProfit;
    private double debt;

    public Merchant(String id, String name) {
        this.id = id;
        this.name = name;
        initAccountNumber();
    }

    public Merchant(String id, String name, String type) {
        this.id = id;
        this.name = name;
        if (type.contains("employee")) {
            this.accountNumber = -1;
            this.commission = 0;
            this.totalProfit = 0;
            this.debt = -1;
        } else {
            initAccountNumber();
        }
    }

    public Merchant(String id, String name, double totalProfit, double commission, double debt) {
        this.id = id;
        this.name = name;
        this.commission = commission;
        this.totalProfit = totalProfit;
        this.debt = debt;
    }

    public Merchant(String id, String name, int accountNumber, double commission, double totalProfit, double debt) {
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
        this.commission = commission;
        this.totalProfit = totalProfit;
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

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public boolean isValidEmployee() {
        return (this.accountNumber == -1 && this.debt == -1);
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
        sb.append("commission: ").append(this.commission).append("\n");
        sb.append("totalProfit: ").append(this.totalProfit).append("\n");
        sb.append("debt: ").append(this.debt).append("\n");
        return sb.toString();
    }

    private void initAccountNumber() {
        this.accountNumber = Integer.parseInt(new AccountNumberGenerator().generate("1908", 10));
    }
}
