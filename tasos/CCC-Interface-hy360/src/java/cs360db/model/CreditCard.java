/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs360db.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.AccountNumberGenerator;

/**
 *
 * @author Tasos
 */
public class CreditCard {

    private int accountNumber;
    private int validThruMonth;
    private int validThruYear;
    private double creditLimit;
    private double currentDebt;
    private double availableCreditBalance;

    public CreditCard(boolean createNew) throws ParseException {
        if (createNew) {
            initCreditCard();
        } else {
            this.accountNumber = 0;
            this.validThruMonth = 0;
            this.validThruYear = 0;
            this.creditLimit = 0;
            this.currentDebt = 0;
            this.availableCreditBalance = 0;
        }
    }

    private void initCreditCard() throws ParseException {
        initAccountNumber();
        initValidThru();
        initCreditLimit();
        initCurrentDebt();
        initAvailableCreditBalance();
    }

    private void initAccountNumber() {
        this.accountNumber = Integer.parseInt(new AccountNumberGenerator().generate("1908", 10));
    }

    private void initValidThru() throws ParseException {
        String MM, DD, YY, expiredDate;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("MM");
        MM = df.format(date);

        df = new SimpleDateFormat("DD");
        DD = df.format(date);

        df = new SimpleDateFormat("YYYY");
        YY = df.format(date);
        int newYY, newMM, newDD;
        newYY = Integer.parseInt(YY);
        newMM = Integer.parseInt(MM);
        newDD = Integer.parseInt(DD);
        newYY += 8;

//        StringBuilder sb = new StringBuilder();
//        sb.append(DD).append("/").append(MM).append("/").append(newYY);
//        expiredDate = sb.toString();
//
//        this.validThru = new Date(expiredDate);
        this.validThruMonth = newMM;
        this.validThruYear = newYY;
    }

    private void initCreditLimit() {
        this.creditLimit = 500.0;
    }

    private void initCurrentDebt() {
        this.currentDebt = 0.0;
    }

    private void initAvailableCreditBalance() {
        this.availableCreditBalance = 500.0;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getValidThru() {
        int YY, MM, DD;
        YY = this.validThruYear;
        MM = this.validThruMonth;
        DD = 01;
        return MM + "/" + YY;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getCurrentDebt() {
        return currentDebt;
    }

    public double getAvailableCreditBalance() {
        return availableCreditBalance;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setValidThru(String validThru) {
        String[] expiredDate = validThru.split("/");
        this.validThruYear = Integer.parseInt(expiredDate[1]);
        this.validThruMonth = Integer.parseInt(expiredDate[0]);
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setCurrentDebt(double currentDebt) {
        this.currentDebt = currentDebt;
    }

    public void setAvailableCreditBalance(double availableCreditBalance) {
        this.availableCreditBalance = availableCreditBalance;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Account number: ").append(this.accountNumber).append("\n");
        str.append("Valid Thru: ").append(getValidThru()).append("\n");
        str.append("Credit Limit: ").append(this.creditLimit).append("\n");
        str.append("Current Debt: ").append(this.currentDebt).append("\n");
        str.append("Available Credit Balance: ").append(this.availableCreditBalance).append("\n");

        return str.toString();
    }
}
