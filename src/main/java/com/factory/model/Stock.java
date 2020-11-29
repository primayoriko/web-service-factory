package com.factory.model;

import java.sql.Date;
import java.sql.ResultSet;

public class Stock {
    private String name;
    private Integer amount;
    private String expireDate;

    public Stock(ResultSet rs){
        try{
            this.name = rs.getString("name");
            this.amount = rs.getInt("amount");
            this.expireDate = rs.getString("expire_date");
        } catch (Exception err){
            err.printStackTrace();
            this.name = null;
            this.amount = null;
            this.expireDate = null;
        }
    }

    public Stock(String name, Integer amount, String expireDate){
        this.name = name;
        this.amount = amount;
        this.expireDate = expireDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isAmountValid(Integer amount) {
        return this.amount + amount >= 0;
    }

    public void changeAmount(Integer amount) {
        this.amount += amount;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExistInStocks() {
        return this.expireDate == null;
    }
}
