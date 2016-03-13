import java.awt.Point;

/**
 * Created by stefanandonian on 2/27/16.
 */
public class FoodSniffer {

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////// The following code that makes inferences on the location of the food ////////////////////////////
    ////////////////// based on the sense of smell changes with changes in direction        ////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * X IS COLUMN and Y IS ROW
	 * @param memoryMap
	 * @param directionToFood
	 * @param bot
	 * @return
	 */
    public MemoryMap checkAllTiles(MemoryMap memoryMap, char directionToFood, MaedenAgent agent) {
        for (int x = 0; x < memoryMap.getXDomain(); ++x) {
            for (int y = 0; y < memoryMap.getYDomain(); ++y) {
            	Tile tile = memoryMap.getTile(x, y);
                memoryMap.setTile(checkTile(agent, tile));
            }
        }
        return memoryMap;
    }
    
    /**
     * X IS COLUMN and Y IS ROW
     * checkTile updates an individual tile based on if it still retains potential or not. 
     * @param directionToFood
     * @param bot
     * @param tile
     * @param value
     * @return
     */
    public TileValue checkTile(MaedenAgent agent, Tile tile) {
    	boolean isInDirectionToFood = isInDirectionToFood(agent, tile);
    	boolean isInSpread          = isInSpread(directionToFood, bot, tile);

    	return checkTileHelper(isInDirectionToFood, isInSpread, value);
    }

    /**
     * X IS COLUMN and Y IS ROW
     * isInDirectionToFood determines if the current tile 
     * is between the bot and the food.
     * @param bot
     * @param tile
     * @param directionToFood
     */
    public boolean isInDirectionToFood(char directionToFood, Point bot, Point tile){
        switch (directionToFood) {
            case 'n' :
                return tile.y<bot.y;
            case 's' :
            	return tile.y>bot.y;
            case 'w' :
                return tile.x<bot.x;
            default: //case 'e' :
                return tile.x>bot.x;
        }
    }
    
    /**
     * X IS COLUMN and Y IS ROW
     * isInSpread determines if the tile checked is within the 
     * cone of scent 
     * @param directionToFood
     * @param bot
     * @param tile
     * @return
     */
    public boolean isInSpread(char directionToFood, Point bot, Point tile){
    	System.out.print("Bot Coordinates("+bot.x + ", "+bot.y+")     "+ "Tile Coordinates("+tile.x+", "+tile.y+")        ");
    	switch(directionToFood) {
    		case 'n':
    			return Math.abs(tile.y- (bot.y-1)) >= Math.abs(tile.x - bot.x); // V ^ 
    		case 's':
            	return Math.abs(tile.y- (bot.y+1)) >= Math.abs(tile.x - bot.x); // V ^ 
    		case 'w': 
    			return Math.abs(tile.x - (bot.x-1)) >= Math.abs(tile.y- bot.y); // ><
    		case 'e': 
    			return Math.abs(tile.x - (bot.x+1)) >= Math.abs(tile.y- bot.y); // ><
    		default: return false;
    	}
    }

    /**
     * ifPossible determines if the current tile is in facing direction
     * @param facingFood
     * @param inSpread
     * @param value 
     */
    public TileValue checkTileHelper(boolean facingFood, boolean inSpread, TileValue value){
    	System.out.print("      facing food: "+facingFood+ "       inSpread : "+ inSpread);        	
        if (facingFood == true &&  inSpread == true){
        	System.out.println("");
            return value;
        } else {
            return notAFoodLocation(value);
        }
    }

    /**
     * notFood asserts that the tile under inspection is not food
     * @param value 
     */
    public TileValue notAFoodLocation(TileValue value){
        if (value == TileValue.POTENTIAL_FOOD) { 
        	System.out.println("     tile: "+'u');
            return TileValue.UNKNOWN;
        }
        	System.out.println("     tile: "+value);
        return value;
    }

}
