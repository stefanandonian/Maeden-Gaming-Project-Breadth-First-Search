
import java.awt.*;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Arrays;

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
    protected static final int WIDTH         = 50;
    protected static final int HEIGHT        = 50;
    protected char[][] memoryMap             = new char[100][100];
    protected Point startingLocation         = new Point(50, 50);
    protected Point currentLocation          = startingLocation; //at first the current location is the starting location
    protected String currentDirection        = "north";
    protected Point botLocationOnVisualField = new Point(5, 2);

    /** Main Method */
    public static void main(String [] args) {
        MAgent client = new MAgent(HOST, MAEDENPORT);
        client.run();
    }

    /** CONSTRUCTOR */
    public MAgent(String h, int p) {
        registerWithGrid(h, p);      //connect to the grid server socket
        initializeMemoryMap();
    }

    /** The array of arrays all have 'p' for every value since the cheese could be at any spot */
    public void initializeMemoryMap() {
        Arrays.fill(memoryMap[0], 'p');
        Arrays.fill(memoryMap[1], 'p');
        Arrays.fill(memoryMap[2], 'p');
        Arrays.fill(memoryMap[3], 'p');
        Arrays.fill(memoryMap[4], 'p');
        Arrays.fill(memoryMap[5], 'p');
        Arrays.fill(memoryMap[6], 'p');
        Arrays.fill(memoryMap[7], 'p');
        Arrays.fill(memoryMap[8], 'p');
        Arrays.fill(memoryMap[9], 'p');
    }

    public void run() {
        //implementation needed
    }

    /* Use the Memory map to choose a move */
    public String chooseMove(String directionToFood, HashMap<String, Integer> flags) {
        //implementation needed
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The following code determines what to do given the current memoryMap


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The following code that makes inferences on the location of the food
    // based on the sense of smell changes with changes in direction

    public void updatePossibleFoodLocation(){

    }

    public void notFood(int row, int column){
                                              //probably going to have a bug with row and column mixed up
        if (memoryMap[row][column] == 'p') { // probably going to have a bug here with the ' ' char
            memoryMap[row][column] = ' ';
        }
    }

    //a list of booleans whether or not row>currentRow, row< currentRow, column<currentColumn, column>currentColumn;
    //pass booleans to function which decides to call ifNotFood or not

    public void notFoodVerticalLoop() {
            for (int r = 0; r < memoryMap.length; r++) {
                for (int c = 0; c < memoryMap[r].length; c++) {
                    HashMap<String, Boolean> directionFlags = booleansNotFood(r, c);
                    ifNotFood(directionFlags, r, c);
                }
            }
    }

    public HashMap<String, Boolean> booleansNotFood(int row, int column, int absoluteDistance) {
        HashMap<String, Boolean> directionFlags = new HashMap<String, Boolean>();

        directionFlags.put("leftOfCurrentLocation", row>currentLocation.y);
        directionFlags.put("rightOfCurrentLocation", row<currentLocation.y);
        directionFlags.put("upOfCurrentLocation", column>currentLocation.x);
        directionFlags.put("downOfCurrentLocation", column<currentLocation.x);

        directionFlags.put("inHorizontalSpread", absoluteDistance <= Math.abs(column-currentLocation.x));
        directionFlags.put("inVerticalSpread", absoluteDistance <= Math.abs(row-currentLocation.y));

        return directionFlags;
    }

    public void ifNotFood(HashMap<String, Boolean> directionFlags, int row, int column){
        boolean facingFoward, absoluteDistance;
        int absoluteVerticalSpread   = 1 + 2*(Math.abs(row - currentLocation.y));
        int absoluteHorizontalSpread = 1 + 2*(Math.abs(column - currentLocation.x));

        if (currentDirection.equals("north")) {
            facingFoward=column>currentLocation.x;
            absoluteDistance = directionFlags.get("inHorizontalSpread");
        } if (currentDirection.equals("south")) {
            facingFoward=column<currentLocation.x;
            absoluteDistance = directionFlags.get("inHorizontalSpread");
        }  if (currentDirection.equals("left")) {
            facingFoward=row>currentLocation.y;
            absoluteDistance = directionFlags.get("inVerticalSpread");
        }  else {//if (currentDirection.equals("right")){
            facingFoward=row<currentLocation.y;
            absoluteDistance = directionFlags.get("inVerticalSpread");
        }

        ifPossible(facingFoward, absoluteDistance, row, column);

    }

    public void ifPossible(boolean facingForward, boolean absoluteDistance, int row, int column){
        if (facingForward &&  absoluteDistance){
        } else {
            notFood(row, column);
        }
    }

    public void eliminatePossibilities(int row, int column, String direction) {
       if (direction.equals("north")) {

       } if (direction.equals("east")){

       } if (direction.equals("south")){

       } if (direction.equals("west")){

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

