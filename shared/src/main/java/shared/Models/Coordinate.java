package shared.Models;

import shared.Enums.MoveType;

public class Coordinate
{
    private final int x;
    private final int y;
    private final MoveType moveType;

    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.moveType = MoveType.MOVE;
    }

    public Coordinate(int x, int y, MoveType moveType)
    {
        this.x = x;
        this.y = y;
        this.moveType = moveType;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public MoveType getMoveType()
    {
        return moveType;
    }

    @Override
    public String toString()
    {
        return "[" + x + ", " + y + "]";
    }
}
