package communicatorserver.Models;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class Players
{
    private ArrayList<Player> playerList = new ArrayList<>();

    public void addPlayer(Player player){
        playerList.add(player);
    }

    public Player getPlayer(String id) {
        return playerList.get(Integer.parseInt(id));
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public Player getPlayerBySession(Session session) {

        for (Player player : playerList) {
            if (player.getSession().equals(session)){
                return player;
            }
        }
        return null;
    }
}
