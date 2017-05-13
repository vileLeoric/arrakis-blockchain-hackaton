package it.enuwa.sfdc.resources;

import com.eternitywall.ots.OpenTimestamps;
import it.enuwa.sfdc.beans.Message;
import it.enuwa.sfdc.beans.VerifyMessage;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by festini on 5/13/17.
 */
public class VerifyResource extends ServerResource{

    private static final Logger logger = LoggerFactory.getLogger(UpgradeResource.class);

    @Post("json")
    public Message save(VerifyMessage message){

        logger.info("verify resource ---- start");
        String hexOts = message.getMessage();
        byte[] blob = message.getFile().getBytes(StandardCharsets.UTF_8);
        byte[] k = DatatypeConverter.parseHexBinary(hexOts);

        Long timestamp = null;
        try {
            timestamp = OpenTimestamps.verify(blob,k);
            if(timestamp==null){
                Message m = new Message();
                m.setMessage("Status is still pending, please try to upgrade");
                return m;
            }else {
                Message m = new Message();
                m.setMessage("Success! Bitcoin attests data existed as of "+ timestamp);
                return m;
            }

        } catch (IOException e) {
           logger.error("{}",e);
            Message l = new Message();
            l.setMessage("Error");
            return l;
        }


    }


}
