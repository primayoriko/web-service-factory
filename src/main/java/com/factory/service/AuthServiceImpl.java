package com.factory.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.factory.service.AuthService;
import com.factory.model.User;
import com.factory.service.Service;

import org.postgresql.util.MD5Digest;

@WebService(endpointInterface = "com.factory.service.AuthService")
@HandlerChain(file="handlers.xml")
public class AuthServiceImpl extends Service implements AuthService {
    @Override
    public String register(String username, String email,
            String name, String password) {
        if (username == null || email == null || name == null || password == null) {
            throw generateSoapFaultException(400, 
                    "Client Request Error: parameter 'username' or 'email' or 'name' or 'password' is not specified", "Client");
        }
        if (username.isEmpty() || email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            throw generateSoapFaultException(400, 
                    "Client Request Error: parameter 'username' or 'email' or 'name' or 'password' is empty", "Client");
        }
        try{
            initConnection();

            if (isExists(username, email)) {
                throw generateSoapFaultException(400, 
                    "Client Request Error: username or email already exist", "Client");
            }

            String sql = "INSERT INTO users(username, email, name, password) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            String hashedPassword = User.hashPassword(username, password);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, name);
            ps.setString(4, hashedPassword);

            int rs = ps.executeUpdate();

            if(rs == 1)
                return String.format("user %s with username %s is successfully registered!", name, username);
            else
                throw generateSoapFaultException(500, 
                        "Server Error: Error registering user", "Server");

        } catch (SOAPFaultException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw generateSoapFaultException(500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    @Override
    public String[] login(String email, String password) {
        if (email == null || password == null) {
            throw generateSoapFaultException(400,
                    "Client Request Error: parameter 'email' or 'password' is not specified", "Client");
        }
        try {
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM users WHERE email='" + email + "'");

            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) { // Check not empty
                rs.next();
                User user = new User(rs);
                if (user.validatePassword(password)) {
                    return new String[] { "SUCCESS", user.getUsername(), user.getName(), user.getEmail() };
                } else {
                    return new String[] { "FAIL" };
                }
            }

            System.out.println("Record empty");
            throw generateSoapFaultException(404, "Client Request Error: User not found.", "Client");
        } catch (SOAPFaultException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw generateSoapFaultException(500, "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    @Override
    public Boolean isAlreadyExists(String username, String email) {
        if (username == null) {
            username = "";
        }
        if (email == null) {
            email = "";
        }
        try {
            initConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw generateSoapFaultException(500, "Internal Server Error. Please try again later.", "Server");
        }
        
        return this.isExists(username, email);
    }
    
    private boolean isExists(String username, String email) {
        try{
            ps = conn.prepareStatement("SELECT * FROM users WHERE username='" + username + "' OR email='" + email + "'");
    
            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                return true;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
