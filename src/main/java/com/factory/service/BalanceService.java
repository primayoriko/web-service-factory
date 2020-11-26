package com.factory.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.factory.model.Response;
import com.factory.model.Balance;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BalanceService {
    @WebMethod
    public Response<Balance> getBalance();

    @WebMethod
    public String[] doTransaction(Integer amount);
}
