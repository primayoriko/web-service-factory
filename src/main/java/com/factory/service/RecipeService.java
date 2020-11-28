package com.factory.service;

import com.factory.model.Recipe;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RecipeService {
    @WebMethod
    public Recipe[] getRecipe(Integer chocolate_id);

}

