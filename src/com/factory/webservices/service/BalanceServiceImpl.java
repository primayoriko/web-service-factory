package com.factory.webservices.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.factory.webservices.service.BalanceService;
import com.factory.webservices.model.Balance;

@WebService(endpointInterface = "com.factory.webservices.service.BalanceService")
public class BalanceServiceImpl extends Service implements BalanceService {
    @Override
    public Integer getBalance(){
        try{
            Class.forName("org.postgresql.Driver");

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
            Class.forName("org.postgresql.Driver");

            initConnection();

            ps = conn.prepareStatement("SELECT * FROM balance LIMIT 1");

            rs = ps.executeQuery();

            if(rs.isBeforeFirst()){ // Check not empty
//                rs.next();
//                return rs.getInt("amount");
            }

            System.out.println("Record empty");
            return "";
        } catch (Exception e){
            e.printStackTrace();
            return "";
        } finally {
            closeConnection();
        }
    }
}
