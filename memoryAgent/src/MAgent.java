
import java.awt.*;
import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * class Agent: Provides a AI agent to a Grid world simulation.
 *
 *@author:  Josh Holm, Wayne Iba, Stefan Andonian
 *@date:    1-30-16
 *@version: Delta 0.2
 */
public class MAgent extends AgentInterface {

    protected static final int visualRows    = 7;
    protected static final int visualColumns = 5;

    /** size of largest possible world fits inside a 50 by 50 space */
    protected static final int WIDTH                     = 100;
    protected static final int HEIGHT                    = 100;
    protected char[][] memoryMap                         = new char[WIDTH][HEIGHT];
    protected Point startingLocation                     = new Point(50, 50);
    protected Point botLocationOnVisualField             = new Point(5, 2);
    protected Point currentLocation                      = startingLocation; //at first the current location is the starting location
    protected String currentDirection                    = "north";
    protected LinkedBlockingQueue<String> movementOrders = new LinkedBlockingQueue<String>();

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The following code determines what to do given the current memoryMap
    public void run() {
        //implementation needed
    }

    /* Use the Memory map to choose a move */
    public String chooseMove(String directionToFood, HashMap<String, Integer> flags) {
        //implementation needed
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The following code determines how to get to locations


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The following code that makes inferences on the location of the food
    // based on the sense of smell changes with changes in direction
    /**
     * checkThroughPotentialFoodLocations checks every location on the for whether it
     * has the potential to be the food source or not depending on the bots current location
     * and facing direction.
     */
    public void checkThroughPotentialFoodLocations() {
            for (int r = 0; r < memoryMap.length; r++) {
                for (int c = 0; c < memoryMap[r].length; c++) {
                    ifNotFood(r, c);
                }
            }
    }

    /**
     * ifNotFood sets two boolean values that determine if the current tile on the game
     * board is a potential food source and if not eliminate its possibility from being assumed.
     * @param row
     * @param column
     */
    public void ifNotFood(int row, int column){
        boolean facingFoward, inSpread;
        int verticalSpread   = 1 + 2*(Math.abs(row - currentLocation.y));
        int horizontalSpread = 1 + 2*(Math.abs(column - currentLocation.x));

        if (currentDirection.equals("north")) {
            facingFoward=column>currentLocation.x;
            inSpread = verticalSpread >= Math.abs(column - currentLocation.x);
        } if (currentDirection.equals("south")) {
            facingFoward=column<currentLocation.x;
            inSpread = verticalSpread >= Math.abs(column - currentLocation.x);
        }  if (currentDirection.equals("left")) {
            facingFoward=row>currentLocation.y;
            inSpread = horizontalSpread >= Math.abs(column - currentLocation.y);
        }  else {//if (currentDirection.equals("right")){
            facingFoward=row<currentLocation.y;
            inSpread = horizontalSpread >= Math.abs(row - currentLocation.y);
        }

        isPossibleFoodLocation(facingFoward, inSpread, row, column);

    }

    /**
     * ifPossible determines if the current tile is in facing direction
     * @param facingForward
     * @param inSpread
     * @param row
     * @param column
     */
    public void isPossibleFoodLocation(boolean facingForward, boolean inSpread, int row, int column){
        if (facingForward &&  inSpread){
        } else {
            notAFoodLocation(row, column);
        }
    }

    /**
     * notFood asserts that the tile under inspection is not food
     * @param row
     * @param column
     */
    public void notAFoodLocation(int row, int column){
        //probably going to have a bug with row and column mixed up
        if (memoryMap[row][column] == 'p') { // probably going to have a bug here with the ' ' char
            memoryMap[row][column] = ' ';
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The following code updates the memory map after processing the retinal field

    /* When processing RetinalField simply set the correct position in the memory map with the object*/
    public HashMap<String, Integer> processRetinalField(String info) {

        StringTokenizer visTokens = new StringTokenizer(info, "(", true);
        visTokens.nextToken();

        //iterate backwards through columns so character printout displays correctly
        for (int y = visualRows-1; y >= 0; y--) {
            visTokens.nextToken();

        //iterate forwards through rows
            for (int x=0; x <= visualColumns-1; x++) {
                visTokens.nextToken();
                String visChars = visTokens.nextToken();
                updateMemory(visChars, y, x);
                }
            }
        return null;
    }

    public void updateMemory(String visChars, int row, int column) {

        char[] visArray = visChars.toCharArray();
        for(int x = 0; x < visChars.length(); x++) {
            char cellChar = visArray[x];
            Point updateLocation = updateLocation(row, column);
            memoryMap[updateLocation.x][updateLocation.y] = cellChar;
        }
    }

    // forward is positive, backward is negative
    public int changeInVertical(int row){
        return botLocationOnVisualField.y - row;
    }

    //left is negative, right is positive
    public int changeInHorizontal(int column) {
        return column - botLocationOnVisualField.x;
    }

    public Point updateLocation(int row, int column){
        Point uploc = new Point(0,0);
        int horizontal = changeInHorizontal(column);
        int vertical   = changeInVertical(row);
        int currentX   = currentLocation.x;
        int currentY   = currentLocation.y;

              if (currentDirection.equals("north")) {
                  uploc = new Point(currentX+horizontal, currentY+vertical);
       } else if (currentDirection.equals("east")) {
                  uploc = new Point(currentX+vertical, currentY-horizontal);
       } else if (currentDirection.equals("south")) {
                  uploc = new Point(currentX-horizontal, currentY-vertical);
       } else if (currentDirection.equals("west")) {
                  uploc = new Point(currentX-vertical, currentY+horizontal);
       } return uploc;
    }

}

