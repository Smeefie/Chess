package shared.Models.Pieces;

import shared.Enums.Col;
import shared.Enums.MoveType;
import shared.Models.Board;
import shared.Models.Coordinate;

import java.util.ArrayList;

public class Knight extends Piece
{
    public Knight(int posX, int posY, Col color, Board board)
    {
        super(posX, posY, color, board);
    }

    public Boolean validMoveTo(int posX, int posY)
    {
        return validMoveDefault(posX, posY) ? moveKnight(posX, posY) : false;
    }

    public Boolean moveKnight(int posX, int posY)
    {
        //TODO: Add Movement Logic
        return true;
    }

    public ArrayList<Coordinate> validMoves()
    {
        return validMovesKnight();
    }

    public ArrayList<Coordinate> validMovesKnight()
    {
        ArrayList<Coordinate> moves = new ArrayList<>();

        knightStep(moves, 2, 1); //RIGHT
        knightStep(moves, 2, -1);

        knightStep(moves, -2, 1); //LEFT
        knightStep(moves, -2, -1);

        knightStep(moves, 1, 2); //UP
        knightStep(moves, -1, 2);

        knightStep(moves, 1, -2); //DOWN
        knightStep(moves, -1, -2);

        return moves;
    }

    private void knightStep(ArrayList<Coordinate> possibleMoves, int hor, int ver)
    {
        if (!board.positionOutsideBounds(posX + hor, posY + ver)) {
            if (board.getPieceAt(posX + hor, posY + ver) == null) {

                possibleMoves.add(new Coordinate(posX + hor, posY + ver));

            } else if (board.getPieceAt(posX + hor, posY + ver).getColor() != this.getColor()) {

                possibleMoves.add(new Coordinate(posX + hor, posY + ver, MoveType.CAPTURE));
            }
        }
    }
}
