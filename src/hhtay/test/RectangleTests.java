package hhtay.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import hhtay.Rectangle;

public class RectangleTests {

	Rectangle rect = new Rectangle(5,4);
	
	@Test
	public void testGetArea() {
		assertEquals(rect.getVolume(), 20);
	}
}
