package it.enuwa.sfdc.utils;

/**
 * Created by festini on 4/11/17.
 */
public class AwsToSalesforceException extends Exception{

    private final int httpCode;

    public AwsToSalesforceException(String message,int httpCode){
        super(message);
        this.httpCode = httpCode;
    }
    
    
}
