package com.factory.webservices;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = Style.RPC)
public class Main {
//  @WebMethod
//  public String bonjour(String name) {
//    return String.format("Bonjour %s", name);
//  }
  public static void main(String[] argv) {
    final String address = "http://localhost:9000/ws/";
    Endpoint.publish(address + "Balance", new BalanceServiceImpl());
    Endpoint.publish(address + "Chocolate", new ChocolateServiceImpl());
    Endpoint.publish(address + "Ingredient", new IngredientServiceImpl());
    Endpoint.publish(address + "Recipe", new RecipeServiceImpl());
    Endpoint.publish(address + "Stock", new StockServiceImpl());
  }
}
