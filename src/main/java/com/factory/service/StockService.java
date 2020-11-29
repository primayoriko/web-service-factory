package com.factory.service;

import com.factory.model.Stock;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@HandlerChain(file = "handlers.xml")
public interface StockService {
    @WebMethod
    public Stock[] getStocks();

    @WebMethod
    public String addStock(@WebParam(name="name") String name, @WebParam(name="expire_date") String expire_date, @WebParam(name="amount") Integer amount);
}
