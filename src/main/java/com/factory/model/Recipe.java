package com.factory.model;

import javafx.util.Pair;

import java.util.ArrayList;

public class Recipe {
    private Chocolate chocolate;
    private ArrayList<Pair<Ingredient, Integer>> ingredients;

    public Recipe(){

    }

    public boolean addIngredient(){
        return true;
    }
}
