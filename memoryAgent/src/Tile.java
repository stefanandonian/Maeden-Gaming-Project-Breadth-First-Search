// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tile.java

import java.awt.Point;

public class Tile
{
	static final char UNKNOWN_CHAR = 117;
    static final char POTENTIAL_FOOD_CHAR = 112;
    static final char FOOD_CHAR = 43;
    static final char WALL_CHAR = 42;
    static final char ROCK_CHAR = 64;
    static final char DOOR_CHAR = 35;
    static final char KEY_CHAR = 75;
    static final char HAMMER_CHAR = 84;
    static final char AGENT_CHAR = 48;
    private int x;
    private int y;
    private TileValue v;

    public Tile(int i, int j, TileValue tilevalue)
    {
        x = i;
        y = j;
        v = tilevalue;
    }

    public Tile(Point point, TileValue tilevalue)
    {
        this.x = point.x;
        this.y = point.y;
        this.v = tilevalue;
    }

    public Tile(int i, int j)
    {
        this.x = i;
        this.y = j;
        v = TileValue.UNKNOWN;
    }

    public Tile(Point point)
    {
        this.x = point.x;
        this.y = point.y;
    }

    public boolean isSameLocation(Tile tile)
    {
        return x == tile.x && y == tile.y;
    }

    public boolean isSameLocation(MaedenAgent maedenagent)
    {
        return isSameLocation(maedenagent.getTile());
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public TileValue getValue()
    {
        return this.v;
    }

    public Point getPoint()
    {
        return new Point(this.x, this.y);
    }

    public Point[] getAdjacentPoints()
    {
        return (new Point[] {
            new Point(this.x, this.y - 1), new Point(this.x + 1, this.y), new Point(this.x, this.y + 1), new Point(this.x - 1, this.y)
        });
    }

    public void setX(int i)
    {
        this.x = i;
    }

    public void setY(int i)
    {
        this.y = i;
    }

    public void setValue(TileValue tilevalue)
    {
        this.v = tilevalue;
    }

    public void setPoint(Point point)
    {
        this.x = point.x;
        this.y = point.y;
    }

    public Character getValueAsChar()
    {
        return Character.valueOf(getCharFromTileValue(this.v));
    }

    public char getCharFromTileValue(TileValue tilevalue) {
    	switch(tilevalue){
        case POTENTIAL_FOOD: 
            return 'p';
        case UNKNOWN: 
            return 'u';
        case FOOD: 
            return '+';
        case WALL: 
            return '*';
        case ROCK: 
            return '@';
        case DOOR: 
            return '#';
        case KEY: 
            return 'K';
        case HAMMER: 
            return 'T';
        case AGENT: 
            return '0';
        default:
        	return ' ';
        }
    }

    public void print() {
    	System.out.print("["+this.x + "," + this.y+"]="+this.v);
    }
    
}
