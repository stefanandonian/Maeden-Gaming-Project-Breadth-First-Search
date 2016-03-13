import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by stefanandonian on 2/28/16.
 */
public class FoodSnifferTest {

    protected FoodSniffer findFood        = new FoodSniffer();
    protected MemoryMap memoryMap         = new MemoryMap(10, 10); 

    @Before
    public void setUp() {

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

        char[][] checkThroughPotentialFoodLocationsOutput = findFood.checkThroughPotentialFoodLocations(memoryMap, 's', new Point(5, 2));

        for (int y = 0; y< expectedMemoryMap.length; y++) {
        	for (int x= 0; x< expectedMemoryMap[y].length; x++) {
        		System.out.println("x: " + x+ "         y: " + y+ "           " + " Expected Memory Map = " + 
        				expectedMemoryMap[y][x] + "  ==  processed Memory Map = "
        			+ "" + checkThroughPotentialFoodLocationsOutput[y][x]);
        		assertEquals(expectedMemoryMap[y][x], checkThroughPotentialFoodLocationsOutput[y][x]);	
        	}
        }
    }
    
    //@Test
    public void testCheckTile() throws Exception {
    	Point bot       = new Point(4,4);
    	Point south_out = new Point(8, 7);
    	Point north_in  = new Point(4, 2);
    	Point west_out  = new Point(2, 9);
    	Point east_in   = new Point(6, 3);

    	char foodSouth_OutSpread  = findFood.checkTile('s', bot, south_out, 'p');
    	char foodNorth_InSpread = findFood.checkTile('n', bot, north_in, 'p');
    	char foodWest_OutSpread   = findFood.checkTile('w', bot, west_out, 'p');
    	char foodEast_InSpread  = findFood.checkTile('e', bot, east_in, 'p');
    	
    	assertEquals(foodSouth_OutSpread, 'u');
    	assertEquals(foodNorth_InSpread, 'p');
    	assertEquals(foodWest_OutSpread, 'u');
    	assertEquals(foodEast_InSpread, 'p');
    }
    
    @Test
    public void testIsInSpread() throws Exception {
    	Point bot = new Point(5,2);
    	Point south_in  = new Point(5, 5);
    	Point north_out = new Point(9, 3);
    	Point west_in   = new Point(2, 3);
    	Point east_out  = new Point(6, 9);

    	boolean foodSouth_InSpread  = findFood.isInSpread('s', bot, south_in);
    	boolean foodNorth_OutSpread = findFood.isInSpread('n', bot, north_out);
    	boolean foodWest_InSpread   = findFood.isInSpread('w', bot, west_in);
    	boolean foodEast_OutSpread  = findFood.isInSpread('e', bot, east_out);
    	
    	assertEquals(false, findFood.isInSpread('s', bot, new Point(9,0)));
    	assertEquals(false, findFood.isInSpread('s', bot, new Point(0, 3)));
    	assertEquals(true, findFood.isInSpread('s', bot, new Point(0,9)));
    }

    //@Test
    public void testIsInDirectionToFood() throws Exception {
    	Point bot   = new Point(4,4);
    	Point south = new Point(4, 8);
    	Point west  = new Point (0, 4);

    	boolean foodSouth_southOfBot = findFood.isInDirectionToFood('s', bot, south); 
    	boolean foodNorth_southOfBot = findFood.isInDirectionToFood('n', bot, south);
    	boolean foodWest_westOfBot   = findFood.isInDirectionToFood('w', bot, west);
    	boolean foodEast_westOfBot   = findFood.isInDirectionToFood('e', bot, west);
    	
    	assertEquals(foodSouth_southOfBot, true);
    	assertEquals(foodNorth_southOfBot, false);
    	assertEquals(foodWest_westOfBot, true);
    	assertEquals(foodEast_westOfBot, false);
    }

    //@Test
    public void testCheckTileHelper() throws Exception {
        char possible    = findFood.checkTileHelper(true, true, 'p');
        char notPossible = findFood.checkTileHelper(false, true, 'p');
        
        assertEquals(possible, 'p');
        assertEquals(notPossible, 'u');

    }

    //@Test
    public void testNotAFoodLocation() throws Exception {
    	char previouslyP = findFood.notAFoodLocation('p'); // Should become u
    	char previously0 = findFood.notAFoodLocation('0');
        assertEquals(previouslyP, 'u');
        assertFalse(previously0 == 'u');
    }
}