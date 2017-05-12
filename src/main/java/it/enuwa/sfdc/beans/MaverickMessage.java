package it.enuwa.sfdc.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by festini on 4/7/17.
 */
public class MaverickMessage {

    private String type;
    private String messageId;
    private String topicArn;
    private String message;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonCreator
    public MaverickMessage(
            @JsonProperty("Type") String type,
            @JsonProperty("MessageId") String mid,
            @JsonProperty("TopicArn") String topicArn,
            @JsonProperty("Message") String message) {
        this.type = type;
        this.messageId = mid;
        this.topicArn = topicArn;
        this.message = message;

    }


}
