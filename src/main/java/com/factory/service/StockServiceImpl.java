package com.factory.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "com.factory.service.StockService")
public class StockServiceImpl implements StockService {
    @Override
    public String addStock(){
        return null;
    }
}
