package com.factory.model;

import java.sql.ResultSet;
import java.sql.Date;

public class Request {
    private Integer id;
    private Integer chocolateId;
    private Integer amount;

    public Request(ResultSet rs){
        try{
            this.id = rs.getInt("id");
            this.chocolateId = rs.getInt("chocolateId");
            this.amount = rs.getInt("amount");
        } catch (Exception err){
            err.printStackTrace();
            this.id = null;
            this.chocolateId = null;
            this.amount = null;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChocolateId() {
        return chocolateId;
    }

    public void setChocolateId(Integer chocolateId) {
        this.chocolateId = chocolateId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
