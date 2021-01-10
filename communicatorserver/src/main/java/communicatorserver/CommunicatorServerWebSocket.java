package communicatorserver;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import shared.Enums.Col;
import shared.Models.Board;
import communicatorserver.Models.Player;
import communicatorserver.Models.Players;
import shared.BaseMessage;
import shared.MessageCreator;
import shared.MessageOperation;
import shared.MessageOperationType;
import shared.in.MessageRegister;
import shared.out.MessagePlayersOut;
import shared.out.MessageStartGameOut;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;


@ServerEndpoint(value = "/communicator/")
public class CommunicatorServerWebSocket
{

    private static final Board chessBoard = new Board(8, 8);
    private static final Players players = new Players();
    private static final Gson gson = new Gson();
    private static Boolean started = false;

    // All sessions
    private static final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onConnect(Session session)
    {
        System.out.println("[WebSocket Connected] SessionID: " + session.getId());
        String message = String.format("[New client with client side session ID]: %s", session.getId());
        sessions.add(session);
        System.out.println("[#sessions]: " + sessions.size());
    }

    @OnMessage
    public void onText(String message, Session session)
    {
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Received] : " + message);
        handleMessageFromClient(message, session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session)
    {
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Socket Closed]: " + reason);
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable cause, Session session)
    {
        System.out.println("[WebSocket Session ID] : " + session.getId() + "[ERROR]: ");
        cause.printStackTrace(System.err);
    }

    // Handle incoming message from client
    private void handleMessageFromClient(String jsonMessage, Session session)
    {
        MessageOperation wbMessage = null;
        try {
            wbMessage = gson.fromJson(jsonMessage, MessageOperation.class);
        } catch (JsonSyntaxException ex) {
            System.out.println("[WebSocket ERROR: cannot parse Json message " + jsonMessage);
            return;
        }

        // Operation defined in message
        BaseMessage message;
        MessageCreator messageCreator = new MessageCreator();
        message = messageCreator.createResult(wbMessage);

        // Process message based on operation
        String property = wbMessage.getProperty();
        if (null != wbMessage.getOperation()) {
            switch (wbMessage.getOperation()) {
                case RECEIVE_REGISTER:
                    MessageRegister messageRegister = (MessageRegister) message;
                    players.addPlayer(new Player(messageRegister.getUsername(), session));

                    MessagePlayersOut messagePlayersOut = new MessagePlayersOut();
                    messagePlayersOut.setPlayerId(Integer.parseInt(players.getPlayerBySession(session).getSession().getId()));

                    session.getAsyncRemote().sendText(gson.toJson(messageCreator.createMessage(MessageOperationType.SEND_PLAYERS, messagePlayersOut)));

                    if (players.getPlayerList().size() >= 2) startGame();
                    break;
//                case UNREGISTERPROPERTY:
//                    // Do nothing as property may also have been registered by
//                    // another client
//                    break;
//                case SUBSCRIBETOPROPERTY:
//                    // Subsribe to property if the property has been registered
//                    if (propertySessions.get(property) != null) {
//                        propertySessions.get(property).add(session);
//                    }
//                    break;
//                case UNSUBSCRIBEFROMPROPERTY:
//                    // Unsubsribe from property if the property has been registered
//                    if (propertySessions.get(property) != null) {
//                        propertySessions.get(property).remove(session);
//                    }
//                    break;
                default:
                    System.out.println("[WebSocket ERROR: cannot process Json message " + jsonMessage);
                    break;
            }
        }
    }

    private void startGame()
    {
        if (!started) {
            int col = 0;
            chessBoard.populateBoard();
            MessageCreator messageCreator = new MessageCreator();

            for (Player player : players.getPlayerList()) {
                Session sess = player.getSession();

                MessageStartGameOut messageStartGameOut = new MessageStartGameOut();
                messageStartGameOut.setColor(col == 0 ? Col.BLACK : Col.WHITE);
                messageStartGameOut.setBoard(chessBoard);

                sess.getAsyncRemote().sendText(gson.toJson(messageCreator.createMessage(MessageOperationType.START_GAME, messageStartGameOut)));
                col++;
            }

            started = true;
        }
    }
}