package shared.Models.Pieces;

import shared.Enums.Col;
import shared.Models.Board;
import shared.Models.Coordinate;

import java.util.ArrayList;

public class Piece
{
    protected int posX;
    protected int posY;
    private Col color;

    protected Boolean hasMoved;
    protected Board board;

    public Piece(int posX, int posY, Col color, Board board)
    {
        this.posX = posX;
        this.posY = posY;
        this.hasMoved = false;
        this.color = color;
        this.board = board;
    }

    public Boolean validMoveTo(int posX, int posY)
    {
        return validMoveDefault(posX, posY);
    }


    protected Boolean validMoveDefault(int posX, int posY)
    {
        if (!board.positionOutsideBounds(posX, posY)) {
            Piece target = board.getPieceAt(posX, posY);

            if (target == null) return true;
            return target.getColor() == this.color;
        }
        return false;
    }

    //To be overwritten by child
    public ArrayList<Coordinate> validMoves()
    {
        return null;
    }

    public void moveTo(int posX, int posY)
    {
        //if (board.getPieceAt(posX, posY) == this) {
        //    board.removePiece(this);
        //}

        board.removePiece(this);

        this.posX = posX;
        this.posY = posY;

        board.addPiece(this);

        //TODO: Add Capturing Logic

        //board.addPiece(this);
        hasMoved = true;
    }

    public void capturePiece(){
        board.removePiece(this);
    }

    public Boolean validStraightTracing(Piece piece, Coordinate coordinate)
    {
        //Horizontal
        if (piece.getPosX() != coordinate.getX()) {
            //Moving Right
            if (piece.getPosX() < coordinate.getX()) {
                for (int x = piece.getPosX() + 1; x < coordinate.getX(); x++) {
                    if (board.getPieceAt(x, coordinate.getY()) != null) return false;
                }
                //Moving Left
            } else if (piece.getPosX() > coordinate.getX()) {
                for (int x = coordinate.getX(); x < piece.getPosX(); x++) {
                    if (board.getPieceAt(x, coordinate.getY()) != null) return false;
                }
            } else return false;
        }
        //Vertical
        else {
            //Moving Up
            if (piece.getPosY() < coordinate.getY()) {
                for (int y = piece.getPosY() + 1; y < coordinate.getY(); y++) {
                    if (board.getPieceAt(coordinate.getX(), y) != null) return false;
                }
                //Moving Down
            } else if (piece.getPosY() > coordinate.getY()) {
                for (int y = coordinate.getY(); y < piece.getPosY(); y++) {
                    if (board.getPieceAt(coordinate.getX(), y) != null) return false;
                }
            } else return false;
        }

        return true;
    }

    public Boolean validDiagonalTracing(Piece piece, Coordinate coordinate){
        int diff = Math.abs(piece.getPosX() - coordinate.getX());

        //RIGHT
        if(piece.getPosX() < coordinate.getX()){
            int hor = 1;
            return DiagonalUpAndDown(piece, coordinate, diff, hor);
        } else if(piece.getPosX() > coordinate.getX()){
            int hor = -1;
            return DiagonalUpAndDown(piece, coordinate, diff, hor);
        }

        return true;
    }

    private boolean DiagonalUpAndDown(Piece piece, Coordinate coordinate, int diff, int hor)
    {
        if(piece.getPosY() < coordinate.getY()){
            int ver = 1;

            for (int i = 1; i < diff; i++) {
                if(board.getPieceAt(piece.getPosX() + (hor * i), piece.getPosY() + (ver * i)) != null){
                    return false;
                }
            }
            //DOWN
        } else if (piece.getPosY() > coordinate.getY()){
            int ver = -1;

            for (int i = 1; i < diff; i++) {
                if(board.getPieceAt(piece.getPosX() + (hor * i), piece.getPosY() + (ver * i)) != null){
                    return false;
                }
            }
        }
        return true;
    }

    public Col getColor()
    {
        return color;
    }

    public int getPosX()
    {
        return posX;
    }

    public int getPosY()
    {
        return posY;
    }
}
