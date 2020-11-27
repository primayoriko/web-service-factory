package com.factory.model;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.namespace.QName;

public class GenerateSoapFault {
    private SOAPFault soapFault;

    public GenerateSoapFault(String message, String faultCode) {
        try {
            SOAPFactory soapFactory;
            soapFactory = SOAPFactory.newInstance();
            soapFault = soapFactory.createFault(message,
                    new QName("http://schemas.xmlsoap.org/soap/envelope/", faultCode));
            getSoapFault();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }
    
    public SOAPFault getSoapFault(){
        return soapFault;
    }
}
