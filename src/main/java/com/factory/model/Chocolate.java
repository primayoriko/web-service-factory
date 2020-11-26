package com.factory.model;

import javafx.util.Pair;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;

public class Chocolate {
    private Integer id;
    private String name;
    private Integer amount;

    public Chocolate(ResultSet rs){
        try{

        } catch (Exception err){
            err.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
