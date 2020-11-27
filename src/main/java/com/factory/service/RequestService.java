package com.factory.service;

import com.factory.model.Request;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RequestService {
    @WebMethod
    public Request[] getRequests();

    @WebMethod
    public String changeRequestStatus(Integer Id, String status);

     @WebMethod
     public String addRequest(Integer chocolate_id, Integer amount);
}
