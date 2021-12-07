package com.fklm.javachess;

public class Position {
    private int x,y;

    public Position(int x,int y){
        this.x = x;
        this.y = y;
    }

    private static final int convetToInt(char c){
        if(!Character.isAlphabetic(c)){
            return -1;
        }

        if(c>= 97)
            return c - 97;
        else
            return c - 65;
    }

    public boolean equals(Position position){
        return (position.getY() == this.y) && (position.getX() == this.x);
    }

    public int getX() {return x;}

    public void setX(int x) {this.x = x;}

    public int getY() {return y;}

    public void setY(int y) {this.y = y;}
}
