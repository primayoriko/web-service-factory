package com.factory.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPFaultException;
import javax.jws.WebMethod;

import com.factory.model.Stock;
import com.factory.model.Balance;
import com.factory.model.Request;
import com.factory.model.Status;

import javax.xml.ws.handler.MessageContext;

@WebService(endpointInterface = "com.factory.service.RequestService")
public class RequestServiceImpl extends Service implements RequestService {
    @Override
    public Stock[] getStocks() {
        AllowCORS();
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
            throw generateSoapFaultException(404, 
                    "Internal Server Error. Please try again later.", "Server");
        } catch (SOAPFaultException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw generateSoapFaultException(500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }
    
    @Override
    public Stock getStock(Integer id) {
        AllowCORS();
        if (id == null) {
            throw generateSoapFaultException(400, "Client Request Error: parameter 'id' is not specified",
                    "Client");
        }

        try {
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM stock where id=" + id.toString());

            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) { // Check not empty
                rs.next();
                return new Stock(rs);
            }

            System.out.println("Record empty");
            throw generateSoapFaultException(404, "Internal Server Error. Please try again later.", "Server");
        } catch (SOAPFaultException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw generateSoapFaultException(500, "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }
    
    public Request[] getRequests() {
        AllowCORS();
        try{
            initConnection();
            ps = conn.prepareStatement("SELECT * FROM request ORDER BY status");

            ArrayList<Request> requests = new ArrayList<Request>();
            rs = ps.executeQuery();

            while(rs.next()){
                requests.add(new Request(rs));
            }

            Request[] response = new Request[requests.size()];
            for(int i = 0; i < requests.size(); i++){
                response[i] = requests.get(i);
            }

            return response;
        } catch (Exception err){
            err.printStackTrace();
            throw generateSoapFaultException(500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    @Override
    public String[] changeRequestStatus(Integer id, String status) {
        AllowCORS();
        if (id == null || status == null) {
            throw generateSoapFaultException(400, "Client Request Error: parameter 'id' is not specified",
                    "Client");
        }
        try{
            Status newStatus = Request.translateToStatus(status);
            initConnection();

            ps = conn.prepareStatement("UPDATE request SET status = ? WHERE id = ?");

            ps.setString(1, status);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            String response = String.format("Request status for %d has been modified to %s", id, status);
            return new String[] { "Success", response };
        } catch (Exception err){
            err.printStackTrace();
            throw generateSoapFaultException(500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }
}
