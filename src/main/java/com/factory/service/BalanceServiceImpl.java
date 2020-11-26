package com.factory.service;

import java.sql.Statement;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.factory.model.Balance;

@WebService(endpointInterface = "com.factory.service.BalanceService")
public class BalanceServiceImpl extends Service implements BalanceService {
    @Resource
    private WebServiceContext webServiceContext;

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
            webServiceContext.getMessageContext()
                    .put(MessageContext.HTTP_RESPONSE_CODE, 404);
            return null;
        } catch (Exception e){
            e.printStackTrace();
            webServiceContext.getMessageContext()
                    .put(MessageContext.HTTP_RESPONSE_CODE, 500);
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public String[] doTransaction(Integer amount){
        try{
            initConnection();
            
            ps = conn.prepareStatement("SELECT * FROM balance LIMIT 1");
            
            rs = ps.executeQuery();
            
            if(rs.isBeforeFirst()){ // Check not empty
                rs.next();
                Balance balance = new Balance(rs);
                if (balance.getAmount() >= amount) {
                    balance.setAmount(balance.getAmount() - amount);
                } else {
                    return new String[]{ "ERROR", "Amount is invalid!" };
                }

                Statement statement = conn.createStatement();
                statement.executeUpdate("UPDATE balance SET amount=" + balance.getAmount().toString() + " WHERE id=1");
                return new String[] { "SUCCESS", balance.getAmount().toString() };
            }

            return new String[]{ "ERROR", "Record is empty" };
        } catch (Exception e){
            e.printStackTrace();
            return new String[]{ "ERROR", e.toString() };
        } finally {
            closeConnection();
        }
    }
}
