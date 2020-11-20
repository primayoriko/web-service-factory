package com.factory.webservices.model;

import java.sql.ResultSet;
import java.sql.Date;

public class Ingredient {
    private String name;
    private Integer count;
    private Date expire;

    public Ingredient(){
        this.name = "";
        this.count = 0;
        this.expire = new Date(0);
    }

    public Ingredient(String name, Integer count, Date expire){
        this.name = name;
        this.count = count;
        this.expire = expire;
    }

    public Ingredient copy(Ingredient target){
        this.count = target.count;
        this.expire = target.expire;
        this.name = target.name;
        return this;
    }

    public String getName(){
        return this.name;
    }

    public Integer getCount(){
        return this.count;
    }

    public Date getExpire(){
        return this.expire;
    }
}
