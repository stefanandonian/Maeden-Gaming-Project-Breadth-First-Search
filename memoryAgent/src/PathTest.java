import static org.junit.Assert.*;

import org.junit.Test;

public class PathTest {
	
	public Path path = new Path();

	@Test
	public void IsBlockedTest() {
		assertTrue(path.isBlocked(new Tile(4, 5, TileValue.WALL)));
		assertFalse(path.isBlocked(new Tile(4, 5, TileValue.NOTHING)));
		assertTrue(path.isBlocked(new Tile(4, 5, TileValue.DOOR)));
	}
	
	@Test
	public void IsOutOfBoundsTest() {
		assertTrue(path.isOutOfBounds(new Tile(4, 5), new MemoryMap(3, 3)));
		assertTrue(path.isOutOfBounds(new Tile(4, 5), new MemoryMap(5, 3)));
		assertFalse(path.isOutOfBounds(new Tile(4, 5), new MemoryMap(6, 6)));
	}
	
	@Test
	public void IsArrivedTest() {
		assertTrue(path.isArrived(new Tile(4, 5), new MaedenAgent(4, 5)));
		assertFalse(path.isArrived(new Tile(4, 5), new MaedenAgent(4, 4)));
	}

}
