
import java.awt.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * class Agent: Provides a AI agent to a Grid world simulation.
 *
 *@author:  Josh Holm, Wayne Iba, Stefan Andonian
 *@date:    1-30-16
 *@version: Delta 0.2
 */
public class MemoryAgent extends AgentInterface {

    /** size of largest possible world fits inside a 50 by 50 space */
    protected static final int WIDTH  = 40;
    protected static final int HEIGHT = 40;
    protected static final int MOVES_BEFORE_CHECKING = 100;
    private int newMoveCount = MOVES_BEFORE_CHECKING;
    MemoryMap memoryMap;
    MaedenAgent agent;
    protected LinkedBlockingQueue<Character> movementOrders; 
    //protected FoodSniffer foodSniffer = new FoodSniffer();
    protected Path path = new Path();

    /** Main Method */
    public static void main(String [] args) {
        MemoryAgent client = new MemoryAgent(HOST, MAEDENPORT);
        client.run();
    }

    /** CONSTRUCTOR */
    public MemoryAgent(String host, int port) {
        registerWithGrid(host, port);
        memoryMap = new MemoryMap(WIDTH, HEIGHT); 
        agent = new MaedenAgent(20,20); 
        movementOrders = new LinkedBlockingQueue<Character>();
    }

    public void run() {
        String sensoryInfo;
        while (true) {
            sensoryInfo = getSensoryInfo()[2]; //help out the agent, it will probably through a bug
            memoryMap.updateMemoryMap(agent, sensoryInfo);
            memoryMap.print();
            checkOrders();
            //while (movementOrders.isEmpty()!=true)
            //	System.out.println(movementOrders.poll());
            char move = movementOrders.poll();
            if (move == 'b'){
            	gridOut.println('r');
            	agent.update('r');
            	sensoryInfo=getSensoryInfo()[2];
            	memoryMap.updateMemoryMap(agent, sensoryInfo);
            	agent.update(relativeDirection.RIGHT);
            	gridOut.println('r');
            	//newMoveCount=1000;
            } else {
            agent.update(move);
            gridOut.println(move);
            }
        }
    }

    public void checkOrders() {
    	if (isTimeForNewMove()){
            if (foundFood()){
            	movementOrders = findPathToObject('+');
               } else if (foundBoulder() && foundHammer()){
                   		 clearRockWithHammer();
               } else if (foundDoor() && foundKey()){
                   clearDoorWithKey();
               } else {
                   explore();
        	}
    	}
    }
    
    public boolean isTimeForNewMove() {
    	newMoveCount++;
    	if (newMoveCount >= MOVES_BEFORE_CHECKING) {
    		newMoveCount = 0;
    		return true;
    	}
    	return false;
    }

    public LinkedBlockingQueue<Character> findPathToObject(char objectSymbol) {
        return path.findPathToTarget(memoryMap, agent, locationOfObject(objectSymbol));
    }

    public LinkedBlockingQueue<Character> concatenateQueues(LinkedBlockingQueue<Character> addTo, LinkedBlockingQueue<Character> from) {
       while(from.peek() != null) {
           addTo.add(from.poll());
       }
        return addTo;
    }

    public void clearRockWithHammer() {
        movementOrders = findPathToObject('T');
        movementOrders.add('g');
        movementOrders = concatenateQueues(movementOrders, findPathToObject('@'));
        movementOrders.add('u');
    }

    public void clearDoorWithKey() {
        movementOrders = findPathToObject('K');
        movementOrders.add('g');
        movementOrders = concatenateQueues(movementOrders, findPathToObject('#'));
        movementOrders.add('u');
    }

    public void explore() {
            //if (Math.random() > 0.50) {
            //    movementOrders = findPathToObject('u');
            //} else {
                movementOrders = findPathToObject('u');
           // }
    }


    public Point locationOfObject(char objectSymbol) {
    	for(Tile t : memoryMap.getAllTiles()){
    		if(t.getValue() == memoryMap.getValueFromChar(objectSymbol))
    			return t.getPoint();
    	}
        return null;
    }

    public boolean foundDoor() {
        return locationOfObject('#') != null;
    }

    public boolean foundBoulder() {
       return locationOfObject('@')  != null;
    }

    public boolean foundHammer() {
       return locationOfObject('T')  != null;
    }

    public boolean foundKey() {
       return locationOfObject('K')  != null;
    }

    public boolean foundFood() {
        return locationOfObject('+') != null;
    }
}

