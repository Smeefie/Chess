package shared.Models.Pieces.SharedLogic;

import shared.Enums.Col;
import shared.Enums.MoveType;
import shared.Models.Board;
import shared.Models.Coordinate;
import shared.Models.Pieces.Piece;

import java.util.ArrayList;

public class SharedMovementLogic
{
    public ArrayList<Coordinate> straightLineMovement(Piece piece, Board board)
    {
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();

        int dir = piece.getColor() == Col.BLACK ? -1 : 1;

        //RIGHT
        for (int x = piece.getPosX() + 1; x < board.getWidth(); x++) {
            HorizontalMovement(piece, board, possibleMoves, x);
        }

        //LEFT
        for (int x = piece.getPosX() - 1; x >= 0; x--) {
            HorizontalMovement(piece, board, possibleMoves, x);
        }

        //UP
        for (int y = piece.getPosY() + 1; y < board.getHeight(); y++) {
            VerticalMovement(piece, board, possibleMoves, y);
        }

        //Down
        for (int y = piece.getPosY() - 1; y >= 0; y--) {
            VerticalMovement(piece, board, possibleMoves, y);
        }

        return possibleMoves;
    }

    public ArrayList<Coordinate> DiagonalLineMovement(Piece piece, Board board)
    {
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();

        //RIGHT UP
        for (int i = piece.getPosX() + 1; i < board.getWidth(); i++) {

            int diff = Math.abs(i - piece.getPosX());
            int hor = 1;
            int ver = 1;

            DiagonalMovement(piece, board, possibleMoves, diff, hor, ver);
        }

        //RIGHT DOWN
        for (int i = piece.getPosX() + 1; i < board.getWidth(); i++) {

            int diff = Math.abs(i - piece.getPosX());
            int hor = 1;
            int ver = -1;

            DiagonalMovement(piece, board, possibleMoves, diff, hor, ver);
        }

        //LEFT UP
        for (int i = piece.getPosX() - 1; i >= 0; i--) {

            int diff = Math.abs(i - piece.getPosX());
            int hor = -1;
            int ver = 1;

            DiagonalMovement(piece, board, possibleMoves, diff, hor, ver);
        }

        //LEFT DOWN
        for (int i = piece.getPosX() - 1; i >= 0; i--) {

            int diff = Math.abs(i - piece.getPosX());
            int hor = -1;
            int ver = -1;

            DiagonalMovement(piece, board, possibleMoves, diff, hor, ver);
        }

        return possibleMoves;
    }

    private void VerticalMovement(Piece piece, Board board, ArrayList<Coordinate> possibleMoves, int y)
    {
        if (!board.positionOutsideBounds(piece.getPosX(), y)
                && piece.validStraightTracing(piece, new Coordinate(piece.getPosX(), y))) {
            if (board.getPieceAt(piece.getPosX(), y) == null) {
                possibleMoves.add(new Coordinate(piece.getPosX(), y));
                //Capturing
            } else if (board.getPieceAt(piece.getPosX(), y).getColor() != piece.getColor()) {
                possibleMoves.add(new Coordinate(piece.getPosX(), y, MoveType.CAPTURE));
            }
        }
    }

    private void HorizontalMovement(Piece piece, Board board, ArrayList<Coordinate> possibleMoves, int x)
    {
        if (!board.positionOutsideBounds(x, piece.getPosY())
                && piece.validStraightTracing(piece, new Coordinate(x, piece.getPosY()))) {
            if (board.getPieceAt(x, piece.getPosY()) == null) {
                possibleMoves.add(new Coordinate(x, piece.getPosY()));
                //Capturing
            } else if (board.getPieceAt(x, piece.getPosY()).getColor() != piece.getColor()) {
                possibleMoves.add(new Coordinate(x, piece.getPosY(), MoveType.CAPTURE));
            }
        }
    }

    private void DiagonalMovement(Piece piece, Board board, ArrayList<Coordinate> possibleMoves, int diff, int hor, int ver)
    {
        Coordinate coord = new Coordinate(piece.getPosX() + (hor * diff), piece.getPosY() + (ver * diff));

        if (!board.positionOutsideBounds(coord.getX(), coord.getY())) {
            if (piece.validDiagonalTracing(piece, coord)) {
                if (board.getPieceAt(coord.getX(), coord.getY()) == null) {
                    possibleMoves.add(coord);
                } else if (board.getPieceAt(coord.getX(), coord.getY()).getColor() != piece.getColor()) {
                    possibleMoves.add(new Coordinate(coord.getX(), coord.getY(), MoveType.CAPTURE));
                }
            }
        }
    }
}
