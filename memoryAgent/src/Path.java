import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by stefanandonian on 2/27/16.
 */
public class Path {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////        The following code determines how to get to locations         ////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Utilities<Character> utils = new Utilities<Character>();
    private HashSet<Point> checked; 
    private Character[] blocked;
    private Character[] relativeDirectionsAsChars;
    private LinkedBlockingQueue<Character> path = new LinkedBlockingQueue<Character>();
    
    private void setUp() {
    	blocked                   = new Character[] {'#', '@', '*', '1', '2', '3', '4', '5'}; 
    	relativeDirectionsAsChars = new Character[] {'f','r', 'b', 'l'};
    	checked                   = new HashSet<Point>();
    }
    
    public Path() {
    	setUp();
    }
    
    public LinkedBlockingQueue<Character> findPathToTarget(MemoryMap memoryMap, MaedenAgent agent, Tile target){
    	path = new LinkedBlockingQueue<Character>();
    	checked = new HashSet<Point>();
        setPathToTarget(memoryMap, agent, target, new LinkedBlockingQueue<Character>(), 'z');
        System.out.println("Path Size = " +path.size());
        return path;
    }

    public LinkedBlockingQueue<Character> findPathToTarget(MemoryMap memoryMap, MaedenAgent agent, Point target){
        return findPathToTarget(memoryMap, agent, new Tile(target));
    }

    private void setPathToTarget(MemoryMap memoryMap, MaedenAgent projectedAgent, Tile target, LinkedBlockingQueue<Character> previousMoves, char previousMoveFlag){
    	System.out.println("X = " + target.getX() + " | Y = " + target.getY());
        previousMoves = addLastMove(previousMoves, previousMoveFlag);
        if (isArrived(target, projectedAgent)) {
            path = previousMoves;
        }
        if (!path.isEmpty())
        	return;
        int count = -1;
        Move[] adjacents = getAdjacentMoves(projectedAgent);
        for(int i = 0; i < adjacents.length; i++){
        	Move adjacent = adjacents[i];
        	if (isOkToCheck(memoryMap, adjacent)) {
        		checked.add(adjacent.getPoint());
        		MaedenAgent iterationAgent = projectedAgent.copy();
        		iterationAgent.update(adjacent);
        		LinkedBlockingQueue<Character> pMCopy = new LinkedBlockingQueue<Character>();
        		pMCopy.addAll(previousMoves);
        		adjacent.print();
        		setPathToTarget(memoryMap, iterationAgent, target, pMCopy, toChar(adjacent.getRelativeDirection()));
        	}
        }
    }
    
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
    	if (utils.arrayContains(relativeDirectionsAsChars, previousMoveFlag))
    			previousMoves.add(previousMoveFlag);
        if (previousMoveFlag == 'l' || previousMoveFlag == 'r')
        		previousMoves.add('f');
        //System.out.println();
        //System.out.println(previousMoves.size());
        //System.out.println(previousMoveFlag);
        return previousMoves;
    }
    
    public boolean isOkToCheck(MemoryMap memoryMap, Move move) {
    	Tile t = move.getTile();
    	//System.out.println(" | " + checked.size());
    	//System.out.println(isOutOfBounds(t, memoryMap)+" "+isBlocked(t)+" "+isChecked(t));
    	if (!(isOutOfBounds(t, memoryMap)) && !(isBlocked(t)) && !(isChecked(t)))
    		return true;
    	return false;
    }
    
    public boolean isOutOfBounds(Tile tile, MemoryMap memoryMap) {
    	//tile.print();
    	if (tile.getX() < 0 || tile.getY() < 0 || tile.getX() >= memoryMap.getXDomain() || tile.getY() >= memoryMap.getYDomain())
    		return true;
    	return false;
    }
    
    public boolean isBlocked(Tile tile) {
        if (utils.arrayContains(blocked, tile.getValueAsChar())) 
            return true;
        return false;
    }
    
    public boolean isChecked(Tile tile) {
    	if (checked.contains(tile.getPoint()))
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
    	Move[] moves = new Move[4]; /*nesw*/
    	int count=-1; 
    	int startFlag = setStartFlag(agent);
    	
        while(++count<4) {
        	moves[count] = new Move(adjacents[startFlag]/*nesw*/, relativeDirectionsAsChars[count]/*frbl*/);
        	startFlag++;
        	if (startFlag >= 4)
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
    
    public char toChar(relativeDirection rd) {
		switch(rd) {
		case FORWARD: return 'f';
		case BACK: return 'b';
		case RIGHT: return 'r';
		case LEFT: return 'l';
		default: return 'y';
		}
	}
}
