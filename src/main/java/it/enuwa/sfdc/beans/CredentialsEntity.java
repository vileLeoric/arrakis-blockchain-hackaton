package it.enuwa.sfdc.beans;

import javax.persistence.*;

/**
 * Created by Mike on 4/27/2016.
 */
@Entity
@Table(name = "credentials", schema = "waypass_orders", catalog = "")
public class CredentialsEntity {
    private int tokenId;
    private String accessToken;
    private String instanceUrl;
    private String issuedAt;
    private String tokenType;
    private String signature;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "tokenId")
    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    @Basic
    @Column(name = "accessToken")
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Basic
    @Column(name = "instanceUrl")
    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    @Basic
    @Column(name = "issuedAt")
    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    @Basic
    @Column(name = "tokenType")
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Basic
    @Column(name = "signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CredentialsEntity that = (CredentialsEntity) o;

        if (tokenId != that.tokenId) return false;
        if (accessToken != null ? !accessToken.equals(that.accessToken) : that.accessToken != null) return false;
        if (instanceUrl != null ? !instanceUrl.equals(that.instanceUrl) : that.instanceUrl != null) return false;
        if (issuedAt != null ? !issuedAt.equals(that.issuedAt) : that.issuedAt != null) return false;
        if (tokenType != null ? !tokenType.equals(that.tokenType) : that.tokenType != null) return false;
        if (signature != null ? !signature.equals(that.signature) : that.signature != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tokenId;
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        result = 31 * result + (instanceUrl != null ? instanceUrl.hashCode() : 0);
        result = 31 * result + (issuedAt != null ? issuedAt.hashCode() : 0);
        result = 31 * result + (tokenType != null ? tokenType.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }
}
