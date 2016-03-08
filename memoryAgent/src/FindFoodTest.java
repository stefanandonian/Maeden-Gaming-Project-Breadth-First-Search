import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by stefanandonian on 2/28/16.
 */
public class FindFoodTest {

    protected FindFood findFood        = new FindFood();
    protected char[][] memoryMap       = new char[10][10];
    protected int currentRow           = 2;
    protected int currentColumn        = 5;

    @Before
    public void setUp() {
        for (int row = 0; row < memoryMap.length; row++) {
            for (int column = 0; column < memoryMap[row].length; column++) {
                memoryMap[row][column] = 'p';
            }
        }
        memoryMap[2][5] = '0';
    }

    @Test
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

        for (int row = 0; row < expectedMemoryMap.length; row++) {
        	for (int column = 0; column < expectedMemoryMap[row].length; column++) {
        	//assertEquals(expectedMemoryMap[row][column], checkThroughPotentialFoodLocationsOutput[column][row]);	
        	System.out.println("row: " + row + "         column: " + column + "           " + " Expected Memory Map = " + 
        	expectedMemoryMap[row][column] + "  ==  processed Memory Map = "
        			+ "" + checkThroughPotentialFoodLocationsOutput[row][column]);
        	}
        }
    }

    @Test
    public void testFindFood() throws Exception {
        char[][] north = findFood.checkTile(memoryMap, false, false, 2, 5);
        char[][] south = findFood.checkTile(memoryMap, false, false, 2, 5);
        char[][] west  = findFood.checkTile(memoryMap, false, false, 2, 5);
        char[][] east  = findFood.checkTile(memoryMap, false, false, 2, 5);

        char[][] NorthNotInScope  = findFood.findFood(memoryMap, 'n', currentColumn, currentRow, currentColumn, currentRow+2);
        char[][] NorthInScope     = findFood.findFood(memoryMap, 'n', currentColumn, currentRow, currentColumn, currentRow-2);

        char[][] WestNotInScope   = findFood.findFood(memoryMap, 'w', currentColumn, currentRow, currentColumn+2, currentRow);
        char[][] WestInScope      = findFood.findFood(memoryMap, 'w', currentColumn, currentRow, currentColumn-2, currentRow);

        char[][] EastNotInScope   = findFood.findFood(memoryMap, 'e', currentColumn, currentRow, currentColumn-2, currentRow);
        char[][] EastInScope      = findFood.findFood(memoryMap, 'e', currentColumn, currentRow, currentColumn+2, currentRow);

        char[][] SouthNotInScope  = findFood.findFood(memoryMap, 's', currentColumn, currentRow, currentColumn, currentRow-2);
        char[][] SouthInScope     = findFood.findFood(memoryMap, 's', currentColumn, currentRow, currentColumn, currentRow+2);


        for (int row = 0; row < memoryMap.length; row++) {
        	for (int column = 0; column < memoryMap[row].length; column++) {

        assertEquals(NorthInScope[row][column], memoryMap[row][column]);
        assertEquals(NorthNotInScope[row][column], north[row][column]);

        assertEquals(SouthInScope[row][column], memoryMap[row][column]);
        assertEquals(SouthNotInScope[row][column], south[row][column]);

        assertEquals(WestInScope[row][column], memoryMap[row][column]);
        assertEquals(WestNotInScope[row][column], west[row][column]);

        assertEquals(EastInScope[row][column], memoryMap[row][column]);
        assertEquals(EastNotInScope[row][column], east[row][column]);
        	}
        }
    }

    @Test
    public void testCheckTile() throws Exception {
        char[][] notAFoodLocationOutput = findFood.notAFoodLocation(memoryMap, currentColumn, currentRow);
        char[][] checkTilesOutputTT     = findFood.checkTile(memoryMap, true, true, currentColumn, currentRow);
        char[][] checkTilesOutputTF     = findFood.checkTile(memoryMap, true, false, currentColumn, currentRow);
        char[][] checkTilesOutputFF     = findFood.checkTile(memoryMap, false, false, currentColumn, currentRow);

                assertFalse(notAFoodLocationOutput[currentColumn][currentRow] == checkTilesOutputTT[currentColumn][currentRow]);
                assertEquals(notAFoodLocationOutput[currentColumn][currentRow], checkTilesOutputTF[currentColumn][currentRow]);
                assertEquals(notAFoodLocationOutput[currentColumn][currentRow], checkTilesOutputFF[currentColumn][currentRow]);
    }

    @Test
    public void testNotAFoodLocation() throws Exception {
        char location = memoryMap[currentColumn][currentRow+3];
        assertEquals(location, 'p');

        char locationIsntFood = findFood.notAFoodLocation(memoryMap, currentColumn, currentRow)[currentColumn][currentRow];
        assertEquals(locationIsntFood,'u');
    }
}