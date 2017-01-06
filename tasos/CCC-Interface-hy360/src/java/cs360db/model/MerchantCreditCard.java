/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs360db.model;

import java.text.ParseException;

/**
 *
 * @author Tasos
 */
public class MerchantCreditCard extends CreditCard{
    
    private double totalProfit;
    private float supply;
    
    public MerchantCreditCard(boolean createNew) throws ParseException {
        super(createNew);
        System.out.println("cs360db.model.MerchantCreditCard.<init>()");
        this.totalProfit = 0;
        this.supply = 0;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public float getSupply() {
        return supply;
    }

    public void setSupply(float supply) {
        this.supply = supply;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString());
        str.append("Total Profit: ").append(this.totalProfit).append("\n");
        str.append("Supply: ").append(this.supply).append("\n");
        
        return str.toString();
    }
    
    
}
