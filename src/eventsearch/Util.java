package eventsearch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

public class Util
{
	private Util()
	{
	}

	public static boolean isSmartMobiles(HttpServletRequest req)
	{
		String userAgent = req.getHeader("User-Agent");
		if(userAgent == null)
		{
			return false;
		}
		if(userAgent.toLowerCase().indexOf("iphone") != -1)
		{
			return true;
		}
		else if(userAgent.toLowerCase().indexOf("ipod") != -1)
		{
			return true;
		}
		else if(userAgent.toLowerCase().indexOf("ipad") != -1)
		{
			return true;
		}
		else if(userAgent.toLowerCase().indexOf("blackberry") != -1)
		{
			return true;
		}
		else if(userAgent.toLowerCase().indexOf("android") != -1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean isStringNull(String str) {
		if(str == null || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static Calendar getStartOfMonth(Calendar cal) {
		Calendar newCal = Calendar.getInstance();
		newCal.setTime(cal.getTime());
		newCal.set(Calendar.DATE, 1);
		newCal.set(Calendar.HOUR, 0);
		newCal.set(Calendar.MINUTE, 0);
		newCal.set(Calendar.SECOND, 0);
		newCal.set(Calendar.MILLISECOND, 0);
		return newCal;
	}

	public static Calendar getEndOfMonth(Calendar cal) {
		Calendar newCal = Calendar.getInstance();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MONTH, 1);
		newCal.set(Calendar.DATE, 1);
		newCal.add(Calendar.DATE, -1);
		newCal.set(Calendar.HOUR, 0);
		newCal.set(Calendar.MINUTE, 0);
		newCal.set(Calendar.SECOND, 0);
		newCal.set(Calendar.MILLISECOND, 0);
		return newCal;
	}

	private static final SimpleDateFormat sdfYYYYMM = new SimpleDateFormat("yyyy/MM");
	public static Calendar getCalendarFromYYYYMM(String yyyymm) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdfYYYYMM.parse(yyyymm));
		return cal;
	}

	public static boolean isInDateRange(Calendar cal, Calendar calFrom, Calendar calTo) {
		return (cal.compareTo(calFrom) >= 0) && (cal.compareTo(calTo) <= 0);
	}

	private static final SimpleDateFormat sdfHHMM = new SimpleDateFormat("HH:mm");
	public static boolean isHHMM(String strTime) {
		try {
			sdfHHMM.parse(strTime);
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}
}