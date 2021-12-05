package com.fklm.javachess;

import com.fklm.javachess.controller.BoardController;
import com.fklm.javachess.model.chessmen.King;
import com.fklm.javachess.model.chessmen.Piece;

import java.util.ArrayList;

public class BoardStatus {
    private ArrayList<Space> whitePosition;
    private ArrayList<Space>blackPosition;
    private Space wKing;
    private Space bKing;
    private Space[][] space = BoardController.space;

    public BoardStatus(){
        this.whitePosition = new ArrayList<Space>();
        this.blackPosition = new ArrayList<Space>();
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

    public void updateStatus(Move move,Player player){
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
        updatePossPieces();
        kingsPos(player);
        if(isInCheck(player)) {
            if (isMate(player,destiny)){
                String winner = new String(player == Player.WHITE? "Branco":"Preto");
                System.out.println(winner + " Ganhou");
            }
        }
    }

    void updatePossPieces(){
        for(int y=0; y<8; y++){
            for(int x=0; x<8; x++){
                space[y][x].clear();
                space[y][x].addWay(space,this);
            }
        }
    }

    public void kingsPos(Player player){
        Space king = (player.opponent() == Player.WHITE? wKing : bKing);
        for(Space space: (player ==Player.WHITE? whitePosition : blackPosition)){
            for(Space poss : space.getPossPositions()){
                if(king.getPossPositions().contains(poss))
                    king.removePossPositions(poss);
            }
        }
    }

    private boolean isMate(Player player,Space checking){
        Space king = (player == Player.WHITE? bKing : wKing);
        ArrayList<Space> enemyPoss = (player == Player.WHITE? blackPosition : whitePosition);
        ArrayList<Space> checkPath = new ArrayList<>();
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
            return true;
        }
        return false;
    }

    public boolean isInCheck(Player player){
        for(Space space: (player.opponent() == Player.WHITE? blackPosition: whitePosition)){
            Move move = new Move(space.getPosition(),(player.opponent() ==Player.WHITE? wKing : bKing).getPosition());
            if(space.getPiece().isLegalMove(move) && isntObstructe(move,space.getPiece())) {
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
        if(space[move.getDestY()][move.getDestX()].getPiece()== null ||
            !space[move.getDestY()][move.getDestX()].getPiece().getColor().equals(piece.getColor())) {
            while (!test.equals(move.getDestiny()) && x < 8 && y < 8) {
                if (space[y][x].getPiece() != null)
                    return false;
                test.setX(x += dirX);
                test.setY(y += dirY);
            }
            return true;
        }
        else{
            return false;
        }
    }
}
