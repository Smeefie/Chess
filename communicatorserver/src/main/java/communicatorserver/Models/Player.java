package communicatorserver.Models;

import javax.websocket.Session;

public class Player
{
    private String username;

    private Session session;

    public Player(String username, Session session)
    {
        this.username = username;
        this.session = session;
    }

    public Player(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public Session getSession()
    {
        return session;
    }
}
