package com.factory.service;

import com.factory.model.Stock;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface StockService {
    @WebMethod
    public Stock[] getStocks();

    @WebMethod
    public String addStock();
}
