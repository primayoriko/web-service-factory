package com.factory.service;

import com.factory.model.Balance;
import com.factory.model.Chocolate;
import com.factory.model.Request;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPFaultException;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;

import com.factory.model.Balance;
import com.factory.model.Request;
import com.factory.model.Status;

import javax.xml.ws.handler.MessageContext;

@WebService(endpointInterface = "com.factory.service.RequestService")
@HandlerChain(file = "handlers.xml")
public class RequestServiceImpl extends Service implements RequestService {
    @Override
    public Request[] getRequests() {
        try {
            initConnection();

            ps = conn.prepareStatement("SELECT r.id, r.amount, r.status, r.chocolate_id, c.name FROM " +
                    "requests as r LEFT JOIN chocolates as c ON r.chocolate_id = c.id  ORDER BY status");

            ArrayList<Request> requests = new ArrayList<Request>();
            rs = ps.executeQuery();

            while (rs.next()) {
                requests.add(new Request(rs));
            }

            Request[] response = new Request[requests.size()];
            for (int i = 0; i < requests.size(); i++) {
                response[i] = requests.get(i);
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
    public String addRequest(Integer chocolateId, Integer amount){
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM chocolates WHERE id = ?");
            ps.setInt(1, chocolateId);

            rs = ps.executeQuery();

            if(!rs.isBeforeFirst()) {
                webServiceContext.getMessageContext()
                        .put(MessageContext.HTTP_RESPONSE_CODE, 404);
                return "Chocolate not found";
            }

            rs.next();
            Chocolate chocolate = new Chocolate(rs);

            ps = conn.prepareStatement("INSERT INTO requests (chocolate_id, amount, status) VALUES (?, ?, ?)");

            ps.setInt(1, chocolateId);
            ps.setInt(2, amount);
            ps.setString(3, "Waiting");

            rs = ps.executeQuery();

            return String.format("Created");
        } catch (Exception err){
            err.printStackTrace();
            throw generateSoapFaultException(500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    @Override
    public String rejectRequest(Integer chocolateId) {
        if (chocolateId == null) {
            throw generateSoapFaultException(400, "Client Request Error: parameter 'id' is not specified", "Client");
        }
        try {
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM requests WHERE id = ?");

            ps.setInt(1, chocolateId);

            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                throw generateSoapFaultException(404, "Client Request Error: Request Not Found", "Client");
            }

            rs.next();
            Request request = new Request(rs);

            if (!request.getStatus().equals(Status.WAITING)) {
                throw generateSoapFaultException(400,
                        "Client Request Error: Bad Request, " + "the status must be still waiting", "Client");
            }

            ps = conn.prepareStatement("UPDATE requests SET status = ? WHERE id = ?");

            ps.setString(1, "Rejected");
            ps.setInt(2, chocolateId);

            rs = ps.executeQuery();

            return String.format("Request with ID %d has been rejected", chocolateId);
        } catch (Exception err) {
            err.printStackTrace();
            webServiceContext.getMessageContext().put(MessageContext.HTTP_RESPONSE_CODE, 500);
            throw generateSoapFaultException(500, "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    

    @Override
    public String deliverRequest(Integer chocolateId){
        if (chocolateId == null) {
            throw generateSoapFaultException(400, "Client Request Error: parameter 'id' is not specified",
                    "Client");
        }
        try{
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM requests WHERE id = ?");

            ps.setInt(1, chocolateId);

            rs = ps.executeQuery();

            if(!rs.isBeforeFirst()){
                throw generateSoapFaultException(404, "Client Request Error: Request Not Found",
                        "Client");
            }

            rs.next();
            Request request = new Request(rs);

            if(!request.getStatus().equals(Status.WAITING)){
                throw generateSoapFaultException(400, "Client Request Error: Bad Request, " +
                                "the status must be still waiting", "Client");
            }

            ps = conn.prepareStatement("SELECT * FROM chocolates WHERE id = ?");

            ps.setInt(1, chocolateId);

            rs = ps.executeQuery();

            rs.next();
            Chocolate chocolate = new Chocolate(rs);

            if(chocolate.getAmount() < request.getAmount()){
                throw generateSoapFaultException(400, "Client Request Error: Bad Request, " +
                        "current chocolate not sufficient", "Client");
            }

            ps = conn.prepareStatement("UPDATE requests SET status = ? WHERE id = ?");

            ps.setString(1, "Delivered");
            ps.setInt(2, chocolateId);

            rs = ps.executeQuery();

            ps = conn.prepareStatement("UPDATE chocolates SET amount = ? WHERE id = ?");

            ps.setInt(1, chocolate.getAmount() - request.getAmount());
            ps.setInt(2, chocolateId);

            rs = ps.executeQuery();

            return String.format("Request with ID %d has been delivered", chocolateId);
        } catch (Exception err){
            err.printStackTrace();
            webServiceContext.getMessageContext()
                    .put(MessageContext.HTTP_RESPONSE_CODE, 500);
            throw generateSoapFaultException(500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }
}
