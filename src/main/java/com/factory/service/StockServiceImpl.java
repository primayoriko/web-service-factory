package com.factory.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "com.factory.service.StockService")
public class StockServiceImpl extends Service implements StockService {
    @Override
    public String addStock(){
        return null;
    }
}
