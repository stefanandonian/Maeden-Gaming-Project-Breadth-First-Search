// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Move.java

import java.awt.Point;

public class Move
{

    public Move(int i, int j, relativeDirection relativeDirection)
    {
        x = i;
        y = j;
        rD = relativeDirection;
    }

    public Move(Point point, relativeDirection relativeDirection)
    {
        x = point.x;
        y = point.y;
        rD = relativeDirection;
    }

    public Move(Point point, char c)
    {
        x = point.x;
        y = point.y;
        rD = relativeDirectionFromChar(c);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Point getPoint()
    {
        return new Point(x, y);
    }

    public Tile getTile()
    {
        return new Tile(x, y);
    }

    public relativeDirection getRelativeDirection()
    {
        return rD;
    }

    public relativeDirection relativeDirectionFromChar(char c)
    {
        switch(c) {
        case FORWARD_CHAR: // 'n'
            return relativeDirection.FORWARD;
        case BACK_CHAR: // 's'
            return relativeDirection.BACK;
        case LEFT_CHAR: // 'w'
            return relativeDirection.LEFT;
        case RIGHT_CHAR:
            return relativeDirection.RIGHT;
        }
        return null;
    }
    
    public void print() {
    	System.out.print("Move "+ this.rD+"["+this.x+","+this.y+"]");
    }

    private int x;
    private int y;
    private relativeDirection rD;
    private static final char FORWARD_CHAR = 'f';
    private static final char BACK_CHAR    = 'b';
    private static final char LEFT_CHAR    = 'l';
    private static final char RIGHT_CHAR   = 'r';
}
