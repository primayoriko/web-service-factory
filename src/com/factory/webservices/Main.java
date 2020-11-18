package com.factory.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = Style.RPC)
public class Main {
//  @WebMethod
//  public String sayHelloWorldFrom(String from) {
//    String result = "Hello, world, from " + from;
//    System.out.println(result);
//    return result;
//  }
//  @WebMethod
//  public String bonjour(String name) {
//    return String.format("Bonjour %s", name);
//  }
  public static void main(String[] argv) {
    Object implementor = new Main();
    String address = "http://localhost:9000/HelloWorld";
    Endpoint.publish(address, implementor);
  }
}
