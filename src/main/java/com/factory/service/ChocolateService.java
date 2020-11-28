package com.factory.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.factory.model.Chocolate;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ChocolateService {
    @WebMethod
    public Chocolate getChocolate(@WebParam(name="id") Integer id);

    @WebMethod
    public Chocolate[] getChocolates();

    @WebMethod
    public String changeAmount(@WebParam(name="id") Integer id, @WebParam(name="amount") Integer amount);

    /*
    Add chocolate with recipe
     */
    @WebMethod
    public String addChocolate();
}
