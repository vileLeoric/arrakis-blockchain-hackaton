package it.enuwa.sfdc.resources;

import com.eternitywall.ots.OpenTimestamps;
import it.enuwa.sfdc.beans.Message;
import it.enuwa.sfdc.utils.BlockchainUtils;
import it.enuwa.sfdc.utils.Email;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

/**
 * Created by festini on 5/13/17.
 */
public class BlockchainResource extends ServerResource{

    private static final Logger logger = LoggerFactory.getLogger(SaveOrderResource.class);

    @Post("json")
    public Message save(Message message){
        logger.info("blockchain resource ---- start");
        byte[] blobOTS;

        try {
            blobOTS = BlockchainUtils.stamp(message.getMessage());
            Message m = new Message();
            m.setMessage(DatatypeConverter.printHexBinary(blobOTS));
            logger.info("blockchain resource ---- end");
            return m;
        } catch (IOException e) {
            logger.error("IO Exception {}",e);
            Message m = new Message();
            m.setMessage("error");
            logger.info("blockchain resource ---- end");
            return m;
        }
    }
}
