package com.factory.model;

import javafx.util.Pair;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Recipe {
    private Chocolate chocolate;
    private ArrayList<Pair<Ingredient, Integer>> ingredients;

    public Recipe(ResultSet rs){
        try{

        } catch (Exception err){
            err.printStackTrace();
        }
    }

    public void addIngredient(Ingredient ingredient, Integer count){
        this.ingredients.add(
                    new Pair<Ingredient, Integer>(ingredient, count)
                );
    }

    public Chocolate getChocolate(){
        return this.chocolate;
    }

    public void setChocolate(Chocolate chocolate){
        this.chocolate = chocolate;
    }

    public ArrayList<Pair<Ingredient, Integer>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Pair<Ingredient, Integer>> ingredients) {
        this.ingredients = ingredients;
    }
}
