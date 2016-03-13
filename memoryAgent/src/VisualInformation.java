import java.awt.*;
import java.util.StringTokenizer;

/**
 * Created by stefanandonian on 2/27/16.
 */
public class VisualInformation {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////// The following code updates the memory map after processing the retinal field ////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected final Point agentLocation       = new Point(5, 2);
    protected static final int visualRows    = 7;
    protected static final int visualColumns = 5;

    /**
     *
     * @param memoryMap
     * @param facingDirection
     * @param visualInformation
     * @return
     */
    public MemoryMap processRetinalField(MemoryMap memoryMap, MaedenAgent agent, String visualInformation) {

        StringTokenizer visTokens = new StringTokenizer(visualInformation, "(", true);
        visTokens.nextToken();
        //iterate backwards through rows so character printout displays correctly
        for (int r = visualRows-1; r >= 0; --r) {
            visTokens.nextToken();
            //iterate forwards through columns
            for (int c=0; c <= visualColumns-1; ++c) {
                visTokens.nextToken();
                char[] visChars = visTokens.nextToken().toCharArray();
                //////// STEFAN'S CODE ////////
                Point visTile = new Point(r,c);
                memoryMap = updateMemory(memoryMap, agent, visTile, visChars);
                ///////////////////////////////
            }
        }
        return memoryMap;
    }

    /**
     * WARNING: This method only returns the latter of two object if they are on the same spot
     * @param memoryMap
     * @param agent
     * @param visTile
     * @param visChars
     * @return
     */
    public MemoryMap updateMemory(MemoryMap memoryMap, MaedenAgent agent, Point visTile, char[] visChars) {
        Point actual = visualToActual(agent, visTile);
        for (char item : visChars) 
        	memoryMap.setTile(actual, item);
        return memoryMap;
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
    public Point visualToActual(MaedenAgent agent, Point visTile){
        int columnDistance = visTile.x - this.agentLocation.x;
        int rowDistance    = visTile.y - this.agentLocation.y;

        switch (agent.getPointingDirection()){
            case NORTH :
                return new Point(agent.getX()+columnDistance, agent.getY()-rowDistance);
            case EAST  :
                return new Point(agent.getX()+rowDistance, 	  agent.getY()+columnDistance);
            case SOUTH :
                return new Point(agent.getX()-columnDistance, agent.getY()+rowDistance);
            case WEST  : //case 'w' :
                return new Point(agent.getX()-rowDistance,    agent.getY()-columnDistance);
            default: return null;
        }
    }

}
