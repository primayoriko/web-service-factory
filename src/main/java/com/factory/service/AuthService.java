package com.factory.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface AuthService {
    @WebMethod
    public Boolean register(String username, String email,
                            String name, String password);

    @WebMethod
    public String[] login(String email, String password);
}
