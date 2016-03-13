import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by stefanandonian on 2/27/16.
 */
public class FindPath {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////        The following code determines how to get to locations         ////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Utilities<Character> utils = new Utilities<Character>();
    private HashSet<Point> checked = new HashSet<Point>();
    private Character[] blocked;
    private char[] relativeDirectionsAsChars;
    
    private void setUp() {
    	blocked        = new Character[] {'#', '@', '*' }; 
    	relativeDirectionsAsChars = new char[] {'f','r', 'b', 'l'};
    }
    
    public FindPath() {
    	setUp();
    }
    	

    public LinkedBlockingQueue<Character> findPathToTarget(MemoryMap memoryMap, MaedenAgent agent, Tile target){
        return findPath(memoryMap, agent, target, new LinkedBlockingQueue<Character>(), 'z');
    }

    public LinkedBlockingQueue<Character> findPath(MemoryMap memoryMap, MaedenAgent projectedAgent, Tile target, LinkedBlockingQueue<Character> previousMoves, char previousMoveFlag){
        previousMoves = addLastMove(previousMoves, previousMoveFlag);
        if (isArrived(target, projectedAgent)) 
            return previousMoves;
        int count = 0;
        for(Move adjacent : getAdjacentMoves(projectedAgent)) {
        	if (isOkToCheck(memoryMap, adjacent))
        		projectedAgent.copy().update(adjacent);
        		findPath(memoryMap, projectedAgent, target, previousMoves, relativeDirectionsAsChars[count++]);
        		//design a pointing directiontoFoodDirectionconverter in maeden agent class.
        }
        return null;
    }
    
    /**
     * 
     * @param target
     * @param projectedAgent
     * @return
     */
    public boolean isArrived(Tile target, MaedenAgent projectedAgent){
    	return target.isSameLocation(projectedAgent);
    }

    /**
     * 
     * @param previousMoves
     * @param previousMoveFlag
     * @return
     */
    public LinkedBlockingQueue<Character> addLastMove(LinkedBlockingQueue<Character> previousMoves, char previousMoveFlag) {
        previousMoves.add(previousMoveFlag);
        if (previousMoveFlag == 'l' || previousMoveFlag == 'r')
        		previousMoves.add('f');
        return previousMoves;
    }
    
    public boolean isOkToCheck(MemoryMap memoryMap, Move move) {
    	Tile t = move.getTile();
    	if (!(isOutOfBounds(t, memoryMap)) && !(isBlocked(t)) && !(isChecked(t)))
    		return true;
    	return false;
    }
    
    /**
     * 
     * @param tile
     * @param memoryMap
     * @return
     */
    public boolean isOutOfBounds(Tile tile, MemoryMap memoryMap) {
    	if (tile.getX() < 0 || tile.getY() < 0 || tile.getX() >= memoryMap.getXDomain() || tile.getY() >= memoryMap.getYDomain())
    		return true;
    	return false;
    }
    
    /**
     * 
     * @param tile
     * @return
     */
    public boolean isBlocked(Tile tile) {
        if (utils.arrayContains(blocked, tile.getValueAsChar())) 
            return true;
        return false;
    }
    
    /**
     * 
     * @param tile
     * @return
     */
    public boolean isChecked(Tile tile) {
    	if (checked.contains(tile))
    		return true;
    	return false;
    }
    
    /**
     * 
     * @param agent
     * @return
     */
    public Move[] getAdjacentMoves(MaedenAgent agent){
    	Point[] adjacents = agent.getAdjacentPoints();
    	Move[] moves = new Move[4];
    	int count=0; 
    	int startFlag = setStartFlag(agent);
    	
        while(count++<=4) {
        	moves[count] = new Move(adjacents[startFlag], relativeDirectionsAsChars[startFlag]);
        	if (startFlag == 3)
        		startFlag = 0;
        }
        return moves;
    }
    
    /**
     * 
     * @param agent
     * @return
     */
    public int setStartFlag(MaedenAgent agent) {
        switch (agent.getCompassDirection()){
            case NORTH:
            	return 0;
            case EAST:
            	return 1;
            case SOUTH:
            	return 2;
            case WEST:
            	return 3;
            default: 
            	return -10000;
        }
    }
}
