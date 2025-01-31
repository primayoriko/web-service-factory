package com.factory.service;

import com.factory.model.Recipe;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import com.factory.model.IgdrRecipe;
import com.factory.service.Service;
import com.factory.service.RecipeService;

@WebService(endpointInterface = "com.factory.service.RecipeService")
@HandlerChain(file = "handlers.xml")
public class RecipeServiceImpl extends Service implements RecipeService {
    @Override
    public Recipe[] getRecipe(Integer chocolateId){
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT r.chocolate_id, c.name as chocolate_name, " +
                    "r.item_id, i.name as item_name FROM chocolates as c " +
                    "RIGHT JOIN recipes as r ON c.id = r.chocolate_id " +
                    "LEFT JOIN items as i ON i.id = r.item_id WHERE chocolate_id = ?");

            ps.setInt(1, chocolateId);

            rs = ps.executeQuery();

            if(!rs.isBeforeFirst()){
                throw generateSoapFaultException(404,
                        "Recipe Not Found", "Server");
            }

            List<Recipe> recipes = new ArrayList<Recipe>();

            while(rs.next()){
                recipes.add(new Recipe(rs));
            }

            Recipe[] response = new Recipe[recipes.size()];

            for(int i = 0; i < recipes.size(); i++){
                response[i] = recipes.get(i);
            }

            return response;
        } catch (Exception err) {
            err.printStackTrace();
            throw generateSoapFaultException(500,
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    @Override
    public Recipe[] getRecipes(){
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT r.chocolate_id, c.name as chocolate_name, r.item_id, " +
                    "i.name as item_name FROM chocolates as c " +
                    "RIGHT JOIN recipes as r ON c.id = r.chocolate_id " +
                    "LEFT JOIN items as i ON i.id = r.item_id ORDER BY r.chocolate_id ASC");

            rs = ps.executeQuery();

            if(!rs.isBeforeFirst()){
                throw generateSoapFaultException(404,
                        "Recipe Not Found", "Server");
            }

            List<Recipe> recipes = new ArrayList<Recipe>();

            while(rs.next()){
                recipes.add(new Recipe(rs));
            }

            Recipe[] response = new Recipe[recipes.size()];

            for(int i = 0; i < recipes.size(); i++){
                response[i] = recipes.get(i);
            }

            return response;
        } catch (Exception err) {
            err.printStackTrace();
            throw generateSoapFaultException(500,
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    @Override
    public String addNewRecipe(String name, IgdrRecipe[] recipes) {
        // if (name == null || recipes == null) {
        //     throw generateSoapFaultException(400, 
        //             "Client Request Error: parameter 'id' or 'quantity' is not specified", "Client");
        // }
        // try {
        //     initConnection();
        //     ps = conn.prepareStatement("SELECT amount FROM chocolates WHERE id = ?")
        // //     ps.setInt(1, id)
        //     rs = ps.executeQuery();
             
        //     // ps = conn.prepareStatement("UPDATE chocolates SET amount =" + id. WHERE")
        // }
        return null;
    }
}
