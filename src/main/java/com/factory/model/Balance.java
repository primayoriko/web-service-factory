package com.factory.model;

import javax.print.attribute.IntegerSyntax;
import java.sql.ResultSet;
import java.sql.Date;

public class Balance {
    private Integer id;
    private Integer amount;

    public Balance(ResultSet rs){
        try{
            this.id = rs.getInt("id");
            this.amount = rs.getInt("amount");
        } catch (Exception err){
            err.printStackTrace();
            this.id = 1;
            this.amount = 0;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public boolean isValidTransaction(Integer amount){
        return this.amount >= amount;
    }

    public void doTransaction(Integer amount){
        this.amount -= amount;
    }
}
