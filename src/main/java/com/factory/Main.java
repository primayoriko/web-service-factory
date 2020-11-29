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
  public static void migrateTable(){}

  public static void seedData(){}

  public static void main(String[] argv) {
    // System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
    // System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
    // System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
    // System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    // System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
    try {
      migrateTable();
      seedData();
    } catch (Exception err) {
      System.out.println(err.getMessage());
    }

    final String address = "http://localhost:9000/ws/";
    Endpoint.publish(address + "Init", new InitServiceImpl());
    Endpoint.publish(address + "Auth", new AuthServiceImpl());

    Endpoint.publish(address + "Balance", new BalanceServiceImpl());
    Endpoint.publish(address + "Request", new RequestServiceImpl());
    Endpoint.publish(address + "Chocolate", new ChocolateServiceImpl());
    Endpoint.publish(address + "Stock", new StockServiceImpl());
    Endpoint.publish(address + "Recipe", new RecipeServiceImpl());
  }
}
