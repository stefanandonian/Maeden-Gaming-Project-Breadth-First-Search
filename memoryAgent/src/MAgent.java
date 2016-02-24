
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
    public void initializeMemoryMap(){
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

    public void updatePossibleFoodLocation()

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

