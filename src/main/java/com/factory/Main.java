package com.factory;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

import com.factory.service.*;
import com.factory.model.*;

import javax.jws.WebMethod;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Main {
  @WebMethod
  public String bonjour(String name) {
    return String.format("Bonjour %s", name);
  }

  public static void migrateTable(){

  }

  public static void seedData(){

  }

  public static void main(String[] argv) {
    try {
      migrateTable();
      seedData();
    } catch (Exception err) {
      System.out.println(err.getMessage());
    }

    final String address = "http://localhost:9000/ws/";
    Endpoint.publish(address + "Balance", new BalanceServiceImpl());
    Endpoint.publish(address + "Init", new InitServiceImpl());
    Endpoint.publish(address + "Request", new RequestServiceImpl());
    Endpoint.publish(address + "Chocolate", new ChocolateServiceImpl());
    Endpoint.publish(address + "Auth", new AuthServiceImpl());
//    Endpoint.publish(address + "Ingredient", new IngredientServiceImpl());
//    Endpoint.publish(address + "Recipe", new RecipeServiceImpl());
    Endpoint.publish(address + "Main", new Main());
  }
}
