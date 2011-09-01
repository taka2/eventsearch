package eventsearch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *	ATND検索のJSPHelper
 */
public class SearchHelper
{
	private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy/MM/dd(E) HH:mm:ss");
	private static final SimpleDateFormat YYYYMM_FORMATTER = new SimpleDateFormat("yyyy/MM");

	/**
	 * インスタンス化不可
	 */
	private SearchHelper()
	{
	}

	/**
	 * 指定した文字列を、指定した長さに切り、...を後ろに付ける。
	 * 
	 * @param str 文字列
	 * @param length 長さ(...を含む)
	 * @return strをlengthの長さに切った文字列
	 */
	public static String omitString(String str, int length)
	{
		if(str.length() > length)
		{
			return str.substring(0, length - 3) + "...";
		}
		return str;
	}

	/**
	 * 指定した日付をyyyy/MM/dd(E) HH:mm:ss形式にフォーマットする
	 * 
	 * @param dt 日付
	 * @return フォーマットされた文字列
	 */
	public static String formatYYYYMMDDHHMMSS(Date dt)
	{
		return DATETIME_FORMATTER.format(dt);
	}

	/**
	 * 指定した日付をyyyy/MM形式にフォーマットする
	 * 
	 * @param dt 日付
	 * @return フォーマットされた文字列
	 */
	public static String formatYYYYMM(Date dt)
	{
		return YYYYMM_FORMATTER.format(dt);
	}

	/**
	 * 今月を含めてnヶ月分のリストを取得する
	 * 
	 * @param months
	 * @return
	 */
	public static Calendar[] getMonthList(int months)
	{
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.DATE, 1);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);

		List<Calendar> resultList = new ArrayList<Calendar>();

		for(int i=0; i<months; i++)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate.getTime());
			cal.add(Calendar.MONTH, i);
			resultList.add(cal);
		}

		return resultList.toArray(new Calendar[0]);
	}

	/**
	 * 与えられた文字列がnullの場合は空文字、それ以外の場合は与えられた文字列をそのまま返す。
	 * 
	 * @param str
	 * @return
	 */
	public static String nullToSpace(String str)
	{
		if(str == null)
		{
			return "";
		}
		else
		{
			return str;
		}
	}

	/**
	 * イベントソースコードをイベントソース名に変換
	 */
	public static String convertEventSourceCodeToName(int eventSourceCode) {
		switch(eventSourceCode) {
		case SearchResult.EVENT_SOURCE_ATND:
			return "ATND";
		case SearchResult.EVENT_SOURCE_ZUSAAR:
			return "Zusaar";
		case SearchResult.EVENT_SOURCE_PARTAKE:
			return "PARTAKE";
		default:
			return "";
		}
	}
}
