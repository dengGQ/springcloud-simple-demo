
package com.fotic.it.support.signature.config;

import com.fotic.it.support.signature.api.webservice.impl.ElectronicSignatureWebServiceImpl;
import com.fotic.it.support.signature.api.webservice.impl.SignatureByRulesWebServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author mfh
 */
@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Bean
    public Endpoint electronicSignatureEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new ElectronicSignatureWebServiceImpl());
        endpoint.getInInterceptors().add(new LoggingInInterceptor());
        endpoint.getInInterceptors().add(new LoggingOutInterceptor());
        endpoint.publish("/ElectronicSignatureWebService");
        return endpoint;
    }

    @Bean
    public Endpoint signatureByRulesWebServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new SignatureByRulesWebServiceImpl());
        endpoint.getInInterceptors().add(new LoggingInInterceptor());
        endpoint.getInInterceptors().add(new LoggingOutInterceptor());
        endpoint.publish("/SignatureByRulesWebService");
        return endpoint;
    }
}
