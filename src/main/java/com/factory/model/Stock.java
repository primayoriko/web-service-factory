package com.factory.model;

import java.sql.Date;
import java.sql.ResultSet;

public class Stock {
    private String name;
    private Integer count;
    private Date expireDate;

    public Stock(ResultSet rs){
        try{
            this.name = rs.getString("name");
            this.count = rs.getInt("count");
            this.expireDate = rs.getDate("expire_date");
        } catch (Exception err){
            err.printStackTrace();
            this.name = null;
            this.count = null;
            this.expireDate = null;
        }
    }

    public Stock(String name, Integer count, Date expireDate){
        this.name = name;
        this.count = count;
        this.expireDate = expireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
