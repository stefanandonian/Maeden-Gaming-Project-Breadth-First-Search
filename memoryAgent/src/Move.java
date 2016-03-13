import java.awt.Point;

public class Move {

	private int x;
	private int y;
	private compassDirection cD;
	
	private static final char NORTH_CHAR = 'n';
	private static final char SOUTH_CHAR = 's';
	private static final char WEST_CHAR  = 'w';
	private static final char EAST_CHAR  = 'e';

	public Move(int x, int y, compassDirection cD) {
		this.x = x;
		this.y = y;
		this.cD = cD;
	}
	
	public Move(Point p, compassDirection cD) {
		this.x = p.x;
		this.y = p.y;
		this.cD = cD;
	}
	
	public Move(Point p, char c) {
		this.x  = p.x;
		this.y  = p.y;
		this.cD = compassDirectionFromChar(c); 
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Tile getTile(){
		return new Tile(this.x, this.y);
	}
	
	public compassDirection getCompassDirection(){
		return this.cD;
	}
	
	public compassDirection compassDirectionFromChar(char c) {
		switch(c) {
		case NORTH_CHAR:
			return compassDirection.NORTH;
		case SOUTH_CHAR:
			return compassDirection.SOUTH;
		case WEST_CHAR:
			return compassDirection.WEST;
		case EAST_CHAR:
			return compassDirection.EAST;
		default:
			return null;
		}
	}
		
}
