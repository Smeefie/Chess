package shared.in;

import shared.BaseMessage;

public class MessageRegister extends BaseMessage
{
    private String username;

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
}
