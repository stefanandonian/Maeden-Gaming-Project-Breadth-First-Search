import java.awt.Point;

enum Value{
	UNKNOWN,
	POTENTIAL_FOOD,
	FOOD,
	WALL,
	ROCK,
	KEY,
	HAMMER,
	DOOR,
	NOTHING
}
public class MemoryMap {
	Value[][] map;
	int xDomain;
	int yDomain;
	
	public MemoryMap(int x, int y){
		this.xDomain = x;
		this.yDomain = y;
		map = new Value[xDomain][yDomain];
	}
	
	public void clearMemory(){
		for(int x = 0; x < xDomain; ++x){
			for(int y = 0; y < yDomain; ++y){
				setTile(x, y, Value.UNKNOWN);
			}
		}
	}
	
	public void setTile(Point p, Value v){
		setTile(p.x, p.y, v);
	}
	public void setTile(int x, int y, Value v){
		map[x][y] = v;
	}
	
	public Value getTile(Point p){
		return getTile(p.x, p.y);
	}
	public Value getTile(int x, int y){
		return map[x][y];
	}
	
	public int getNumTiles(){
		return xDomain*yDomain;
	}
}
