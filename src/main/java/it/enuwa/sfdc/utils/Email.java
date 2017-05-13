package it.enuwa.sfdc.utils;

import it.enuwa.sfdc.resources.SaveOrderResource;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;


/**
 * Created by festini on 4/19/16.
 */
public class Email {

    private static final Logger logger = LoggerFactory.getLogger(SaveOrderResource.class);

    public static ClientResponse sendSimple(String text,String subject) {

        Client client = ClientBuilder.newClient();
        client.register(HttpAuthenticationFeature.basic(
                "api",
                "REPLACE WITH YOUR API KEY"
        ));

        WebTarget mgRoot = client.target("https://api.mailgun.net/v3");

        Form reqData = new Form();
        reqData.param("from", "Excited User <maverick@sandboxb28ccbadfaed436c8766e2a966c0011d.mailgun.org>");
        reqData.param("to", "m.festini@enuwa.it");
        reqData.param("subject", "AWS to SFDC synchronization error: "+subject);
        reqData.param("text", text);

        try {
            ClientResponse cr = mgRoot
                    .path("/{domain}/messages")
                    .resolveTemplate("domain", "sandboxb28ccbadfaed436c8766e2a966c0011d.mailgun.org")
                    .request(MediaType.APPLICATION_FORM_URLENCODED)
                    .buildPost(Entity.entity(reqData, MediaType.APPLICATION_FORM_URLENCODED))
                    .invoke(ClientResponse.class);

            return cr;
        }catch(Throwable t){
            logger.error("{}",t);
            return null;
        }

    }

}
