package com.factory.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.factory.model.Request;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RequestService {
    @WebMethod
    public Request[] getRequests();

    /*
    Change status to delivered and substract the choco in ws factory
     */
    @WebMethod
    public String deliverRequest(@WebParam(name="id") Integer requestId);

    @WebMethod
    public String rejectRequest(@WebParam(name="id") Integer requestId);

    @WebMethod
    public String addRequest(@WebParam(name="id") Integer chocolateId, @WebParam(name="amount") Integer amount);
    
    // @WebMethod
    // public String[] changeRequestStatus(@WebParam(name="id") Integer Id, @WebParam(name="status") String status);
}
