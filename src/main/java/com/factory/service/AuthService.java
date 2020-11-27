package com.factory.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface AuthService {
    @WebMethod
    public String register(@WebParam(name="username") String username, @WebParam(name="email") String email,
            @WebParam(name="name") String name, @WebParam(name="password") String password);

    @WebMethod
    public String[] login(@WebParam(name = "email") String email, @WebParam(name = "password") String password);
    
    @WebMethod
    public Boolean isAlreadyExists(@WebParam(name="username") String username, @WebParam(name="email") String email);
}
