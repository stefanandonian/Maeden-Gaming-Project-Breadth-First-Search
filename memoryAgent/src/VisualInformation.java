import java.awt.*;
import java.util.StringTokenizer;

/**
 * Created by stefanandonian on 2/27/16.
 */
public class VisualInformation {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////// The following code updates the memory map after processing the retinal field ////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected Point botLocationOnVisualField             = new Point(5, 2);
    protected static final int visualRows    = 7;
    protected static final int visualColumns = 5;

    /**
     *
     * @param memoryMap
     * @param facingDirection
     * @param visualInformation
     * @param locationRow
     * @param locationColumn
     * @return
     */
    public char[][] processRetinalField(char[][] memoryMap, char facingDirection, String visualInformation, int locationRow, int locationColumn) {

        char[][] alteredMemoryMap = memoryMap;
        StringTokenizer visTokens = new StringTokenizer(visualInformation, "(", true);
        visTokens.nextToken();

        //iterate backwards through rows so character printout displays correctly
        for (int visualRowUnderObservation = visualRows-1; visualRowUnderObservation >= 0; visualRowUnderObservation--) {
            visTokens.nextToken();

            //iterate forwards through columns
            for (int visualColumnUnderObservation=0; visualColumnUnderObservation <= visualColumns-1; visualColumnUnderObservation++) {
                visTokens.nextToken();
                String visChars = visTokens.nextToken();
                alteredMemoryMap = updateMemory(alteredMemoryMap, facingDirection, visChars, locationRow, locationColumn, visualRowUnderObservation, visualColumnUnderObservation);
            }
        }
        return alteredMemoryMap;
    }

    /**
     * WARNING: This method only returns the latter of two object if they are on the same spot
     * @param memoryMap
     * @param currentDirection
     * @param visChars
     * @param locationRow
     * @param locationColumn
     * @param visualRow
     * @param visualColumn
     * @return
     */
    public char[][] updateMemory(char[][] memoryMap, char currentDirection, String visChars, int locationRow, int locationColumn, int visualRow, int visualColumn) {

        char[][] alteredMemoryMap = memoryMap;
        char[] visArray = visChars.toCharArray();
        for(int x = 0; x < visChars.length(); x++) {
            char cellChar = visArray[x];
            Point updateLocation = translateLocationOnVisualToActual(currentDirection, locationRow, locationColumn, visualRow, visualColumn);
            alteredMemoryMap[updateLocation.x][updateLocation.y] = cellChar;
        }
        return alteredMemoryMap;
    }

    /**
     * This method takes a location on the visual field map and translates that into an actual location on the memorymap
     * @param currentDirection
     * @param currentLocationRow
     * @param currentLocationColumn
     * @param visualLocationRow
     * @param visualLocationColumn
     * @return
     */
    public Point translateLocationOnVisualToActual(char currentDirection, int currentLocationRow, int currentLocationColumn, int visualLocationRow, int visualLocationColumn){
        Point updateLocation;
        int horizontalDistanceFromBot = visualLocationColumn - botLocationOnVisualField.x;
        int verticalDistanceFromBot   = visualLocationRow    - botLocationOnVisualField.y;

        switch (currentDirection){
            case 'n' :
                updateLocation = new Point(currentLocationColumn+horizontalDistanceFromBot, currentLocationRow-verticalDistanceFromBot);
                break;
            case 'e' :
                updateLocation = new Point(currentLocationColumn+verticalDistanceFromBot, currentLocationRow+horizontalDistanceFromBot);
                break;
            case 's' :
                updateLocation = new Point(currentLocationColumn-horizontalDistanceFromBot, currentLocationRow+verticalDistanceFromBot);
                break;
            default: //case 'w' :
                updateLocation = new Point(currentLocationColumn-verticalDistanceFromBot, currentLocationRow-horizontalDistanceFromBot);
                break;
        } return updateLocation;
    }
}
