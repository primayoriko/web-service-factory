package com.factory.service;

import com.factory.model.Recipe;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService(endpointInterface = "com.factory.service.RecipeService")
public class RecipeServiceImpl implements RecipeService {
    @Override
    public Recipe[] getRecipe(Integer chocolate_id){
        return null;
    }
}
