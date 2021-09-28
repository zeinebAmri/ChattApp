package com.kth.chatapp.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public ServletWebServerFactory servletContainer() {
    	//This creates containers that listen for HTTP requests on 8080 port
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
            	//security constraint element for a web application
                SecurityConstraint securityConstraint = new SecurityConstraint();
                //sets the user constraint to confidential.
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                //web resource collection for a web application's security constraint.
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");//Adds URL pattern to be pasrt of the web resource collection
                //Add a new web resource collection to those protected by this security constraint
                securityConstraint.addCollection(collection);
                //add the constraint to the context element which represents a web application.
                context.addConstraint(securityConstraint);
            }
        };
        //in addition to the default connector, add the named connector.
        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcat;
    }

    private Connector getHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
}
