package com.factory.model;

import java.sql.ResultSet;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

import java.sql.Date;

import com.factory.model.Status;

@XmlAccessorType(XmlAccessType.FIELD)
public class Stock {
    @XmlElement( required = true )
    private Integer id;
    @XmlElement( required = true )
    private Integer chocolate_id;
    @XmlElement( required = true )
    private Integer amount;
    @XmlElement( required = true )
    private Status status;
    @XmlElement( required = true )
    private Date date_requested;

    public Stock(ResultSet rs) {
        try {
            this.id = rs.getInt("id");
            this.chocolate_id = rs.getInt("chocolate_id");
            this.amount = rs.getInt("amount");
            this.status = Stock.translateToStatus(rs.getString("status"));
            this.date_requested = rs.getDate("date_requested");
        } catch (Exception err) {
            err.printStackTrace();
            this.id = null;
            this.chocolate_id = null;
            this.amount = null;
            this.status = null;
            this.date_requested = null;
        }
    }
    
    public static Status translateToStatus(String status) throws Exception {
        if(status.equals("Rejected")){
            return Status.REJECTED;
        } else if (status.equals("Delivered")){
            return Status.DELIVERED;
        } else if (status.equals("Waiting")){
            return Status.WAITING;
        }

        throw new Exception("Request Status not valid!");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChocolateId() {
        return chocolate_id;
    }

    public void setChocolateId(Integer chocolate_id) {
        this.chocolate_id = chocolate_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDateRequested() {
        return date_requested;
    }

    public void setDateRequested(Date date_requested) {
        this.date_requested = date_requested;
    }
}
