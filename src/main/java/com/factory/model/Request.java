package com.factory.model;

import java.sql.ResultSet;
import java.sql.Date;

public class Request {
    public enum Status {
        WAITING,
        REJECTED,
        DELIVERED
    }

    private Integer id;
    private Integer chocolateName;
    private Integer amount;
    private Status status;

    public Request(ResultSet rs){
        try{
            this.id = rs.getInt("id");
            this.chocolateName = rs.getInt("name");
            this.amount = rs.getInt("amount");
            this.status = this.translateToStatus(rs.getString("status"));
        } catch (Exception err){
            err.printStackTrace();
            this.id = null;
            this.chocolateName = null;
            this.amount = null;
            this.status = null;
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
        if(status.equals(status.REJECTED)){
            return "Rejected";
        } else if (status.equals(status.DELIVERED)){
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
}
