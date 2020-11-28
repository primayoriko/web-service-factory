package com.factory.service;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.factory.model.Chocolate;
import com.factory.service.Service;
import com.factory.service.ChocolateService;

@WebService(endpointInterface = "com.factory.service.ChocolateService")
@HandlerChain(file="handlers.xml")
public class ChocolateServiceImpl extends Service implements ChocolateService {
    @Override
    public Chocolate getChocolate(Integer id) {
        if (id == null) {
            throw generateSoapFaultException(400, 
                    "Client Request Error: parameter 'id' is not specified", "Client");
        }
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM chocolates where id=" + id.toString());

            rs = ps.executeQuery();

            if(rs.isBeforeFirst()){ 
                rs.next();
                return new Chocolate(rs);
            }

            System.out.println("Record empty");
            throw generateSoapFaultException(404, 
                    "Chocolate ID not found.", "Client");
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
    public Chocolate[] getChocolates() {
        try{
            initConnection();
//            ps = conn.prepareStatement("SELECT * FROM requests ORDER BY status");
            ps = conn.prepareStatement("SELECT * FROM chocolates");

            List<Chocolate> chocolates = new ArrayList<>();
            rs = ps.executeQuery();

            while(rs.next()){
                chocolates.add(new Chocolate(rs));
            }

            Chocolate[] response = new Chocolate[chocolates.size()];
            response = chocolates.toArray(response);

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
    public String changeAmount(Integer id, Integer amount) {
        if (id == null || amount == null) {
            throw generateSoapFaultException(400, 
                    "Client Request Error: parameter 'id' or 'amount' is not specified", "Client");
        }
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM chocolates where id=" + id.toString());

            rs = ps.executeQuery();

            if(rs.isBeforeFirst()){ 
                rs.next();
                Chocolate chocolate = new Chocolate(rs);
                if (chocolate.isValidChange(amount)) {
                    chocolate.changeAmount(amount);
                } else {
                    throw generateSoapFaultException(404, 
                            "Client Request Error: Amount is invalid!", "Client");
                }

                Statement statement = conn.createStatement();
                statement.executeUpdate("UPDATE chocolates SET amount=" + chocolate.getAmount().toString() + " WHERE id=" + id.toString());
                return String.format("Chocolate stock for id=%d has been modified to %d", id, chocolate.getAmount());
            }

            System.out.println("Record empty");
            throw generateSoapFaultException(404, 
                    "Chocolate ID not found.", "Client");
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
    public String addChocolate(){
        return null;
    }
}
