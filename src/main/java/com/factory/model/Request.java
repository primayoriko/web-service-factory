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
    private String chocolateName;
    @XmlElement( required = true )
    private Integer amount;
    @XmlElement( required = true )
    private Status status;
    @XmlElement( required = true )
    private Date dateRequested;

    public Request(ResultSet rs){
        try{
            this.id = rs.getInt("id");
            this.chocolateName = rs.getString("name");
            this.chocolateId = rs.getInt("chocolate_id");
            this.amount = rs.getInt("amount");
            this.status = Request.translateToStatus(rs.getString("status"));
            this.dateRequested = rs.getDate("dateRequested");
        } catch (Exception err){
            err.printStackTrace();
            this.id = null;
            this.chocolateName = null;
            this.chocolateId = null;
            this.amount = null;
            this.status = null;
            this.dateRequested = null;
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

     public String getChocolateName() {
         return chocolateName;
     }

     public void setChocolateName(String chocolateName) {
         this.chocolateName = chocolateName;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }
}
