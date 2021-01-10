package shared.Models.Pieces;

import shared.Enums.Col;
import shared.Enums.MoveType;
import shared.Models.Board;
import shared.Models.Coordinate;

import java.util.ArrayList;

public class Pawn extends Piece
{

    public Pawn(int posX, int posY, Col col, Board board)
    {
        super(posX, posY, col, board);
    }


    public Boolean validMoveTo(int posX, int posY)
    {
        return validMoveDefault(posX, posY) ? movePawn(posX, posY) : false;
    }

    public Boolean movePawn(int posX, int posY)
    {
        //TODO: Add Movement Logic
        return true;
    }

    public ArrayList<Coordinate> validMoves()
    {
        return validMovesPawn();
    }

    public ArrayList<Coordinate> validMovesPawn()
    {
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();

        int dir = this.getColor() == Col.BLACK ? -1 : 1;

        //STRAIGHT
        if (board.getPieceAt(posX, posY + dir) == null
                && !board.positionOutsideBounds(posX, posY + dir)) {
            possibleMoves.add(new Coordinate(posX, posY + dir));
        }

        if (!hasMoved) {
            if (board.getPieceAt(posX, posY + (2 * dir)) == null
                    && !board.positionOutsideBounds(posX, posY + (2 * dir))) {
                if (validStraightTracing(this, new Coordinate(posX, posY + (2 * dir)))) {
                    possibleMoves.add(new Coordinate(posX, posY + (2 * dir)));
                }
            }
        }

        //DIAGONALLY (CAPTURING)
        if (!board.positionOutsideBounds(posX - 1, posY + dir)) {
            if (board.getPieceAt(posX - 1, posY + dir) != null) {
                if (board.getPieceAt(posX - 1, posY + dir).getColor() != this.getColor()) {
                    possibleMoves.add(new Coordinate(posX - 1, posY + dir, MoveType.CAPTURE));
                }
            }
        }

        if (!board.positionOutsideBounds(posX + 1, posY + dir)) {
            if (board.getPieceAt(posX + 1, posY + dir) != null) {
                if (board.getPieceAt(posX + 1, posY + dir).getColor() != this.getColor()) {
                    possibleMoves.add(new Coordinate(posX + 1, posY + dir, MoveType.CAPTURE));
                }
            }
        }

        return possibleMoves;
    }
}
