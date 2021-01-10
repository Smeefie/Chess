package shared.out;

import shared.BaseMessage;

public class MessagePlayersOut extends BaseMessage
{
    private int playerId;
    private String username;

    public int getPlayerId()
    {
        return playerId;
    }

    public void setPlayerId(int playerId)
    {
        this.playerId = playerId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
