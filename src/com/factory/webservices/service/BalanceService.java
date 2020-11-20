package com.factory.webservices.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BalanceService {
    @WebMethod
    public Integer getBalance();

    @WebMethod
    public String doTransaction(Integer amount);
}
