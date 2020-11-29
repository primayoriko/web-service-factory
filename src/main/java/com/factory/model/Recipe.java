package com.factory.model;

import javafx.util.Pair;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Recipe {
    private Integer chocolateId;
    private Integer itemId;
    private String chocolateName;
    private String itemName;

    public Recipe(ResultSet rs){
        try{
            this.chocolateId = rs.getInt("chocolate_id");
            this.itemId = rs.getInt("item_id");
            this.chocolateName = rs.getString("item_name");
            this.itemName = rs.getString("chocolate_name");
        } catch (Exception err){
            err.printStackTrace();
            this.chocolateId = null;
            this.itemId = null;
            this.chocolateName = null;
            this.itemName = null;
        }
    }

    public Integer getChocolateId() {
        return chocolateId;
    }

    public void setChocolateId(Integer chocolateId) {
        this.chocolateId = chocolateId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getChocolateName() {
        return chocolateName;
    }

    public void setChocolateName(String chocolateName) {
        this.chocolateName = chocolateName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
