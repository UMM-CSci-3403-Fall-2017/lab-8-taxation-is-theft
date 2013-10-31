package mpd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SerialMinimumPairwiseDistanceTest {

	private MinimumPairwiseDistance mpd;

	@Before
	public void setUp() throws Exception {
		mpd = new SerialMinimumPairwiseDistance();
	}

	@Test
	public void testEmptyArray() {
		int[] values = new int[0];
		int minimum = mpd.minimumPairwiseDistance(values);
		assertEquals(Integer.MAX_VALUE, minimum);
	}
	
	@Test
	public void testSmallArrayWithDuplicates() {
		int[] values = new int[] { 3, 2, 0, 5, 8, 9, 6, 3, 2, 0 };
		int minimum = mpd.minimumPairwiseDistance(values);
		assertEquals(0, minimum);
	}
	
	@Test
	public void testSmallSequence() {
		int[] values = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int minimum = mpd.minimumPairwiseDistance(values);
		assertEquals(1, minimum);
	}

	@Test
	public void testSmallRandomArray() {
		int[] values = new int[] { 742428, 320304, 479193, 412922, 567421, 442073, 425546, 393948, 505578, 660888 };
		int minimum = mpd.minimumPairwiseDistance(values);
		assertEquals(12624, minimum);
	}
}