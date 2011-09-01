package test.eventsearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Test;

import eventsearch.Util;

public class TestUtil {
	@Test
	public void testIsStringNull() {
		assertTrue(Util.isStringNull(null));
		assertTrue(Util.isStringNull(""));
		assertTrue(Util.isStringNull(" "));
		assertTrue(Util.isStringNull("	"));
		assertTrue(!Util.isStringNull("a"));
		assertTrue(!Util.isStringNull("あ"));
	}

	@Test
	public void testGetStartOfMonth() {
		Calendar cal = Calendar.getInstance();
		
		// 2011/08/15
		cal.set(2011, 7, 15);

		Calendar startOfMonth = Util.getStartOfMonth(cal);
		assertEquals(startOfMonth.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		assertEquals(startOfMonth.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(startOfMonth.get(Calendar.DATE), 1);
	}

	@Test
	public void testGetEndOfMonth() {
		Calendar cal = Calendar.getInstance();
		
		// 2011/08/15
		cal.set(2011, 7, 15);

		Calendar startOfMonth = Util.getEndOfMonth(cal);
		assertEquals(startOfMonth.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		assertEquals(startOfMonth.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(startOfMonth.get(Calendar.DATE), 31);
	}


	@Test
	public void testGetCalendarFromYYYYMM() {
		try {
			Calendar cal = Util.getCalendarFromYYYYMM("2011/08");
			assertEquals(cal.get(Calendar.YEAR), 2011);
			assertEquals(cal.get(Calendar.MONTH), 7);
			assertEquals(cal.get(Calendar.DATE), 1);
		} catch (ParseException e) {
			org.junit.Assert.fail();
		}
	}

	@Test
	public void testIsInDateRange() {
		Calendar calFrom = Calendar.getInstance();
		calFrom.set(2011, 7, 1, 0, 0, 0);
		calFrom.set(Calendar.MILLISECOND, 0);
		Calendar calTo = Calendar.getInstance();
		calTo.set(2011, 9, 31, 0, 0, 0);
		calTo.set(Calendar.MILLISECOND, 0);

		Calendar cal = Calendar.getInstance();
		cal.set(2011, 7, 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		assertTrue(Util.isInDateRange(cal, calFrom, calTo));

		cal.set(2011, 9, 31, 0, 0, 0);
		assertTrue(Util.isInDateRange(cal, calFrom, calTo));

		cal.set(2011, 6, 30, 0, 0, 0);
		assertTrue(!Util.isInDateRange(cal, calFrom, calTo));

		cal.set(2011, 10, 1, 0, 0, 0);
		assertTrue(!Util.isInDateRange(cal, calFrom, calTo));
	}

	@Test
	public void testIsHHMM() {
		assertTrue(Util.isHHMM("00:00"));
		assertTrue(Util.isHHMM("23:59"));
		assertTrue(!Util.isHHMM("1200"));
		assertTrue(!Util.isHHMM(""));
	}
}
