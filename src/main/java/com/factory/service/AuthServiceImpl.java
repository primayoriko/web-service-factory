package com.factory.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import com.factory.service.AuthService;
import com.factory.model.User;
import com.factory.service.Service;

@WebService(endpointInterface = "com.factory.service.AuthService")
public class AuthServiceImpl extends Service implements AuthService {
    @Resource
    private WebServiceContext webServiceContext;

    @Override
    public Boolean register(String username, String email,
                            String name, String password){
        return true;
    }

    @Override
    public String[] login(String email, String password){
        return new String[10];
    }
}
