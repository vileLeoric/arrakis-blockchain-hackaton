package it.enuwa.sfdc.resources;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.enuwa.sfdc.beans.CredentialsEntity;
import it.enuwa.sfdc.beans.Message;
import it.enuwa.sfdc.sfdc.Salesforce;
import it.enuwa.sfdc.utils.DatabaseManager;
import it.enuwa.sfdc.utils.Email;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by festini on 3/26/17.
 */
public class SaveOrderResource extends ServerResource{

    private static final Logger logger = LoggerFactory.getLogger(SaveOrderResource.class);

    @Get("json")
    public Message save(){

        logger.info("test resource ---- start");

        Message m = new Message();
        m.setMessage("success");

        Email.sendSimple("test call","Test subject");

        logger.info("test resource ---- end");

        return m;
    }

    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }


}
