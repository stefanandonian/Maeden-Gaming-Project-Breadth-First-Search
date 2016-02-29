
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
public class MAgent extends AgentInterface {

    /** size of largest possible world fits inside a 50 by 50 space */
    protected static final int WIDTH                     = 100;
    protected static final int HEIGHT                    = 100;
    protected char[][] memoryMap                         = new char[WIDTH][HEIGHT];
    protected Point startingLocation                     = new Point(50, 50);
    protected Point currentLocation                      = startingLocation; //at first the current location is the starting location
    protected char facingDirection                       = 'n';
    protected LinkedBlockingQueue<Character> movementOrders = new LinkedBlockingQueue<Character>();
    protected VisualInformation visualInformation        = new VisualInformation();
    protected FindFood findFood                          = new FindFood();
    protected FindPath findPath                          = new FindPath();

    /** Main Method */
    public static void main(String [] args) {
        MAgent client = new MAgent(HOST, MAEDENPORT);
        client.run();
    }

    /** CONSTRUCTOR */
    public MAgent(String h, int p) {
        registerWithGrid(h, p);
        initializeMemoryMap();
    }

    /** The array of arrays all have 'p' for every value since the cheese could be at any spot */
    public void initializeMemoryMap() {
       for (int i = 0; i< memoryMap.length; i++) {
        Arrays.fill(memoryMap[i], 'p');
       }
    }


    public void run() {
        String[] sensoryInfo;
        while (true) {
            sensoryInfo = getSensoryInfo();
            memoryMap = visualInformation.processRetinalField(memoryMap, facingDirection, sensoryInfo[2], currentLocation.y, currentLocation.x);
            memoryMap = findFood.checkThroughPotentialFoodLocations(memoryMap, facingDirection, currentLocation.y, currentLocation.x);
            checkOrders();
            gridOut.print(movementOrders.poll());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////// The following code determines what to do given the current memoryMap ////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void checkOrders() {
               if (foundFood()                    ){
                   movementOrders = findPathToObject('+');
                   return;
        } else if (foundBoulder() && foundHammer()){
                   clearRockWithHammer();
                   return;
        } else if (foundDoor()    && foundKey()   ){
                   clearDoorWithKey();
                   return;
        } else {
                   explore();
        }
    }

    public LinkedBlockingQueue<Character> findPathToObject(char objectSymbol) {
        Point goHere = locationOfObject(objectSymbol);
        return findPath.findPathToTarget(memoryMap, facingDirection, currentLocation.y, currentLocation.x, goHere.y, goHere.x);
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
            if (Math.random() > 0.50) {
                movementOrders = findPathToObject('u');
            } else {
                movementOrders = findPathToObject('p');
            }
    }


    public Point locationOfObject(char objectSymbol) {
        for (int row = 0; row < memoryMap.length; row++) {
            for (int column = 0; column < memoryMap[row].length; column++){
                if (memoryMap[row][column]==objectSymbol){
                    return new Point(column, row);
                }
            }
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

