package com.fklm.javachess;

import com.fklm.javachess.controller.BoardController;
import com.fklm.javachess.model.chessmen.King;
import com.fklm.javachess.model.chessmen.Piece;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class BoardStatus {
    private ArrayList<Space> whitePosition;
    private ArrayList<Space>blackPosition;
    private int checkBlack;
    private int checkWhite;
    private Space wKing;
    private Space bKing;
    public Space[][] space = BoardController.space;

    public BoardStatus(){
        this.checkBlack=0;
        this.checkWhite=0;
        this.whitePosition = new ArrayList<>();
        this.blackPosition = new ArrayList<>();
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(space[i][j].getPiece() != null) {
                    if (space[i][j].getPiece().getColor().equals(Player.WHITE)) {
                        whitePosition.add(space[i][j]);
                        if(space[i][j].getPiece().getType() == 5)
                            wKing = space[i][j];
                    }
                    else {
                        blackPosition.add(space[i][j]);
                        if(space[i][j].getPiece().getType() == 5)
                            bKing = space[i][j];
                    }
                }
            }
        }
    }


    public void updateStatus(Move move, Player player) throws IOException {
        Space start = space[move.getStartY()][move.getStartX()];
        Space destiny = space[move.getDestY()][move.getDestX()];

        if(start.getPiece() != null && start.getPiece().getType() == 5){
            if(start.getPiece().getColor().equals(Player.WHITE))
                this.wKing = destiny;
            else
                this.bKing = destiny;
        }
        if(player.equals(Player.WHITE)){
            if(destiny.getPiece() != null)
                blackPosition.remove(destiny);
            whitePosition.remove(start);
            whitePosition.add(destiny);
        }
        else{
            if(destiny.getPiece() != null)
                whitePosition.remove(destiny);
            blackPosition.remove(start);
            blackPosition.add(destiny);
        }
        int y = (player==Player.WHITE? 7 : 0);
        for(int j=0; j<8;j++){
            if(space[y][j].getPiece() != null && space[y][j].getPiece().getType() == 0 &&
                space[y][j].getPiece().getColor().equals(player)){
                FXMLLoader loader = new FXMLLoader(ChessApplication.class.getResource("pawn-promotion.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.showAndWait();
            }
        }
        updatePossPieces();
        if(isInCheck(player)) {
            if(player == Player.WHITE)
                checkWhite++;
            else
                checkBlack++;
            if (isMate(player,destiny)){
                String winner = (player == Player.WHITE? "Branco":"Preto");
                disableSpace();
                //BoardController.writer.close();
                System.out.println(winner + " Ganhou");
            }
        }
        else if(pat(player)){
            disableSpace();
            System.out.println("Empate");
        }
        if(checkWhite == 5 || checkBlack == 5){
            disableSpace();
            System.out.println("Empate");
        }
    }

    boolean pat(Player player){
        ArrayList<Space> test = (player.opponent() == Player.WHITE? whitePosition : blackPosition);
        for(Space space : test){
            if(!space.getPossPositions().isEmpty())
                return false;
        }
        return true;
    }


    void updatePossPieces(){
        for(int y=0; y<8; y++){
            for(int x=0; x<8; x++){
                space[y][x].clear();
                space[y][x].addWay(space,this);
                if(space[y][x].getPiece() != null && space[y][x].getPiece().getType() == 5)
                    kingsPos(space[y][x]);
            }
        }
    }

    public void kingsPos(Space kingSpace){
        King king= (King) kingSpace.getPiece();
        ArrayList<Space> kingPoss = new ArrayList<>();
        for(Space space : kingSpace.getPossPositions()) {
            kingPoss.add(space);
        }
        if(king.getColor().equals(Player.WHITE)){
            wKing = kingSpace;
            if(!kingSpace.getPossPositions().isEmpty()) {
                for (Space possKing : kingPoss) {
                    for (Space opPiece : blackPosition) {
                        Move move = new Move(opPiece.getPosition(),possKing.getPosition());
                        if (opPiece.getPiece().isLegalMove(move,this) && isntObstructe(move,opPiece.getPiece())) {
                            kingSpace.removePossPositions(possKing);
                            break;
                        }
                    }
                }
            }
        }
        else {
            bKing = kingSpace;
            if (!kingSpace.getPossPositions().isEmpty()){
                for (Space possKing : kingPoss) {
                    for (Space opPiece : whitePosition) {
                        Move move = new Move(opPiece.getPosition(),possKing.getPosition());
                        if (opPiece.getPiece().isLegalMove(move,this) && isntObstructe(move,opPiece.getPiece()) &&
                                opPiece.getPosition()!=possKing.getPosition()) {
                            kingSpace.removePossPositions(possKing);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void disableSpace(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                space[i][j].setDisable(true);
            }
        }
    }

    private boolean isMate(Player player,Space checking){
        Space king = (player == Player.WHITE? bKing : wKing);
        ArrayList<Space> enemyPoss = (player == Player.WHITE? blackPosition : whitePosition);
        ArrayList<Space> checkPath = new ArrayList<>();
        ArrayList<Space> possPieces = new ArrayList<>();
        Move move = new Move(checking.getPosition(),king.getPosition());
        if(king.getPossPositions().isEmpty()){
            int dirX = move.getXDif(),dirY= move.getYDif();
            int x,y;
            x = move.getStartX();
            y = move.getStartY();
            if(move.getXDif() != 0) {
                dirX = (Math.abs(move.getXDif())) / move.getXDif();
                x+= dirX;
            }
            if(move.getYDif() != 0){
                dirY = (Math.abs(move.getYDif()))/move.getYDif();
                y += dirY;
            }
            Position test = new Position(x,y);
            while (!test.equals(move.getDestiny()) && x < 8 && y < 8){
                checkPath.add(space[y][x]);
                test.setX(x += dirX);
                test.setY(y += dirY);
            }
            for(Space test1 : checkPath){
                for (Space test2 : enemyPoss){
                    if(test2.getPossPositions().contains(test1)){
                        return false;
                    }
                }
            }
            int flag=0;
            for(Space uncheck : enemyPoss){
                Move testMove = new Move(uncheck.getPosition(),checking.getPosition());
                if(isntObstructe(testMove,uncheck.getPiece()) && uncheck.getPiece().isLegalMove(testMove,this)){
                    if(uncheck.getPiece().getType() == 5){
                        for(Space secure: (player == Player.WHITE? whitePosition: blackPosition)){
                            Move testMove1 = new Move(secure.getPosition(),checking.getPosition());
                            if(isntObstructe(testMove1,secure.getPiece()) && secure.getPiece().isLegalMove(testMove1,this)) {
                                flag =1;
                                break;
                            }
                        }if(flag == 0)
                            possPieces.add(uncheck);
                    }
                    else
                        possPieces.add(uncheck);
                }
            }
            return possPieces.isEmpty();
        }
        return  (king.getPiece().getType() != 5);
    }

    public boolean isInCheck(Player player){
        for(Space space: (player.opponent() == Player.WHITE? blackPosition: whitePosition)){
            Move move = new Move(space.getPosition(),(player.opponent() ==Player.WHITE? wKing : bKing).getPosition());
            if(space.getPiece().isLegalMove(move,this) && isntObstructe(move,space.getPiece())) {
                System.out.println("CHECK NO REI " + player.opponent().toString().toUpperCase());
                return true;
            }
        }
        return false;
    }

    public boolean isntObstructe(Move move, Piece piece){
        if(piece.getType() == 2)
            return true;
        int dirX = move.getXDif(),dirY= move.getYDif();
        int x,y;
        x = move.getStartX();
        y = move.getStartY();
        if(move.getXDif() != 0) {
            dirX = (Math.abs(move.getXDif())) / move.getXDif();
            x+= dirX;
        }
        if(move.getYDif() != 0){
            dirY = (Math.abs(move.getYDif()))/move.getYDif();
            y += dirY;
        }
        Position test = new Position(x,y);
        while (!test.equals(move.getDestiny()) && x < 8 && y < 8) {
            if (space[y][x].getPiece() != null)
                return false;
            test.setX(x += dirX);
            test.setY(y += dirY);
        }
        return true;
    }

    public Space getwKing() {
        return wKing;
    }

    public Space getbKing() {
        return bKing;
    }
}
