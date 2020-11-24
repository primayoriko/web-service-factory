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
    public String doTransaction(Integer amount){
        try{
            initConnection();

            
            ps = conn.prepareStatement("SELECT * FROM balance LIMIT 1");
            
            rs = ps.executeQuery();
            
            if(rs.isBeforeFirst()){ // Check not empty
                rs.next();
                int cur_balance = rs.getInt("amount");
                System.out.println(cur_balance);
                System.out.println(amount);
                int new_balance = cur_balance - amount;
                if (new_balance < 0) {
                    return "Balance not enough!";
                }
                Statement statement = conn.createStatement();
                statement.executeUpdate("UPDATE balance SET amount=" + Integer.toString(new_balance) + " WHERE id=1");
                return Integer.toString(new_balance);
            }

            return "Record empty";
        } catch (Exception e){
            e.printStackTrace();
            return "";
        } finally {
            closeConnection();
        }
    }
}
