package com.factory.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.factory.model.Stock;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RequestService {
    @WebMethod
    public Stock[] getStocks();

    @WebMethod
    public Stock getStock(@WebParam(name="id") Integer id);

    // @WebMethod
    // public String[] requestStock();

    // @WebMethod
    // public String[] addStock();
}
