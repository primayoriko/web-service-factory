package com.factory.webservices.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface StockService {
    @WebMethod
    public String[] getStocks();

    // @WebMethod
    // public String[] getStock(Integer id);

    // @WebMethod
    // public String[] requestStock();

    // @WebMethod
    // public String[] addStock();
}
