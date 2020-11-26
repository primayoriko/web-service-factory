package com.factory.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface InitService {
    @WebMethod
    public String[] initSchema();

    @WebMethod
    public String[] initDummy();

    @WebMethod
    public String[][] resetDatabase();
}
