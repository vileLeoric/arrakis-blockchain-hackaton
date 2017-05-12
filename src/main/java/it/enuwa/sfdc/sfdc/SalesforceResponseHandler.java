package it.enuwa.sfdc.sfdc;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SalesforceResponseHandler implements ResponseHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(SalesforceResponseHandler.class);

    @Override
    public String handleResponse(final HttpResponse response)
            throws IOException {
        int status = response.getStatusLine()
                .getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils
                    .toString(entity) : null;
        } else {
            HttpEntity entity = response.getEntity();
            logger.error(EntityUtils.toString(entity));
            throw new IOException(
                    "Unexpected response status: " + status);
        }
    }

}