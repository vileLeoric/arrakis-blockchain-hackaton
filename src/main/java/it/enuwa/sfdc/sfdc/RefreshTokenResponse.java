package it.enuwa.sfdc.sfdc;

/**
 * Created by festini on 4/19/16.
 */
public class RefreshTokenResponse {

    private String id;
    private String access_token;
    private String instance_url;
    private String issued_at;
    private String signature;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getInstance_url() {
        return instance_url;
    }

    public void setInstance_url(String instance_url) {
        this.instance_url = instance_url;
    }

    public String getIssued_at() {
        return issued_at;
    }

    public void setIssued_at(String issued_at) {
        this.issued_at = issued_at;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
