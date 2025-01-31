package com.factory.service;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.factory.model.GenerateSoapFault;

import java.sql.*;

public class Service {
    @Resource
    protected WebServiceContext webServiceContext;

    protected Connection conn = null;
    protected PreparedStatement ps = null;
    protected ResultSet rs = null;

    @Resource
    WebServiceContext ctx;

    protected void initConnection() throws Exception{
        try{
//            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ws_factory",
                    "postgres", "root");
        } catch (Error err){
            throw err;
        }
    }

    protected void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
    
    protected SOAPFaultException generateSoapFaultException(Integer response_code, String message, String fault_code) {
        webServiceContext.getMessageContext()
                .put(MessageContext.HTTP_RESPONSE_CODE, response_code);
        return new SOAPFaultException(
            new GenerateSoapFault(message, fault_code).getSoapFault()
        );
    }
}
