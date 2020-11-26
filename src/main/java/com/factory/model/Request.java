package com.factory.model;

import java.sql.ResultSet;
import java.sql.Date;

public class Request {
    private Integer id;
    private Integer chocolate_id;
    private Integer amount;

    public Request(ResultSet rs){
        try{
            this.id = rs.getInt("id");
            this.chocolate_id = rs.getInt("chocolate_id");
            this.amount = rs.getInt("amount");
        } catch (Exception err){
            err.printStackTrace();
            this.id = null;
            this.chocolate_id = null;
            this.amount = null;
        }
    }

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
}
