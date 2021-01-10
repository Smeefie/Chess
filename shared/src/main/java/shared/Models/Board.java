package shared.Models;

import shared.Enums.Col;
import shared.Models.Pieces.*;

import java.util.ArrayList;

public class Board
{
    private final int width;
    private final int height;

    private Piece[][] pieces;

    public Board(int width, int height)
    {
        this.width = width;
        this.height = height;

        pieces = new Piece[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pieces[x][y] = null;
            }
        }
    }

    public void populateBoard()
    {
        addPiece(new Rook(0, 0, Col.WHITE, this));
        addPiece(new Knight(1, 0, Col.WHITE, this));
        addPiece(new Bishop(2, 0, Col.WHITE, this));
        addPiece(new King(3, 0, Col.WHITE, this));
        addPiece(new Queen(4, 0, Col.WHITE, this));
        addPiece(new Bishop(5, 0, Col.WHITE, this));
        addPiece(new Knight(6, 0, Col.WHITE, this));
        addPiece(new Rook(7, 0, Col.WHITE, this));

        addPiece(new Pawn(0, 1, Col.WHITE, this));
        addPiece(new Pawn(1, 1, Col.WHITE, this));
        addPiece(new Pawn(2, 1, Col.WHITE, this));
        addPiece(new Pawn(3, 1, Col.WHITE, this));
        addPiece(new Pawn(4, 1, Col.WHITE, this));
        addPiece(new Pawn(5, 1, Col.WHITE, this));
        addPiece(new Pawn(6, 1, Col.WHITE, this));
        addPiece(new Pawn(7, 1, Col.WHITE, this));

        addPiece(new Pawn(0, 6, Col.BLACK, this));
        addPiece(new Pawn(1, 6, Col.BLACK, this));
        addPiece(new Pawn(2, 6, Col.BLACK, this));
        addPiece(new Pawn(3, 6, Col.BLACK, this));
        addPiece(new Pawn(4, 6, Col.BLACK, this));
        addPiece(new Pawn(5, 6, Col.BLACK, this));
        addPiece(new Pawn(6, 6, Col.BLACK, this));
        addPiece(new Pawn(7, 6, Col.BLACK, this));

        addPiece(new Rook(0, 7, Col.BLACK, this));
        addPiece(new Knight(1, 7, Col.BLACK, this));
        addPiece(new Bishop(2, 7, Col.BLACK, this));
        addPiece(new Queen(3, 7, Col.BLACK, this));
        addPiece(new King(4, 7, Col.BLACK, this));
        addPiece(new Bishop(5, 7, Col.BLACK, this));
        addPiece(new Knight(6, 7, Col.BLACK, this));
        addPiece(new Rook(7, 7, Col.BLACK, this));
    }

    public void addPiece(Piece piece)
    {
        pieces[piece.getPosX()][piece.getPosY()] = piece;
    }

    public Piece[][] getPieces()
    {
        return pieces;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void removePiece(Piece piece)
    {
        pieces[piece.getPosX()][piece.getPosY()] = null;
    }

    public Piece getPieceAt(int posX, int posY)
    {
        return pieces[posX][posY];
    }

    public ArrayList<Piece> getPiecesForColor(Col color)
    {
        ArrayList<Piece> returnList = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (pieces[x][y] != null) {
                    if (pieces[x][y].getColor() == color) returnList.add(pieces[x][y]);
                }
            }
        }
        return returnList;
    }

    public Boolean positionOutsideBounds(int posX, int posY)
    {
        if (posX < width && posX >= 0 && posY < height && posY >= 0) return false;
        return true;
    }

    public Boolean PossitionOccupied(int posX, int posY)
    {
        return pieces[posX][posY] != null;
    }

    public void drawBoard()
    {
        StringBuilder finalString = new StringBuilder();
        finalString.append("   0  1  2  3  4  5  6  7").append( "\n");


        for (int x = 0; x < width; x++) {
            StringBuilder xString = new StringBuilder();
            xString.append(x).append(" ");

            for (int y = 0; y < height; y++) {
                if (pieces[y][x] != null) {
                    xString.append(" ").append(pieces[y][x].getClass().getSimpleName().charAt(0));
                    if (pieces[y][x].getColor() == Col.BLACK) {
                        xString.append("b");
                    } else {
                        xString.append("w");
                    }
                } else {
                    xString.append(" --");
                }
            }

            finalString.append(xString).append( "\n");
        }

        System.out.println(finalString);
    }
}
