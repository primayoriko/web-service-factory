package com.factory.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.factory.model.Stock;

@WebService(endpointInterface = "com.factory.service.RequestService")
public class RequestServiceImpl extends Service implements RequestService {
    @Resource
    private WebServiceContext webServiceContext;
    
    @Override
    public Stock[] getStocks() {
        AllowCORS(webServiceContext);
        try{
            initConnection();
            List<Stock> output = new ArrayList<>();

            ps = conn.prepareStatement("SELECT * FROM requests");

            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) { // Check not empty
                while (rs.next()) {
                    Stock stock = new Stock(rs);
                    output.add(stock);
                }
                Stock[] outputStock = new Stock[output.size()];
                outputStock = output.toArray(outputStock);
                return outputStock;
            }

            System.out.println("Record empty");
            throw generateSoapFaultException(ctx, 404, 
                    "Internal Server Error. Please try again later.", "Server");
        } catch (SOAPFaultException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw generateSoapFaultException(ctx, 500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }
    
    @Override
    public Stock getStock(Integer id) {
        AllowCORS(webServiceContext);
        if (id == null) {
            throw generateSoapFaultException(ctx, 400,
                    "Client Request Error: parameter 'id' is not specified", "Client");
        }
        
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM stock where id=" + id.toString());

            rs = ps.executeQuery();

            if(rs.isBeforeFirst()){ // Check not empty
                rs.next();
                return new Stock(rs);
            }

            System.out.println("Record empty");
            throw generateSoapFaultException(ctx, 404, 
                    "Internal Server Error. Please try again later.", "Server");
        } catch (SOAPFaultException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw generateSoapFaultException(ctx, 500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

}
