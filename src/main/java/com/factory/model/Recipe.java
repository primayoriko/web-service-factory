package com.factory.model;

import javafx.util.Pair;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Recipe {
    private Integer chocolateId;
    private Integer ingredientId;
    private String chocolateName;
    private String ingredientName;

    public Recipe(ResultSet rs){
        try{
            this.chocolateId = rs.getInt("chocolateId");
            this.ingredientId = rs.getInt("ingredientId");
            this.chocolateName = rs.getString("ingredient_name");
            this.ingredientName = rs.getString("chocolate_name");
        } catch (Exception err){
            err.printStackTrace();
            this.chocolateId = null;
            this.ingredientId = null;
            this.chocolateName = null;
            this.ingredientName = null;
        }
    }

    public Integer getChocolateId() {
        return chocolateId;
    }

    public void setChocolateId(Integer chocolateId) {
        this.chocolateId = chocolateId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getChocolateName() {
        return chocolateName;
    }

    public void setChocolateName(String chocolateName) {
        this.chocolateName = chocolateName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
