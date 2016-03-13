import java.awt.Point;

public class Tile {
	
	static final char UNKNOWN_CHAR        = 'u';
	static final char POTENTIAL_FOOD_CHAR = 'p';
	static final char FOOD_CHAR           = '+';
	static final char WALL_CHAR 		  = '*';
	static final char ROCK_CHAR           = '@';
	static final char DOOR_CHAR           = '#';
	static final char KEY_CHAR 			  = 'K';
	static final char HAMMER_CHAR         = 'T';
	static final char AGENT_CHAR          = '0';

	private int x;
	private int y;
	private TileValue v;
	
	public Tile(int x, int y, TileValue v){
		this.x = x;
		this.y = y;
		this.v = v;
	}
	
	public Tile(Point p, TileValue v) {
		this.x = p.x;
		this.y = p.y;
		this.v = v;
	}
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		this.v = TileValue.UNKNOWN;
	}
	
	public Tile(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public boolean isSameLocation(Tile t) {
		if (this.x == t.x && this.y == t.y)
			return true;
		return false;
	}
	
	public boolean isSameLocation(MaedenAgent agent){
		return isSameLocation(agent.getTile());
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public TileValue getValue() {
		return this.v;
	}
	
	public Point getPoint(){
		return new Point(x, y);
	}
	
	public Point[] getAdjacentPoints() {
		//supposed to return north, east, south, west
		return new Point[] {
				new Point(this.x, this.y-1), new Point(this.x+1, this.y), 
				new Point(this.x, this.y+1), new Point(this.x-1, this.y)
		};
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y ){
		this.y = y;
	}
	
	public void setValue(TileValue v){
		this.v = v;
	}
	
	public void setPoint(Point p){
		this.x = p.x;
		this.y = p.y;
	}
	
	public Character getValueAsChar() {
		return (Character)getCharFromTileValue(this.v);
	}
	
	public char getCharFromTileValue(TileValue v){
		switch(v){
		case POTENTIAL_FOOD:
			return POTENTIAL_FOOD_CHAR;
		case UNKNOWN:
			return UNKNOWN_CHAR;
		case FOOD:
			return FOOD_CHAR;
		case WALL:
			return WALL_CHAR;
		case ROCK:
			return ROCK_CHAR;
		case DOOR:
			return DOOR_CHAR;
		case KEY:
			return KEY_CHAR;
		case HAMMER:
			return HAMMER_CHAR;
		case AGENT:
			return AGENT_CHAR;
		default:
			return ' ';
		}
	}

}
