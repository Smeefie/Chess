package shared.Models.Pieces;

import shared.Enums.Col;
import shared.Models.Board;
import shared.Models.Coordinate;
import shared.Models.Pieces.SharedLogic.SharedMovementLogic;

import java.util.ArrayList;

public class Bishop extends Piece
{
    private final SharedMovementLogic sharedMovementLogic = new SharedMovementLogic();

    public Bishop(int posX, int posY, Col color, Board board)
    {
        super(posX, posY, color, board);
    }

    public Boolean validMoveTo(int posX, int posY)
    {
        return validMoveDefault(posX, posY) ? moveBishop(posX, posY) : false;
    }

    public Boolean moveBishop(int posX, int posY)
    {
        //TODO: Add Movement Logic
        return true;
    }

    public ArrayList<Coordinate> validMoves()
    {
        return validMovesBishop();
    }

    public ArrayList<Coordinate> validMovesBishop()
    {
        return sharedMovementLogic.DiagonalLineMovement(this, board);
    }
}
