package netty.c8;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestMessage extends Message{

    private String username;

    private String password;

    private String nickname;

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
