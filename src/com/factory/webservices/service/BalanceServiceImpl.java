package com.factory.webservices.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.factory.webservices.service.BalanceService;
import com.factory.webservices.model.Balance;
import com.factory.webservices.service.Service;

@WebService(endpointInterface = "com.factory.webservices.service.BalanceService")
public class BalanceServiceImpl extends Service implements BalanceService {
    @Override
    public Integer getBalance(){
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM balance LIMIT 1");

            rs = ps.executeQuery();

            if(rs.isBeforeFirst()){ // Check not empty
                rs.next();
                return rs.getInt("amount");
            }

            System.out.println("Record empty");
            return 0;
        } catch (Exception e){
            e.printStackTrace();
            return 0;
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
                Balance balance = new Balance(rs.getInt("amount"));
                if (balance.isValidTransaction(amount)) {
                    balance.doTransaction(amount);
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
