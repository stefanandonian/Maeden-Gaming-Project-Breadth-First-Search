/**
 * Created by stefanandonian on 2/27/16.
 */
public class FindFood {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////// The following code that makes inferences on the location of the food ////////////////////////////
    ////////////////// based on the sense of smell changes with changes in direction        ////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * checkThroughPotentialFoodLocations checks every location on the for whether it
     * has the potential to be the food source or not depending on the bots current location
     * and facing direction.
     * @param memoryMap
     */
    public char[][] checkThroughPotentialFoodLocations(char[][] memoryMap, char directionToFood, int currentRow, int currentColumn) {
        char[][] alteredMemoryMap = memoryMap;
        for (int r = 0; r < memoryMap.length; r++) {
            for (int c = 0; c < memoryMap[r].length; c++) {
                alteredMemoryMap = findFood(memoryMap, directionToFood, currentRow, currentColumn, r, c);
            }
        }
        return alteredMemoryMap;
    }

    /**
     * ifNotFood sets two boolean values that determine if the current tile on the game
     * board is a potential food source and if not eliminate its possibility from being assumed.
     * @param memoryMap
     * @param botRow
     * @param botColumn
     * @param projectedRow
     * @param projectedColumn
     * @param directionToFood
     */
    public char[][] findFood(char[][] memoryMap, char directionToFood, int botRow, int botColumn, int projectedRow, int projectedColumn){
        boolean facingFood, inSpread;
        int verticalSpread   = 1 + 2*(Math.abs(projectedRow - botRow));
        int horizontalSpread = 1 + 2*(Math.abs(projectedColumn - botColumn));
        boolean vspread = verticalSpread >= Math.abs(projectedColumn - botColumn);
        boolean hspread = horizontalSpread >= Math.abs(projectedRow - botRow);

        switch (directionToFood) {
            case 'n' :
                facingFood=projectedColumn>botColumn;
                inSpread = vspread;
                break;
            case 's' :
                facingFood=projectedColumn<botColumn;
                inSpread = vspread;
                break;
            case 'l' :
                facingFood=projectedRow>botRow;
                inSpread = hspread;
                break;
            default: //case 'r' :
                facingFood=projectedRow<botRow;
                inSpread = hspread;
                break;
        }

        return checkTiles(memoryMap, facingFood, inSpread, projectedRow, projectedColumn);

    }

    /**
     * ifPossible determines if the current tile is in facing direction
     * @param memoryMap
     * @param facingFood
     * @param inSpread
     * @param row
     * @param column
     */
    public char[][] checkTiles(char[][] memoryMap, boolean facingFood, boolean inSpread, int row, int column){
        if (facingFood  &&  inSpread){
            return memoryMap;
        } else {
            return notAFoodLocation(memoryMap, row, column);
        }
    }

    /**
     * notFood asserts that the tile under inspection is not food
     * @param memoryMap
     * @param row
     * @param column
     */
    public char[][] notAFoodLocation(char[][] memoryMap, int row, int column){
        //probably going to have a bug with row and column mixed up
        char[][] alteredMemoryMap = memoryMap;
        if (memoryMap[row][column] == 'p') { // probably going to have a bug here with the ' ' char
            alteredMemoryMap[row][column] = 'u';
            return alteredMemoryMap;
        }
        return memoryMap;
    }
}
