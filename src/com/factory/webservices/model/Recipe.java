package com.factory.webservices.model;

import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.Date;
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
