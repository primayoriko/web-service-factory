package com.factory.model;

import javafx.util.Pair;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Recipe {
    private Integer chocolateId;
    private Integer ingredientId;

    public Recipe(ResultSet rs){
        try{
            this.chocolateId = rs.getInt("chocolateId");
            this.ingredientId = rs.getInt("ingredientId");
        } catch (Exception err){
            err.printStackTrace();
            this.chocolateId = null;
            this.ingredientId = null;
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
}
