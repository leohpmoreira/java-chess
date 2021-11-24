package com.fklm.javachess;

public class Move {
    private final Position start,destiny;

    public Move(Position start, Position destiny){
        this.start = start;
        this.destiny = destiny;
    }

    public boolean vertical(){
        return start.getX() == destiny.getX();
    }

    public boolean horizontal(){
        return start.getY() == destiny.getY();
    }

    public boolean diagonal(){
        return Math.abs(destiny.getX() - start.getX()) == Math.abs(destiny.getY() - start.getY());
    }

    public boolean lMove(){
        int difX,difY;
        difX = Math.abs(destiny.getX() - start.getX());
        difY = Math.abs(destiny.getY() - start.getY());
        return (difX==1 && difY==2) || (difY==1 && difX==2);
    }

    public Position getStart() {
        return start;
    }

    public Position getDestiny() {
        return destiny;
    }
}
