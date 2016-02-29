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

    private HashSet<Point> previouslyCheckedLocations    = new HashSet<Point>();

    public LinkedBlockingQueue<Character> findPathToTarget(char[][] memoryMap, char facingDirection, int currentRow, int currentColumn, int targetRow, int targetColumn){
        return findPath(memoryMap, new LinkedBlockingQueue<Character>(), facingDirection, 'z', currentRow, currentColumn, targetRow, targetColumn);
    }

    public LinkedBlockingQueue<Character> findPath(
                                           char[][] memoryMap,
                                           LinkedBlockingQueue<Character> previousMoves,
                                           char projectedFacingDirection,
                                           char previousMoveFlag,
                                           int currentProjectedRow,
                                           int currentProjectedColumn,
                                           int targetRow,
                                           int targetColumn)
    {
        addLastMove(previousMoves, previousMoveFlag);
        if (currentProjectedColumn==targetColumn    &&    currentProjectedRow==targetRow) {
            return previousMoves;
        }
        HashMap<Character, Point> directionsToLocations = mapDirectionsToLocations(projectedFacingDirection, currentProjectedRow, currentProjectedColumn);
        //look through the four directions at from the current stop unless they are either not a wall/boulder/door
        //or they have already been looked at.
        // check to see if the way forward is blocked or if it has already been checked
        //recursively call findPathToTarget with a different row or column, incremented previousMoves, but same target
        Point forward = directionsToLocations.get('f');
        Point back    = directionsToLocations.get('b');
        Point right   = directionsToLocations.get('r');
        Point left    = directionsToLocations.get('l');

        if (isBlockedOrPreviouslyCheckedLocation(forward.x, forward.y, memoryMap) != true) {
            findPath(memoryMap, previousMoves, projectedFacingDirection, 'f', forward.x, forward.y, targetRow, targetColumn);
        }
        if (isBlockedOrPreviouslyCheckedLocation(back.x, back.y, memoryMap)       != true) {
            findPath(memoryMap, previousMoves, projectedFacingDirection, 'b', back.x, back.y,       targetRow, targetColumn);
        }
        if (isBlockedOrPreviouslyCheckedLocation(right.x, right.y, memoryMap)     != true) {
            findPath(memoryMap, previousMoves, projectedFacingDirection, 'r', right.x, right.y,     targetRow, targetColumn);
        }
        if (isBlockedOrPreviouslyCheckedLocation(left.x, left.y, memoryMap)       != true) {
            findPath(memoryMap, previousMoves, projectedFacingDirection, 'l', left.x, left.y,       targetRow, targetColumn);
        }
        //System.out.println("Error! There is not a route to the desired location from your current location.");
        return null;

    }

    public LinkedBlockingQueue<Character> addLastMove(LinkedBlockingQueue<Character> incompletePreviousMoves, char previousMoveFlag) {
        LinkedBlockingQueue<Character> completePreviousMoves = new LinkedBlockingQueue<Character>();
        switch (previousMoveFlag) {
            case 'f':
                completePreviousMoves.add('f');
                break;
            case 'b':
                completePreviousMoves.add('b');
                break;
            case 'l':
                completePreviousMoves.add('l');
                completePreviousMoves.add('f');
                break;
            case 'r':
                completePreviousMoves.add('r');
                completePreviousMoves.add('f');
                break;
            default:
                break;
        }
        return completePreviousMoves;
    }

    public boolean isBlockedOrPreviouslyCheckedLocation(int row,int column, char[][] memoryMap) {
        char inspectedLocation = memoryMap[row][column];
        if (inspectedLocation == '*' || inspectedLocation == '#' || inspectedLocation == '@'
                || previouslyCheckedLocations.contains(new Point(row, column))) {
            return true;
        }
        return false;
    }

    public HashMap<Character, Point> mapDirectionsToLocations(char facingDirection, int row, int column){
        HashMap<Character, Point> directionsToLocations = new HashMap<Character, Point>();
        switch (facingDirection){
            case 'n':
                directionsToLocations.put('f',   new Point(row--, column));
                directionsToLocations.put('b',      new Point(row++, column));
                directionsToLocations.put('r',     new Point(row,   column--));
                directionsToLocations.put('l',      new Point(row,   column++));
                break;
            case 's':
                directionsToLocations.put('f',   new Point(row++, column));
                directionsToLocations.put('b',      new Point(row--, column));
                directionsToLocations.put('r',     new Point(row,   column++));
                directionsToLocations.put('f',      new Point(row,   column--));
                break;
            case 'w':
                directionsToLocations.put('f',   new Point(row,   column--));
                directionsToLocations.put('b',      new Point(row,   column++));
                directionsToLocations.put('r',     new Point(row--, column));
                directionsToLocations.put('l',      new Point(row++, column));
                break;
            default: //'e':
                directionsToLocations.put('f',   new Point(row,   column++));
                directionsToLocations.put('b',      new Point(row,   column--));
                directionsToLocations.put('r',     new Point(row++, column));
                directionsToLocations.put('l',      new Point(row--, column));
                break;
        }
        return directionsToLocations;
    }
}
