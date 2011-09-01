package eventsearch.partake;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;
import eventsearch.SearchCriteria;
import eventsearch.SearchResult;
import eventsearch.Util;

/**
 * Partake iCal取得を行うクラス
 */
public class PartakeSearchRequester {
	private static final String PARTAKE_ICAL_URL = "http://partake.in/calendars/all";

	/**
	 * 検索条件を指定して、Partake iCalを取得する
	 * 
	 * @param criteria
	 *            検索条件
	 * @return 結果DOM
	 */
	public static SearchResult[] request(SearchCriteria criteria)
			throws IOException {
		InputStream in = null;

		try {
			URL url = new URL(PARTAKE_ICAL_URL);
			URLConnection urlCon = url.openConnection();
			in = urlCon.getInputStream();

			// iCalのパース
			CalendarBuilder builder = new CalendarBuilder();
			Calendar calendar = builder.build(in);
			ComponentList list = calendar.getComponents(Component.VEVENT);

			return process(list, criteria);
		} catch (ParserException pe) {
			throw new IOException(pe);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}


	/**
	 * iCal to Bean変換
	 * 
	 * @param result Partakeで得られたiCal形式のドキュメント
	 * @return 各イベントをBeanに変換して格納したもの
	 */
	private static SearchResult[] process(ComponentList list, SearchCriteria criteria)
	{
		int listSize = list.size();
		List<SearchResult> resultList = new ArrayList<SearchResult>();
		for(int i=0; i<listSize; i++) {
			VEvent event = (VEvent)list.get(i);
			SearchResult searchResult = new SearchResult();
			searchResult.setEventSource(SearchResult.EVENT_SOURCE_PARTAKE);
			searchResult.setEventId(event.getUid().getValue());
			searchResult.setTitle(event.getSummary().getValue());
			if(event.getUrl() != null) {
				searchResult.setUrl(event.getUrl().getValue());
			}

			searchResult.setEventUrl(event.getDescription().getValue());
			searchResult.setStartedAt(dateToCalendar(event.getStartDate().getDate()));
			if(event.getEndDate() != null) {
				searchResult.setEndedAt(dateToCalendar(event.getEndDate().getDate()));
			}
			searchResult.setUpdatedAt(dateToCalendar(event.getLastModified().getDate()));

			if(isMatchCriteria(searchResult, criteria)) {
				resultList.add(searchResult);
			}
		}

		return resultList.toArray(new SearchResult[0]);
	}

	private static boolean isMatchCriteria(SearchResult searchResult, SearchCriteria criteria) {
		// イベントID
		if(!Util.isStringNull(criteria.getEventId())) {
			if(searchResult.getEventId().equals(criteria.getEventId())) {
				return true;
			} else {
				return false;
			}
		}

		// 対象月 (yyyy/mm or null)
		java.util.Calendar targetFrom = null;
		java.util.Calendar targetTo = null;
		if(!Util.isStringNull(criteria.getTargetMonth())) {
			java.util.Calendar calTargetMonth = null;
			try {
				calTargetMonth = Util.getCalendarFromYYYYMM(criteria.getTargetMonth());
			} catch (ParseException pe) {
				calTargetMonth = java.util.Calendar.getInstance();
			}

			targetFrom = Util.getStartOfMonth(calTargetMonth);
			targetTo = Util.getEndOfMonth(calTargetMonth);
		} else {
			// 指定なしの場合は、むこう三ヶ月を検索する
			java.util.Calendar calTargetMonth = java.util.Calendar.getInstance();
			java.util.Calendar calTargetMonthTo = java.util.Calendar.getInstance();
			calTargetMonthTo.setTime(calTargetMonth.getTime());
			calTargetMonthTo.add(java.util.Calendar.MONTH, 2);

			targetFrom = Util.getStartOfMonth(calTargetMonth);
			targetTo = Util.getEndOfMonth(calTargetMonthTo);
		}
		if(Util.isInDateRange(searchResult.getStartedAt(), targetFrom, targetTo)) {
			return true;
		}

		// キーワード
		if(!Util.isStringNull(criteria.getKeyword())) {
			if(searchResult.getTitle().indexOf(criteria.getKeyword()) != -1) {
				return true;
			}
		}

		return false;
	}

	private static java.util.Calendar dateToCalendar(java.util.Date dt) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(dt);
		cal.add(java.util.Calendar.HOUR, 9);
		return cal;
	}
}
