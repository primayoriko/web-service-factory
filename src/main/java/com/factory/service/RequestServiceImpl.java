package com.factory.service;

import com.factory.model.Balance;
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
            webServiceContext.getMessageContext()
                    .put(MessageContext.HTTP_RESPONSE_CODE, 500);
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public String[] changeRequestStatus(Integer id, String status){
        try{
            Request.Status newStatus = Request.translateToStatus(status);
            initConnection();

            ps = conn.prepareStatement("UPDATE request SET status = ? WHERE id = ?");

            ps.setString(1, status);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            String response = String.format("Request status for %d has been modified to %s", id, status);
            return new String[] { "Success", response };
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
