package shared;

import com.google.gson.Gson;
import shared.in.MessageRegister;
import shared.out.MessagePlayersOut;
import shared.out.MessageStartGameOut;

public class MessageCreator
{
    private Gson gson = new Gson();

    public BaseMessage createResult(MessageOperation message){
        switch (message.getOperation()){

            case RECEIVE_REGISTER:
                try{
                    return gson.fromJson(message.getProperty(), MessageRegister.class);
                } catch (Exception e){
                    e.getMessage();
                }
                break;
            case SEND_PLAYERS:
                try{
                    return gson.fromJson(message.getProperty(), MessagePlayersOut.class);
                } catch (Exception e){
                    e.getMessage();
                }
                break;

            case START_GAME:
                try{
                    return gson.fromJson(message.getProperty(), MessageStartGameOut.class);
                } catch (Exception e){
                    e.getMessage();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected Value: " + message);
        }
        return null;
    }

    public MessageOperation createMessage(MessageOperationType operation, BaseMessage property){
        MessageOperation messageOperation = new MessageOperation();

        messageOperation.setProperty(gson.toJson(property));
        messageOperation.setOperation(operation);

        return messageOperation;
    }
}
