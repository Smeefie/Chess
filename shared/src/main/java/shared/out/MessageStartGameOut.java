package shared.out;

import shared.BaseMessage;
import shared.Enums.Col;
import shared.Models.Board;

public class MessageStartGameOut extends BaseMessage
{
    private Col color;
    private Board board;

    public Col getColor()
    {
        return color;
    }

    public void setColor(Col color)
    {
        this.color = color;
    }

    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }
}
