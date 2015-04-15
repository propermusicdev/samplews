package data;

//import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Lebel on 20/11/2014.
 */
public class MessageInput implements Serializable {
    private static final long serialVersionUID = 1L;
    private int MessageId;
    private String Source;
    private String MessageType;
    private int IncomingStatus;
    private String IncomingMessage;
    private int OutgoingStatus;
    private String OutgoingMessage;
    private Timestamp InsertedTimeStamp;
    private int TTL;

    public MessageInput() {
    }

    public MessageInput(int messageId, String source, String messageType, int incomingStatus, String incomingMessage, int outgoingStatus, String outgoingMessage, Timestamp insertedTimeStamp, int TTL) {
        MessageId = messageId;
        Source = source;
        MessageType = messageType;
        IncomingStatus = incomingStatus;
        IncomingMessage = incomingMessage;
        OutgoingStatus = outgoingStatus;
        OutgoingMessage = outgoingMessage;
        InsertedTimeStamp = insertedTimeStamp;
        this.TTL = TTL;
    }

//    public MessageInput(Message msg) {
//        MessageId = msg.getMessageId();
//        Source = msg.getSource();
//        MessageType = msg.getMessageType();
//        IncomingStatus = msg.getIncomingStatus();
//        IncomingMessage = msg.getIncomingMessage();
//        OutgoingStatus = msg.getOutgoingStatus();
//        OutgoingMessage = msg.getOutgoingMessage();
//        InsertedTimeStamp = msg.getInsertedTimeStamp();
//        this.TTL = msg.getTTL();
//    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @JsonProperty("MessageId")
    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

    @JsonProperty("Source")
    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    @JsonProperty("MessageType")
    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    @JsonProperty("IncomingStatus")
    public int getIncomingStatus() {
        return IncomingStatus;
    }

    public void setIncomingStatus(int incomingStatus) {
        IncomingStatus = incomingStatus;
    }

    @JsonProperty("IncomingMessage")
    public String getIncomingMessage() {
        return IncomingMessage;
    }

    public void setIncomingMessage(String incomingMessage) {
        IncomingMessage = incomingMessage;
    }

    @JsonProperty("OutgoingStatus")
    public int getOutgoingStatus() {
        return OutgoingStatus;
    }

    public void setOutgoingStatus(int outgoingStatus) {
        OutgoingStatus = outgoingStatus;
    }

    @JsonProperty("OutgoingMessage")
    public String getOutgoingMessage() {
        return OutgoingMessage;
    }

    public void setOutgoingMessage(String outgoingMessage) {
        OutgoingMessage = outgoingMessage;
    }

    @JsonProperty("InsertedTimeStamp")
    public Timestamp getInsertedTimeStamp() {
        return InsertedTimeStamp;
    }

    public void setInsertedTimeStamp(Timestamp insertedTimeStamp) {
        InsertedTimeStamp = insertedTimeStamp;
    }

    @JsonProperty("TTL")
    public int getTTL() {
        return TTL;
    }

    public void setTTL(int TTL) {
        this.TTL = TTL;
    }
}

