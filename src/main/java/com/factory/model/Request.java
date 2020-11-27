package com.factory.model;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;

import java.sql.ResultSet;
import java.sql.Date;

import com.factory.model.Status;
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {
    @XmlElement( required = true )
    private Integer id;
    @XmlElement( required = true )
    private Integer chocolateId;
    @XmlElement( required = true )
    private Integer amount;
    @XmlElement( required = true )
    private Status status;
    @XmlElement( required = true )
    private Date date_requested;

    public Request(ResultSet rs){
        try{
            this.id = rs.getInt("id");
            this.chocolateName = rs.getInt("name");
            this.amount = rs.getInt("amount");
            this.status = Request.translateToStatus(rs.getString("status"));
            this.date_requested = rs.getDate("date_requested");
        } catch (Exception err){
            err.printStackTrace();
            this.id = null;
            this.chocolateName = null;
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

    public static String translateFromStatus(Status status){
        if(status.equals(Status.REJECTED)){
            return "Rejected";
        } else if (status.equals(Status.DELIVERED)){
            return "Delivered";
        }
        return "Waiting";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChocolateName() {
        return chocolateName;
    }

    public void setChocolateName(Integer chocolateName) {
        this.chocolateName = chocolateName;
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
