package shared.Models.Pieces;

import shared.Enums.Col;
import shared.Models.Board;
import shared.Models.Coordinate;
import shared.Models.Pieces.SharedLogic.SharedMovementLogic;

import java.util.ArrayList;

public class Queen extends Piece
{
    private final SharedMovementLogic sharedMovementLogic = new SharedMovementLogic();

    public Queen(int posX, int posY, Col color, Board board)
    {
        super(posX, posY, color, board);
    }

    public Boolean validMoveTo(int posX, int posY)
    {
        return validMoveDefault(posX, posY) ? moveQueen(posX, posY) : false;
    }

    public Boolean moveQueen(int posX, int posY)
    {
        //TODO: Add Movement Logic
        return true;
    }

    public ArrayList<Coordinate> validMoves()
    {
        return validMovesQueen();
    }

    public ArrayList<Coordinate> validMovesQueen()
    {
        ArrayList<Coordinate> moves = new ArrayList<>();
        moves.addAll(sharedMovementLogic.straightLineMovement(this, board));
        moves.addAll(sharedMovementLogic.DiagonalLineMovement(this, board));
        return moves;
    }
}
