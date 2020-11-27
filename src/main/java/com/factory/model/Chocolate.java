package com.factory.model;

import javafx.util.Pair;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Chocolate {
    @XmlElement(required = true)
    private Integer id;
    @XmlElement( required = true )
    private String name;
    @XmlElement( required = true )
    private Integer amount;

    public Chocolate(ResultSet rs){
        try{
            this.id = rs.getInt("id");
            this.name = rs.getString("name");
            this.amount = rs.getInt("amount");
        } catch (Exception err){
            err.printStackTrace();
            this.id = null;
            this.name = null;
            this.amount = null;
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

    public boolean isValidChange(Integer amount) {
        return this.amount + amount >= 0;
    }

    public void changeAmount(Integer amount) {
        this.amount += amount;
    }
}
