package com.grails.cxf.client

import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.FactoryBeanNotInitializedException
import org.springframework.context.MessageSource

/**
 * Class used to provide web service clients.  Supports dynamically changing the wsdl document url
 * at runtime, as well as initializing the url from system settings.
 */
class DynamicWebServiceClient implements FactoryBean<Object> {

    String wsdlURL
    String wsdlServiceName
    Class<?> clientInterface
    Boolean enableDefaultLoggingInterceptors
    def clientPolicyMap = [:]
    String serviceEndpointAddress
    String serviceName
    WebServiceClientFactory webServiceClientFactory
    MessageSource messageSource
    def outInterceptors = []
    def inInterceptors = []
    def outFaultInterceptors = []
    def httpClientPolicy
    String proxyFactoryBindingId

    Object getObject() throws FactoryBeanNotInitializedException, MalformedURLException {
        if(!clientInterface || !serviceEndpointAddress) {
            throw new FactoryBeanNotInitializedException("""Web service client cannot be created
before setting the clientInterface=${clientInterface} and 
serviceEndpointAddress=${serviceEndpointAddress} properties""")
        }
        webServiceClientFactory.getWebServiceClient(wsdlURL,
                                                    wsdlServiceName,
                                                    clientInterface,
                                                    serviceName,
                                                    serviceEndpointAddress,
                                                    enableDefaultLoggingInterceptors,
                                                    clientPolicyMap,
                                                    outInterceptors,
                                                    inInterceptors,
                                                    outFaultInterceptors,
                                                    httpClientPolicy,
                                                    proxyFactoryBindingId)
    }

    Class<?> getObjectType() {
        clientInterface
    }

    boolean isSingleton() {
        true
    }
}
