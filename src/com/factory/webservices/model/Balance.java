package com.factory.webservices.model;

import javax.print.attribute.IntegerSyntax;
import java.sql.ResultSet;
import java.sql.Date;

public class Balance {
    private Integer amount;

    public Balance(){
        this.amount = 0;
    }

    public Balance(Integer amount){
        this.amount = amount;
    }

    public Integer getAmount(){
        return this.amount;
    }

    public boolean isValidTransaction(Integer amount){
        return this.amount + amount >= 0;
    }

    public Integer doTransaction(Integer amount){
        this.amount += amount;
        return this.amount;
    }

    public void setAmount(Integer amount){
        this.amount = amount;
    }
}
