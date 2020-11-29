package com.factory.service;

import com.factory.model.Recipe;
import com.factory.model.Stock;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.factory.service.StockService")
public class StockServiceImpl extends Service implements StockService {
    @Override
    public Stock[] getStocks(){
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM stocks as s " +
                    "LEFT JOIN items as i ON s.item_id = i.id ORDER BY i.id");

            rs = ps.executeQuery();

            List<Stock> stocks = new ArrayList<Stock>();

            while(rs.next()){
                stocks.add(new Stock(rs));
            }

            Stock[] response = new Stock[stocks.size()];

            for(int i = 0; i < stocks.size(); i++){
                response[i] = stocks.get(i);
            }

            return response;
        } catch (Exception err) {
            err.printStackTrace();
            throw generateSoapFaultException(500,
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    @Override
    public String addStock(){
        return null;
    }
}
