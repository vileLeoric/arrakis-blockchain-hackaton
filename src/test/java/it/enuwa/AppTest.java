package it.enuwa;

import it.enuwa.sfdc.beans.Message;
import it.enuwa.sfdc.resources.SaveOrderResource;
import it.enuwa.sfdc.utils.BlockchainUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.FileOutputStream;
import java.io.IOException;

public class AppTest extends TestCase{

    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    @org.junit.Test
    public void testSignature() throws IOException {

        Message m = new Message();
        m.setMessage("cgU/9EsIBiHdiAb24r7RRrRmcMtVOI5sjlycp/l5KrU=");

        byte[] blob = BlockchainUtils.stamp(m.getMessage());

        try (FileOutputStream fos = new FileOutputStream("testtest-"+System.currentTimeMillis()+".ots")) {
            fos.write(blob);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        String hex = DatatypeConverter.printHexBinary(blob);
        logger.info("{}",hex);
    }


}
