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
        if(isInCheck(player)) {
            //if (isMate(player)){

                String winner = new String(player == Player.WHITE? "Branco":"Preto");
                System.out.println(winner + " Ganhou");
            //}
        }
    }

    /*public boolean isMate(Player player){
        ArrayList<Position> possiblePlMov = new ArrayList<Position>();
        ArrayList<Position> possibleKingMove = new ArrayList<Position>();
        Space king = (player.opponent() == Player.WHITE? wKing : bKing);
        int startX = king.getPosition().getX(),startY = king.getPosition().getY();
        for(int i= startY-1; i<=startY+1; i++){
            for(int j=startX-1; i<=startX+1; j++){
                Position destiny = new Position(j,i);
                Move move = new Move(new Position(startX,startY),destiny);
                if(king.getPiece().isLegalMove(move))
                    possibleKingMove.add(destiny);
            }
        }
        for(Space space: (player ==Player.WHITE? ))
    }*/

    public boolean isInCheck(Player player){
        if(player.opponent().equals(Player.WHITE)){
            for(Space space: blackPosition){
                Move move = new Move(space.getPosition(),wKing.getPosition());
                if(space.getPiece().isLegalMove(move) && isntObstructe(move,space.getPiece())) {
                    System.out.println("CHECK NO REI BRANCO");
                    return true;
                }
            }
            return false;
        }
        else{
            for(Space space: whitePosition){
                Move move = new Move(space.getPosition(),bKing.getPosition());
                if(space.getPiece().isLegalMove(move) && isntObstructe(move,space.getPiece())) {
                    System.out.println("CHECK NO REI PRETO");
                    return true;
                }
            }
            return false;
        }
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
            x += dirX;
        }
        if(move.getYDif() != 0){
            dirY = (Math.abs(move.getYDif()))/move.getYDif();
            y += dirY;
        }
        Position test = new Position(x,y);
        while(!test.equals(move.getDestiny()) && x<8 && y<8){
            if(space[y][x].getPiece() != null)
                return false;
            test.setX(x += dirX);
            test.setY(y += dirY);
        }
        return true;
    }
}
