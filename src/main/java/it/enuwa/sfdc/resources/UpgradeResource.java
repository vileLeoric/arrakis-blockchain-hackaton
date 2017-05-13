package it.enuwa.sfdc.resources;

import com.eternitywall.ots.OpenTimestamps;
import it.enuwa.sfdc.beans.Message;
import it.enuwa.sfdc.utils.BlockchainUtils;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by festini on 5/13/17.
 */
public class UpgradeResource extends ServerResource {

    private static final Logger logger = LoggerFactory.getLogger(UpgradeResource.class);

    @Post("json")
    public Message save(Message message){
        logger.info("upgrade resource ---- start");
        String hexOts = message.getMessage();
        byte[] k = DatatypeConverter.parseHexBinary(hexOts);
        byte[] blobOTS;

        blobOTS = OpenTimestamps.upgrade(k);

        if(Arrays.equals(k, blobOTS)) {
            return message;
        }else{
            Message m = new Message();
            m.setMessage(DatatypeConverter.printHexBinary(blobOTS));
            return m;
        }

    }

}
