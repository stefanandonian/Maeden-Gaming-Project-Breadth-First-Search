import java.awt.Point;

enum compassDirection {
		NORTH,
		SOUTH,
		EAST,
		WEST
	}

enum relativeDirection {
	FORWARD,
	BACK,
	LEFT,
	RIGHT
}

enum Item {
	FOOD,
	KEY,
	HAMMER,
	NOTHING
}

public class MaedenAgent {
	
	Utilities<compassDirection> utils = new Utilities<compassDirection>();
	
	private static final int DEFAULT_STARTING_X = 12;
	private static final int DEFAULT_STARTING_Y = 12;
	private int x;
	private int y;
	private compassDirection cD;
	private Item i;

	private int compassDirectionsIndex = 0;
	private final compassDirection[] compassDirections = new compassDirection[] {
			compassDirection.NORTH, compassDirection.EAST, compassDirection.SOUTH, compassDirection.WEST
	};
	
	public MaedenAgent(int x, int y, compassDirection cD, Item i) {
		this.x  = x;
		this.y  = y;
		this.cD = cD;
		this.i  = i;
	}
	
	public MaedenAgent(int x, int y) {
		this.x = x;
		this.y = y;
		this.cD = compassDirection.SOUTH;
		this.i = Item.NOTHING;
	}

	public MaedenAgent() {
		this.x = DEFAULT_STARTING_X;
		this.y = DEFAULT_STARTING_Y;
		this.cD = compassDirection.SOUTH;
		this.i = Item.NOTHING;
	}
	
	public MaedenAgent copy() {
		return new MaedenAgent(this.x, this.y, this.cD, this.i);
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public Point getPoint(){
		return new Point(this.x, this.y);
	}
	
	public Tile getTile() {
		return new Tile(this.x, this.y);
	}
	
	public compassDirection getCompassDirection(){
		return this.cD;
	}
	
	public void update(Move move) {
		this.x = move.getX();
		this.y = move.getY();
		updateCompassDirection(move.getRelativeDirection());
	}
	
	public void update(char c) {
		update(toRelativeDirection(c));
	}
	
	public void update(relativeDirection rd){
		updateCompassDirection(rd);
		switch(rd){
		case FORWARD: 
			switch(this.cD) {
			case WEST: this.x++; return;
			case EAST: this.x--; return;
			case NORTH: this.y--; return;
			case SOUTH: this.y++; return;
			default: System.out.println("agent is not facing north, south, east, or west"); return;
			}
		case BACK: 
			switch(this.cD) {
			case WEST: this.x--; return;
			case EAST: this.x++; return;
			case NORTH: this.y++; return;
			case SOUTH: this.y--; return;
			default: System.out.println("agent is not facing north, south, east, or west"); return;
			}
		default: 
			if (rd != relativeDirection.LEFT && rd != relativeDirection.RIGHT) {
				System.out.println("update recieved a character that wasn't f, b, l, or r"); return;
			}
		}
	}
	
	public void updateCompassDirection(relativeDirection rd){
		updateCompassDirection(toChar(rd));
	}
	
	public void updateCompassDirection(char rd){
		compassDirectionsIndex = utils.arrayIndexOf(compassDirections, this.cD);
		switch(toRelativeDirection(rd)) {
		case RIGHT:
			++compassDirectionsIndex;
			break;
		case LEFT: 
			--compassDirectionsIndex;
			break;
		default: break;
		}
		resetCompassDirectionsIndex();
		setCompassDirection(compassDirections[compassDirectionsIndex]);
	}
	
	public void resetCompassDirectionsIndex(){
		if (compassDirectionsIndex > 3)
			compassDirectionsIndex = 0;
		if (compassDirectionsIndex < 0)
			compassDirectionsIndex = 3;
	}
	
	public Item getItem(){
		return this.i;
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
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setCompassDirection(compassDirection cD){
		this.cD = cD;
	}
	
	public boolean hasItem() {
		if (this.i != Item.NOTHING)
				return true;
		return false;
	}
	
	public void setItem(Item i){
		this.i = i;
	}
	
	public relativeDirection toRelativeDirection(char c) {
		switch(c) {
		case 'f': return relativeDirection.FORWARD;
		case 'b': return relativeDirection.BACK;
		case 'r': return relativeDirection.RIGHT;
		case 'l': return relativeDirection.LEFT;
		default: return null;
		}
	}

	public char toChar(relativeDirection rd) {
		switch(rd) {
		case FORWARD: return 'f';
		case BACK: return 'b';
		case RIGHT: return 'r';
		case LEFT: return 'l';
		default: return 'y';
		}
	}
	
	public char compassDirectionAsChar(compassDirection cD) {
		switch(cD){
		case NORTH:	return 'n';
		case EAST: return 'e';
		case SOUTH:	return 's';
		case WEST: return 'w';
		default: return 'z';
		}
	}
	
}
