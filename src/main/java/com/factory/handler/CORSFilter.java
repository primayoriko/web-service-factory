package com.factory.handler;

import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;

public class CORSFilter implements SOAPHandler<SOAPMessageContext> {
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    public boolean handleMessage(SOAPMessageContext messageContext) {
        Boolean outboundProperty = (Boolean)
                messageContext.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {
            messageContext.put(SOAPMessageContext.HTTP_RESPONSE_HEADERS, new HashMap<String, List<String>>() {{
                put("Access-Control-Allow-Origin", new ArrayList<String>() {{
                    add("*");
                }});
                // put("Access-Control-Allow-Methods", new ArrayList<String>() {{
                //     add("GET");
                //     add("POST");
                //     add("DELETE");
                //     add("PUT");
                //     add("HEAD");
                //     add("OPTIONS");
                // }});
            }});
        }

        return true;
    }

    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }

    public void close(MessageContext messageContext) {
    }
}