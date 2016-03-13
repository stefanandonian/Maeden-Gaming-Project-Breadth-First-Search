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
	private int x;
	private int y;
	private compassDirection cD;
	private relativeDirection rD;
	private Item i;

	private int compassDirectionsIndex = 0;
	private final relativeDirection[] relativeDirections = new relativeDirection[] {
			relativeDirection.FORWARD, relativeDirection.RIGHT, relativeDirection.BACK, relativeDirection.LEFT
	};
	private final compassDirection[] compassDirections = new compassDirection[] {
			compassDirection.NORTH, compassDirection.EAST, compassDirection.SOUTH, compassDirection.WEST
	};
	
	public MaedenAgent(int x, int y, compassDirection cD, relativeDirection rD, Item i) {
		this.x  = x;
		this.y  = y;
		this.cD = cD;
		this.rD = rD;
		this.i  = i;
	}
	
	public MaedenAgent(int x, int y) {
		this.x = x;
		this.y = y;
		this.cD = compassDirection.SOUTH;
		this.rD = relativeDirection.FORWARD;
		this.i = Item.NOTHING;
	}

	public MaedenAgent() {
		this.x = 25;
		this.y = 25;
		this.cD = compassDirection.SOUTH;
		this.rD = relativeDirection.FORWARD;
		this.i = Item.NOTHING;
	}
	
	public MaedenAgent copy() {
		return new MaedenAgent(this.x, this.y, this.cD, this.rD, this.i);
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
	
	public relativeDirection getRelativeDirection(){
		return this.rD;
	}
	
	public compassDirection getCompassDirection(){
		return this.cD;
	}
	
	public void update(Move move) {
		this.x = move.getX();
		this.y = move.getY();
		this.cD = move.getCompassDirection();
	}
	
	public void updateCompassDirection(compassDirection cD) {
		updateCompassDirection(compassDirectionAsChar(cD));
	}
	
	public void updateCompassDirection(char c){
		compassDirectionsIndex = utils.arrayIndexOf(compassDirections, this.cD);
		switch(charAsRelativeDirection(c)) {
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
	
	public void setRelativeDirection(relativeDirection rD){
		this.rD = rD;
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
	
	public relativeDirection charAsRelativeDirection(char c) {
		switch(c) {
		case 'f': return relativeDirection.FORWARD;
		case 'b': return relativeDirection.BACK;
		case 'r': return relativeDirection.RIGHT;
		case 'l': return relativeDirection.LEFT;
		default: return null;
		}
	}
	
	public char compassDirectionAsChar(compassDirection cD) {
		switch(cD){
		case NORTH:	return 'p';
		case EAST: return 'e';
		case SOUTH:	return 's';
		case WEST: return 'w';
		default: return 'z';
		}
	}

}
