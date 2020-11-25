package com.factory.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.factory.service.StockService;
import com.factory.model.Stock;
import com.factory.service.Service;

import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.factory.service.StockService")
public class StockServiceImpl extends Service implements StockService {
    @Override
    public String[] getStocks() {
        return new String[] {"Masih dibuat yak punten"};
//        try{
//            initConnection();
//            List<String> output = new ArrayList<>();
//
//            ps = conn.prepareStatement("SELECT * FROM requests");
//
//            rs = ps.executeQuery();
//
//            if(rs.isBeforeFirst()){ // Check not empty
//                rs.next();
//                return rs.getInt("amount");
//            }
//
//            System.out.println("Record empty");
//            return 0;
//        } catch (Exception e){
//            e.printStackTrace();
//            return 0;
//        } finally {
//            closeConnection();
//        }
    }

}
