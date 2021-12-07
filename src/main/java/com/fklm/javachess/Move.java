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

    public int getDistance(){
        int xDif = Math.abs(destiny.getX() - start.getX());
        int yDif = Math.abs(destiny.getY() - start.getY());
        return Math.max(xDif,yDif);
    }

    public String translate(){
        char letter;
        letter = (char) (this.getDestX() + 65);
        return (letter + "-" + this.getDestY()+1);
    }

    public int getXDif(){
        return destiny.getX() - start.getX();
    }

    public int getYDif(){
        return destiny.getY() - start.getY();
    }

    public Position getStart() {
        return start;
    }
    public int getStartX(){
        return start.getX();
    }
    public int getStartY(){
        return start.getY();
    }

    public Position getDestiny() {
        return destiny;
    }
    public int getDestX(){return destiny.getX();}
    public int getDestY(){return destiny.getY();}
}
