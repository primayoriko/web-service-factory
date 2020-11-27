package com.factory.service;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.factory.model.Balance;

@WebService(endpointInterface = "com.factory.service.BalanceService")
public class BalanceServiceImpl extends Service implements BalanceService {
    @Override
    public Balance getBalance(){
        AllowCORS();
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
<<<<<<< HEAD
    public String doTransaction(Integer amount){
=======
    public Balance doTransaction(Integer amount) {
        AllowCORS();
        if (amount == null) {
            throw generateSoapFaultException(400, 
                    "Client Request Error: parameter 'amount' is not specified", "Client");
        }
        
>>>>>>> 9217c7f53bbb63d7f0d6e02ee10d2fcdbd3d9d6d
        try{
            initConnection();
            
            ps = conn.prepareStatement("SELECT * FROM balance LIMIT 1");
            
            rs = ps.executeQuery();
            
            if (rs.isBeforeFirst()) { // Check not empty
                rs.next();
                Balance balance = new Balance(rs);
                if (balance.isValidTransaction(amount)) {
                    balance.doTransaction(amount);
                } else {
<<<<<<< HEAD
                    webServiceContext.getMessageContext()
                            .put(MessageContext.HTTP_RESPONSE_CODE, 404);
                    return "Amount is invalid!";
=======
                    throw generateSoapFaultException(404, 
                            "Client Request Error: Amount is invalid!", "Client");
>>>>>>> 9217c7f53bbb63d7f0d6e02ee10d2fcdbd3d9d6d
                }

                Statement statement = conn.createStatement();
                statement.executeUpdate("UPDATE balance SET amount=" + balance.getAmount().toString() + " WHERE id=1");
<<<<<<< HEAD
                return balance.getAmount().toString();
            }

            webServiceContext.getMessageContext()
                    .put(MessageContext.HTTP_RESPONSE_CODE, 404);
            return "Record is empty";
        } catch (Exception e){
            e.printStackTrace();
            webServiceContext.getMessageContext()
                    .put(MessageContext.HTTP_RESPONSE_CODE, 500);
            return e.getMessage();
=======
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
>>>>>>> 9217c7f53bbb63d7f0d6e02ee10d2fcdbd3d9d6d
        } finally {
            closeConnection();
        }
    }
}
