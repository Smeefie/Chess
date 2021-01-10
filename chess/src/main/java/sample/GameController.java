package sample;

import shared.Enums.Col;
import shared.Enums.MoveType;
import shared.Models.Board;
import shared.Models.Coordinate;
import shared.Models.Pieces.King;
import shared.Models.Pieces.Piece;
import shared.MessageCreator;
import shared.MessageOperation;
import shared.out.MessagePlayersOut;

import Models.Player;

import client.Communicator;
import client.CommunicatorClientWebSocket;
import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import shared.out.MessageStartGameOut;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameController implements Observer
{
    @FXML
    public GridPane gridPane;
    @FXML
    private TextField usernameTextfield;

    //CLIENT STUFF
    public static Player PLAYER;
    public static Col COLOR;

    public Gson gson = new Gson();
    public Communicator communicator = null;

    private Board board = new Board(8, 8);
    private ArrayList<StackPane> hightlights = new ArrayList<>();
    private Coordinate clickedCoord = null;
    private Col turn = Col.WHITE;

    @FXML
    public void setupButtonClicked(ActionEvent actionEvent)
    {
        setupChessBoard();
    }

    private void setupChessBoard()
    {
        board.populateBoard();
        Piece[][] pieces = board.getPieces();
        String baseImgUrl = "https://raw.githubusercontent.com/GuiBon/ChessGame/master/src/ChessPiece/";

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {

                if (pieces[x][y] == null) continue;

                ImageView iv = new ImageView();
                iv.setFitWidth(50);
                iv.setFitHeight(50);
                iv.setDisable(true);


                switch (pieces[x][y].getClass().getSimpleName()) {
                    default:
                        System.out.println("Found Nothing");
                        break;
                    case "Pawn":
                        if (pieces[x][y].getColor() == Col.BLACK)
                            iv.setImage(new Image(baseImgUrl + "Black_Pawn.png"));
                        else
                            iv.setImage(new Image(baseImgUrl + "White_Pawn.png"));
                        break;
                    case "Rook":
                        if (pieces[x][y].getColor() == Col.BLACK)
                            iv.setImage(new Image(baseImgUrl + "Black_Rook.png"));
                        else
                            iv.setImage(new Image(baseImgUrl + "White_Rook.png"));
                        break;
                    case "Bishop":
                        if (pieces[x][y].getColor() == Col.BLACK)
                            iv.setImage(new Image(baseImgUrl + "Black_Bishop.png"));
                        else
                            iv.setImage(new Image(baseImgUrl + "White_Bishop.png"));
                        break;
                    case "Knight":
                        if (pieces[x][y].getColor() == Col.BLACK)
                            iv.setImage(new Image(baseImgUrl + "Black_Knight.png"));
                        else
                            iv.setImage(new Image(baseImgUrl + "White_Knight.png"));
                        break;
                    case "King":
                        if (pieces[x][y].getColor() == Col.BLACK)
                            iv.setImage(new Image(baseImgUrl + "Black_King.png"));
                        else
                            iv.setImage(new Image(baseImgUrl + "White_King.png"));
                        break;
                    case "Queen":
                        if (pieces[x][y].getColor() == Col.BLACK)
                            iv.setImage(new Image(baseImgUrl + "Black_Queen.png"));
                        else
                            iv.setImage(new Image(baseImgUrl + "White_Queen.png"));
                        break;
                }

                gridPane.add(iv, x, y);
            }
        }
    }

    private ImageView getPieceAt(int posX, int posY)
    {
        for (Node child : gridPane.getChildren()) {
            if (child instanceof ImageView) {
                if (GridPane.getColumnIndex(child) == posX && GridPane.getRowIndex(child) == posY) {
                    return (ImageView) child;
                }
            }
        }
        return null;
    }

    private void removePieceAt(int posX, int posY)
    {
        for (Node child : gridPane.getChildren()) {
            if (child instanceof ImageView) {
                if (GridPane.getColumnIndex(child) == posX && GridPane.getRowIndex(child) == posY) {
                    gridPane.getChildren().remove(child);
                    return;
                }
            }
        }
    }

    private void movePiece(int originX, int originY, int targetX, int targetY, Piece piece)
    {
        //MOVE THE FXML IMAGE
        ImageView image = getPieceAt(originX, originY);
        removePieceAt(originX, originY);
        gridPane.add(image, targetX, targetY);
        //MOVE THE BACKEND PIECE
        piece.moveTo(targetX, targetY);
        //CHANGE THE TURN
        changeTurn();
    }

    private void highlightSquare(int posX, int posY, MoveType type)
    {
        for (Node child : gridPane.getChildren()) {
            if (child instanceof StackPane) {
                if (GridPane.getColumnIndex(child) == posX && GridPane.getRowIndex(child) == posY) {

                    Color col;
                    switch (type) {
                        default:
                        case MOVE:
                            col = Color.GREEN;
                            break;

                        case CAPTURE:
                            col = Color.ORANGE;
                            break;

                        case INVALID:
                            col = Color.DARKRED;
                            break;

                        case SENDER:
                            col = Color.YELLOW;
                            break;
                    }

                    ((StackPane) child).setBorder(new Border(new BorderStroke(col,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.5))));
                    hightlights.add((StackPane) child);
                }
            }
        }
    }

    private void clearHighlights()
    {
        for (StackPane hightlight : hightlights) {
            hightlight.setBorder(null);
        }
    }

    private void executeTurn(int originX, int originY, int targetX, int targetY)
    {
        Piece piece = board.getPieceAt(originX, originY);
        if (piece != null) {
            for (Coordinate validMove : piece.validMoves()) {
                if (validMove.getX() == targetX && validMove.getY() == targetY) {

                    switch (validMove.getMoveType()){
                        default:
                        case INVALID:
                            break;

                        case MOVE:
                            movePiece(originX, originY, targetX, targetY, piece);
                            break;

                        case CAPTURE:
                            Piece capturePiece = board.getPieceAt(targetX, targetY);
                            removePieceAt(targetX, targetY);
                            capturePiece.capturePiece();

                            movePiece(originX, originY, targetX, targetY, piece);
                            break;
                    }
                }
            }
        }
    }

    @FXML
    public void gridClicked(MouseEvent mouseEvent)
    {
        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
        if (clickedNode != gridPane) {
            int colIndex = GridPane.getColumnIndex(clickedNode);
            int rowIndex = GridPane.getRowIndex(clickedNode);

            clearHighlights();

            Piece piece = board.getPieceAt(colIndex, rowIndex);

            if (piece != null)
            {
                if(piece.getColor() == turn){
                    //ADD CLICKED COORDINATE
                    clickedCoord = new Coordinate(colIndex, rowIndex);

                    //CREATE HIGHLIGHTS
                    highlightSquare(colIndex, rowIndex, MoveType.SENDER);
                    for (Coordinate validMove : piece.validMoves()) {
                        highlightSquare(validMove.getX(), validMove.getY(), validMove.getMoveType());
                    }
                } else if(clickedCoord != null){
                    //CAPTURE A PIECE
                    executeTurn(clickedCoord.getX(), clickedCoord.getY(), colIndex, rowIndex);
                    clickedCoord = null;
                }
            } else {
                if (clickedCoord != null) {
                    //MOVE A PIECE
                    executeTurn(clickedCoord.getX(), clickedCoord.getY(), colIndex, rowIndex);
                    clickedCoord = null;
                }
            }
        }
    }

    private void changeTurn()
    {
        turn = turn == Col.WHITE ? Col.BLACK : Col.WHITE;
    }

    @FXML
    public void connectToServer(ActionEvent actionEvent)
    {
        String username = usernameTextfield.getText();
        PLAYER = new Player(username);;

        communicator = CommunicatorClientWebSocket.getInstance();
        communicator.addObserver(this);
        communicator.start();

        communicator.register(username);
        System.out.println("CONNECTED");
    }


    @Override
    public void update(Observable o, Object arg)
    {
        MessageOperation message = (MessageOperation) arg;
        MessageCreator messageCreator = new MessageCreator();

        switch(message.getOperation()){
            case SEND_PLAYERS:

                MessagePlayersOut messagePlayersOut = (MessagePlayersOut) messageCreator.createResult(message);
                PLAYER.setPlayerId(messagePlayersOut.getPlayerId());
                System.out.println("PLAYER WITH ID: " + PLAYER.getPlayerId() + " CONNECTED");
                break;

            case START_GAME:

                MessageStartGameOut messageStartGameOut = (MessageStartGameOut) messageCreator.createResult(message);
                board = messageStartGameOut.getBoard();
                System.out.println("YOU ARE " + messageStartGameOut.getColor());
                break;

            default:
                break;
        }
    }
}
