package com.factory.service;

import com.factory.model.Balance;
import com.factory.model.Chocolate;
import com.factory.model.Request;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.ArrayList;

@WebService(endpointInterface = "com.factory.service.RequestService")
public class RequestServiceImpl extends Service implements RequestService {
    @Override
    public Request[] getRequests(){
        try{
            initConnection();
//            ps = conn.prepareStatement("SELECT * FROM requests ORDER BY status");
            ps = conn.prepareStatement("SELECT r.id, r.amount, r.status, c.name FROM " +
                    "requests as r LEFT JOIN chocolates as c ON r.chocolate_id = c.id  ORDER BY status");

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
            webServiceContext.getMessageContext()
                    .put(MessageContext.HTTP_RESPONSE_CODE, 500);
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public String changeRequestStatus(Integer id, String status){
        try{
            Request.Status newStatus = Request.translateToStatus(status);
            initConnection();

            ps = conn.prepareStatement("UPDATE requests SET status = ? WHERE id = ?");

            ps.setString(1, status);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            // LOGIC HAVEN'T ADDED

            return String.format("Request status for %d has been modified to %s", id, status);
        } catch (Exception err){
            err.printStackTrace();
            webServiceContext.getMessageContext()
                    .put(MessageContext.HTTP_RESPONSE_CODE, 500);
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public String addRequest(Integer chocolate_id, Integer amount){
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM chocolates WHERE id = ?");
            ps.setInt(1, chocolate_id);

            rs = ps.executeQuery();

            if(!rs.isBeforeFirst()) {
                webServiceContext.getMessageContext()
                        .put(MessageContext.HTTP_RESPONSE_CODE, 404);
                return "Chocolate not found";
            }

            rs.next();
            Chocolate chocolate = new Chocolate(rs);

            ps = conn.prepareStatement("INSERT INTO requests (chocolate_id, amount, status) VALUES (?, ?, ?)");

            ps.setInt(1, chocolate_id);
            ps.setInt(2, amount);
            ps.setString(3, "Waiting");

            rs = ps.executeQuery();

            return String.format("Created");
        } catch (Exception err){
            err.printStackTrace();
            webServiceContext.getMessageContext()
                    .put(MessageContext.HTTP_RESPONSE_CODE, 500);
            return null;
        } finally {
            closeConnection();
        }
    }
}
