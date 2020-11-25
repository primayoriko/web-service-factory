package com.factory.model;

import javafx.util.Pair;

import java.util.ArrayList;

public class Recipe {
    private Chocolate chocolate;
    private ArrayList<Pair<Ingredient, Integer>> ingredients;

    public Recipe(){

    }

    public Chocolate getChocolate(){
        return this.chocolate;
    }

    public void setChocolate(Chocolate chocolate){
        this.chocolate = chocolate;
    }

    public void addIngredient(Ingredient ingredient, Integer count){
        this.ingredients.add(
                    new Pair<Ingredient, Integer>(ingredient, count)
                );
    }

    public ArrayList<Pair<Ingredient, Integer>> getIngredient(){
        return this.ingredients;
    }

}
