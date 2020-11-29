package com.factory.model;

import javafx.util.Pair;

import java.sql.ResultSet;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public class IgdrRecipe {
    @XmlElement(required = true)
    private String name;
    @XmlElement( required = true )
    private Integer amount;
    
    public Ingredients(ResultSet rs){
        try{
            this.ingredientName = rs.getString("name");
            this.ingredientAmount = rs.getInt("amount");
        } catch (Exception err){
            err.printStackTrace();
            this.ingredientName = null;
            this.ingredientAmount = null;
        }
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Integer getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(Integer ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

}
