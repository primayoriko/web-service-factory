package com.factory.model;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;

import java.sql.ResultSet;

@XmlAccessorType(XmlAccessType.FIELD)
public class Balance {
    @XmlElement( required = true )
    private Integer id;
    @XmlElement( required = true )
    private Integer amount;

    public Balance(ResultSet rs){
        try{
            this.id = rs.getInt("id");
            this.amount = rs.getInt("amount");
        } catch (Exception err){
            err.printStackTrace();
            this.id = 1;
            this.amount = 0;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public boolean isValidChange(Integer amount){
        return this.amount + amount >= 0;
    }

    public void changeAmount(Integer amount){
        this.amount += amount;
    }
}
