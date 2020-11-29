package com.factory.model;

import javafx.util.Pair;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class IgdrRecipe {
    @XmlElement(required = true)
    private String name;
    @XmlElement( required = true )
    private Integer amount;
    
    public IgdrRecipe(ResultSet rs){
        try{
            this.name = rs.getString("name");
            this.amount = rs.getInt("amount");
        } catch (Exception err){
            err.printStackTrace();
            this.name = null;
            this.amount = null;
        }
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
