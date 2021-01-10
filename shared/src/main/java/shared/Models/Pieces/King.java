package shared.Models.Pieces;

import shared.Enums.Col;
import shared.Enums.MoveType;
import shared.Models.Board;
import shared.Models.Coordinate;

import java.util.ArrayList;

public class King extends Piece
{
    public King(int posX, int posY, Col color, Board board)
    {
        super(posX, posY, color, board);
    }


    public Boolean validMoveTo(int posX, int posY)
    {
        return validMoveDefault(posX, posY) ? moveKing(posX, posY) : false;
    }

    public Boolean moveKing(int posX, int posY)
    {
        //TODO: Add Movement Logic
        return true;
    }

    public ArrayList<Coordinate> validMoves()
    {
        return validMovesKing();
    }

    public ArrayList<Coordinate> validMovesKing()
    {
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();

        kingStep(possibleMoves, 1, 0);   // RIGHT
        kingStep(possibleMoves, -1, 0);  // LEFt
        kingStep(possibleMoves, 0, 1);   // UP
        kingStep(possibleMoves, 0, -1);  //DOWN
        kingStep(possibleMoves, 1, 1);   // RIGHT UP
        kingStep(possibleMoves, 1, -1);  //RIGHT DOWN
        kingStep(possibleMoves, -1, 1);  // LEFT UP
        kingStep(possibleMoves, -1, -1); //LEFT DOWN

        return possibleMoves;
    }

    private void kingStep(ArrayList<Coordinate> possibleMoves, int hor, int ver)
    {
        if (!board.positionOutsideBounds(posX + hor, posY + ver)) {
            if (board.getPieceAt(posX + hor, posY + ver) == null) {

                if (isSafeToMove(posX + hor, posY + ver))
                    possibleMoves.add(new Coordinate(posX + hor, posY + ver));
                else
                    possibleMoves.add(new Coordinate(posX + hor, posY + ver, MoveType.INVALID));

            } else if (board.getPieceAt(posX + hor, posY + ver).getColor() != this.getColor()) {
                if (isSafeToMove(posX + hor, posY + ver))
                    possibleMoves.add(new Coordinate(posX + hor, posY + ver, MoveType.CAPTURE));
                else
                    possibleMoves.add(new Coordinate(posX + hor, posY + ver, MoveType.INVALID));
            }
        }
    }

    private Boolean isSafeToMove(int posX, int posY)
    {
        Col otherColor = this.getColor() == Col.BLACK ? Col.WHITE : Col.BLACK;
        King ghostKing = new King(posX, posY, this.getColor(), board);
        board.addPiece(ghostKing);

        for (Piece piece : board.getPiecesForColor(otherColor)) {

            //TODO: Fix king 1v1 infinite loop
            if (piece instanceof King) continue;

            for (Coordinate coord : piece.validMoves()) {
                if (coord.getX() == posX && coord.getY() == posY && coord.getMoveType() == MoveType.CAPTURE) {
                    board.removePiece(ghostKing);
                    return false;
                }
            }
        }

        board.removePiece(ghostKing);
        return true;
    }

    public Boolean isCheck(){
        return !isSafeToMove(this.posX, this.posY);
    }

    public Boolean isCheckmate(){
        if(isCheck()){
            return validMoves().size() <= 0;
        }
        return false;
    }
}
