package com.factory.service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService(endpointInterface = "com.factory.service.RequestService")
public class RequestServiceImpl extends Service implements RequestService {
    @Resource
    private WebServiceContext webServiceContext;
    
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
