package com.factory.model;

import java.sql.ResultSet;
import java.sql.Date;

public class Stock {
    private Integer id;
    private Integer chocolate_id;
    private Integer amount;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChocolate_id() {
        return chocolate_id;
    }

    public void setChocolate_id(Integer chocolate_id) {
        this.chocolate_id = chocolate_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Stock(ResultSet rs) {
        try{
            this.id = rs.getInt("id");
            this.chocolate_id = rs.getInt("chocolate_id");
            this.amount = rs.getInt("amount");
            this.status = rs.getString("status");
        } catch (Exception err){
            err.printStackTrace();
            this.id = 1;
            this.chocolate_id = 0;
            this.amount = 0;
            this.status = "";
        }
    }
}
