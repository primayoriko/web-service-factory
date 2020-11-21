package com.factory.service;

import javax.jws.WebService;

@WebService(endpointInterface = "com.factory.service.BalanceService")
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
