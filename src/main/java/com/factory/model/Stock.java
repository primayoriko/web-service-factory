package com.factory.model;

import java.sql.Date;
import java.sql.ResultSet;

public class Stock {
    private String name;
    private Integer amount;
    private Date expireDate;

    public Stock(ResultSet rs){
        try{
            this.name = rs.getString("name");
            this.amount = rs.getInt("amount");
            this.expireDate = rs.getDate("expire_date");
        } catch (Exception err){
            err.printStackTrace();
            this.name = null;
            this.amount = null;
            this.expireDate = null;
        }
    }

    public Stock(String name, Integer amount, Date expireDate){
        this.name = name;
        this.amount = amount;
        this.expireDate = expireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
