package com.factory.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.factory.model.Balance;
import com.factory.model.Response;
import com.factory.service.BalanceService;
import com.factory.service.Service;

@WebService(endpointInterface = "com.factory.service.BalanceService")
public class BalanceServiceImpl extends Service implements BalanceService {
    @Override
    public Response<Balance> getBalance(){
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM balance LIMIT 1");

            rs = ps.executeQuery();

            if(rs.isBeforeFirst()){ 
                rs.next();
                return new Response<Balance>(200, new Balance(rs));
            }

            System.out.println("Record empty");
            return new Response<Balance>(404);
        } catch (Exception e){
            e.printStackTrace();
            return new Response<Balance>(500);
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
