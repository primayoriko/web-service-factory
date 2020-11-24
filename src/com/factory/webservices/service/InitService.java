package com.factory.webservices.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface InitService {
    @WebMethod
    public String[] initDatabase();

    @WebMethod
    public String[] initDummy();
}
