package com.factory.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;

import com.factory.model.Balance;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BalanceService {
    @WebMethod
    public Balance getBalance();

    @WebMethod
    public Balance doTransaction(@WebParam(name="amount") Integer amount);
}
