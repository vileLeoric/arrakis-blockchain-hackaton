package it.enuwa.sfdc.sfdc;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.enuwa.sfdc.beans.CredentialsEntity;
import it.enuwa.sfdc.utils.DatabaseManager;
import it.enuwa.sfdc.utils.Email;
import it.enuwa.sfdc.utils.Validator;
import it.enuwa.sfdc.utils.ValidatorUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Created by festini on 4/19/16.
 */
public class Salesforce {

    private static final Logger logger = LoggerFactory.getLogger(Salesforce.class);

    public static void boot(){

        Optional<CredentialsEntity> credentialsEntity = DatabaseManager.selectOAuthToken();

        if(!credentialsEntity.isPresent()){
            accessToken();
        }else{
            logger.info("access token found: "+credentialsEntity.get().toString());
        }
    }

    public static void accessToken(){

        logger.info("***** METHOD BEGIN: accessToken");


        String sfdcUser = null;
        String sfdcPass = null;
        String consumerKey = null;
        String consumerSecret = null;
        String endpoint = null;

        sfdcUser = System.getProperty("sfdcUser");
        sfdcPass = System.getProperty("sfdcPass");
        consumerKey = System.getProperty("consumerKey");
        consumerSecret = System.getProperty("consumerSecret");
        endpoint = System.getProperty("endpoint");

        Validator<String> validator = ValidatorUtils.getEmptyOrNullValidator();

        if(validator.validate(sfdcPass) &&
                validator.validate(sfdcUser) &&
                validator.validate(consumerKey) &&
                validator.validate(consumerSecret) && validator.validate(endpoint) ){

            logger.info(" SFDC: using environment configuration");
            logger.info(" SFDC: "+sfdcUser);
            logger.info(" SFDC: "+sfdcPass);
            logger.info(" SFDC: "+consumerKey);
            logger.info(" SFDC: "+consumerSecret);
            logger.info(" SFDC: "+endpoint);

            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost post = new HttpPost(endpoint);

                List<NameValuePair> nvps = new ArrayList<>();

                nvps.add(new BasicNameValuePair("grant_type", "password"));
                nvps.add(new BasicNameValuePair("client_id", consumerKey));
                nvps.add(new BasicNameValuePair("client_secret", consumerSecret));
                nvps.add(new BasicNameValuePair("username", sfdcUser));
                nvps.add(new BasicNameValuePair("password", sfdcPass));

                post.setEntity(new UrlEncodedFormEntity(nvps));

                String loginresponse = client.execute(post, new SalesforceResponseHandler());

                //String sessionId=getSessionId(loginresponse);
                //String serviceResponse =RestCaller.execute("https://ap1.salesforce.com/services/apexrest/AccountDetails","GET","00158000002fjCE","application/xml",sessionId);
                logger.debug("accessToken SFDC API response" + loginresponse);

                ObjectMapper mapper = new ObjectMapper();
                AccessTokenResponse accResponse = mapper.readValue(loginresponse, AccessTokenResponse.class);

                DatabaseManager.saveOAuthToken(accResponse);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        else {
        /*
        * retrieve from properties file the private load balancer
        * */
            logger.info("SFDC : using properties file");
            Properties prop = new Properties();
            try (InputStream in = Salesforce.class.getClassLoader().getResourceAsStream("salesforce.properties");
                    CloseableHttpClient client = HttpClients.createDefault()) {
                prop.load(in);

                endpoint = (String) prop.get("server");
                sfdcUser = (String) prop.get("sfdcuser");
                sfdcPass = (String) prop.get("sfdcpass");
                consumerKey = (String) prop.get("consumerkey");
                consumerSecret = (String) prop.get("consumersecret");

                HttpPost post = new HttpPost(endpoint);

                List<NameValuePair> nvps = new ArrayList<>();

                nvps.add(new BasicNameValuePair("grant_type", "password"));
                nvps.add(new BasicNameValuePair("client_id", consumerKey));
                nvps.add(new BasicNameValuePair("client_secret", consumerSecret));
                nvps.add(new BasicNameValuePair("username", sfdcUser));
                nvps.add(new BasicNameValuePair("password", sfdcPass));

                post.setEntity(new UrlEncodedFormEntity(nvps));

                String loginresponse = client.execute(post, new SalesforceResponseHandler());

                //String sessionId=getSessionId(loginresponse);
                //String serviceResponse =RestCaller.execute("https://ap1.salesforce.com/services/apexrest/AccountDetails","GET","00158000002fjCE","application/xml",sessionId);
                logger.debug("accessToken SFDC API response" + loginresponse);

                ObjectMapper mapper = new ObjectMapper();
                AccessTokenResponse accResponse = mapper.readValue(loginresponse, AccessTokenResponse.class);

                DatabaseManager.saveOAuthToken(accResponse);

            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("***** METHOD END: accessToken");
    }

    public static void sendOrderMessage(String message) {
        boot();

        logger.info("AWS to SFDC message: "+message);

        Optional<CredentialsEntity> credentials = DatabaseManager.selectOAuthToken();

        credentials.ifPresent(c -> talktoSfdc(c,message));

    }

    private static String executeRequest(CredentialsEntity credentialsEntity, String message){
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){

            //services/apexrest/order
            HttpPost post = new HttpPost(credentialsEntity.getInstanceUrl()+"/services/apexrest/order");

            StringEntity se = new StringEntity(message);
            post.setEntity(se);
            post.setHeader("Content-type", "application/json");
            post.setHeader("Authorization", "Bearer "+credentialsEntity.getAccessToken());

            String feedbackString = httpClient.execute(post,new SalesforceResponseHandler());

            return feedbackString;
        }catch(Throwable t){
            logger.error("error in aws -> sfdc flow: "+t.getMessage());
            Email.sendSimple(message,"Error AWS ---> SFDC :"+t.getMessage());
            return null;
        }
    }

    public static void talktoSfdc(CredentialsEntity c, String message) {
        String response = executeRequest(c,message);
        if(response == null){
            // try to refresh token and execute again
            DatabaseManager.deleteOAuthToken();
            accessToken();
            Optional<CredentialsEntity> cred = DatabaseManager.selectOAuthToken();
            if(cred.isPresent()){
                response = executeRequest(cred.get(),message);
            }
            if(response == null){
                logger.error("unable to refresh the access token aws-->sfdc (changed credentials??)");
            }
        }
    }

}
