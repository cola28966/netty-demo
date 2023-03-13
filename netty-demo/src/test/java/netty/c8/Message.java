package netty.c8;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Message implements Serializable {

    public int sequenceId;

    private int messageType;

    public abstract int getMessageType();

    public static final int LoginRequestMessage = 0;
}
