package com.factory.service;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.factory.model.Balance;

@WebService(endpointInterface = "com.factory.service.BalanceService")
@HandlerChain(file = "handlers.xml")
public class BalanceServiceImpl extends Service implements BalanceService {
    @Override
    public Balance getBalance(){
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM balance LIMIT 1");

            rs = ps.executeQuery();

            if(rs.isBeforeFirst()){ 
                rs.next();
                return new Balance(rs);
            }

            System.out.println("Record empty");
            throw generateSoapFaultException(404, 
                    "Internal Server Error. Please try again later.", "Server");
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
    public Balance changeAmount(Integer amount) {
        if (amount == null) {
            throw generateSoapFaultException(400, 
                    "Client Request Error: parameter 'amount' is not specified", "Client");
        }
        
        try{
            initConnection();
            
            ps = conn.prepareStatement("SELECT * FROM balance LIMIT 1");
            
            rs = ps.executeQuery();
            
            if (rs.isBeforeFirst()) { // Check not empty
                rs.next();
                Balance balance = new Balance(rs);
                if (balance.isValidChange(amount)) {
                    balance.changeAmount(amount);
                } else {
                    throw generateSoapFaultException(404, 
                            "Client Request Error: Amount is invalid!", "Client");
                }

                Statement statement = conn.createStatement();
                statement.executeUpdate("UPDATE balance SET amount=" + balance.getAmount().toString() + " WHERE id=1");
                return balance;
            }
            
            throw generateSoapFaultException(404, 
                    "Internal Server Error. Please try again later.", "Server");
        } catch (SOAPFaultException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw generateSoapFaultException(500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }
}
