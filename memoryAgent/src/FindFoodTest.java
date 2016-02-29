import junit.framework.*;

/**
 * Created by stefanandonian on 2/28/16.
 */
public class FindFoodTest extends TestCase {

    protected FindFood findFood        = new FindFood();
    protected char[][] memoryMap       = new char[10][10];
    protected int currentRow           = 2;
    protected int currentColumn        = 5;

    protected void setUp() {
        for (int row = 0; row < memoryMap.length; row++) {
            for (int column = 0; column < memoryMap[row].length; column++) {
                memoryMap[row][column] = 'p';
            }
        }
    }

    public void testCheckThroughPotentialFoodLocations() throws Exception {
        char[][] expectedMemoryMap = {
                {'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u'},
                {'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u'},
                {'u', 'u', 'u', 'u', 'u', '0', 'u', 'u', 'u', 'u'},
                {'u', 'u', 'u', 'u', 'p', 'p', 'p', 'u', 'u', 'u'},
                {'u', 'u', 'u', 'p', 'p', 'p', 'p', 'p', 'u', 'u'},
                {'u', 'u', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'u'},
                {'u', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'}
        };

        char[][] checkThroughPotentialFoodLocationsOutput = findFood.checkThroughPotentialFoodLocations(memoryMap, 's', currentRow, currentColumn);

        assertEquals(expectedMemoryMap, checkThroughPotentialFoodLocationsOutput);
    }

    public void testFindFood() throws Exception {
        char[][] checkTilesOutputNorthOfBot     = findFood.checkTiles(memoryMap, false, false, currentColumn, currentRow-2);
        char[][] checkTilesOutputSouthOfBot     = findFood.checkTiles(memoryMap, false, false, currentColumn, currentRow+2);
        char[][] checkTilesOutputWestOfBot      = findFood.checkTiles(memoryMap, false, false, currentColumn-2, currentRow);
        char[][] checkTilesOutputEastOfBot      = findFood.checkTiles(memoryMap, false, false, currentColumn+2, currentRow);

        char[][] findFoodOutputNorthNotInScope  = findFood.findFood(memoryMap, 'n', currentColumn, currentRow, currentColumn, currentRow+2);
        char[][] findFoodOutputNorthInScope     = findFood.findFood(memoryMap, 'n', currentColumn, currentRow, currentColumn, currentRow-2);

        char[][] findFoodOutputWestNotInScope   = findFood.findFood(memoryMap, 'w', currentColumn, currentRow, currentColumn+2, currentRow);
        char[][] findFoodOutputWestInScope      = findFood.findFood(memoryMap, 'w', currentColumn, currentRow, currentColumn-2, currentRow);

        char[][] findFoodOutputEastNotInScope   = findFood.findFood(memoryMap, 'e', currentColumn, currentRow, currentColumn-2, currentRow);
        char[][] findFoodOutputEastInScope      = findFood.findFood(memoryMap, 'e', currentColumn, currentRow, currentColumn+2, currentRow);

        char[][] findFoodOutputSouthNotInScope  = findFood.findFood(memoryMap, 's', currentColumn, currentRow, currentColumn, currentRow-2);
        char[][] findFoodOutputSouthInScope     = findFood.findFood(memoryMap, 's', currentColumn, currentRow, currentColumn, currentRow+2);

        assertEquals(findFoodOutputNorthInScope, memoryMap);
        assertEquals(findFoodOutputNorthNotInScope, checkTilesOutputNorthOfBot);

        assertEquals(findFoodOutputSouthInScope, memoryMap);
        assertEquals(findFoodOutputSouthNotInScope, checkTilesOutputSouthOfBot);

        assertEquals(findFoodOutputWestInScope, memoryMap);
        assertEquals(findFoodOutputWestNotInScope, checkTilesOutputWestOfBot);

        assertEquals(findFoodOutputEastInScope, memoryMap);
        assertEquals(findFoodOutputEastNotInScope, checkTilesOutputEastOfBot);
    }

    public void testCheckTiles() throws Exception {
        char[][] notAFoodLocationOutput = findFood.notAFoodLocation(memoryMap, currentColumn, currentRow);

        char[][] checkTilesOutputTT     = findFood.checkTiles(memoryMap, true, true, currentColumn, currentRow);
        char[][] checkTilesOutputTF     = findFood.checkTiles(memoryMap, true, false, currentColumn, currentRow);
        char[][] checkTilesOutputFF     = findFood.checkTiles(memoryMap, false, false, currentColumn, currentRow);

        assertFalse(notAFoodLocationOutput == checkTilesOutputTT);
        assertEquals(notAFoodLocationOutput, checkTilesOutputTF);
        assertEquals(notAFoodLocationOutput, checkTilesOutputFF);
    }

    public void testNotAFoodLocation() throws Exception {
        char location = memoryMap[currentColumn][currentRow+3];
        assertEquals(location, 'p');

        char locationIsntFood = findFood.notAFoodLocation(memoryMap, currentColumn, currentRow)[currentColumn][currentRow+2];
        assertEquals(locationIsntFood,'u');
    }
}