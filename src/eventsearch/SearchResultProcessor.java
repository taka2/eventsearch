package eventsearch;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.arnx.jsonic.JSON;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class SearchResultProcessor {
	private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HHmm");

	public static SearchResult[] postFilter(SearchResult[] searchResultArray, SearchCriteria criteria)
	{
		List<SearchResult> resultList = new ArrayList<SearchResult>();

		for(SearchResult searchResult : searchResultArray)
		{
			// 本日以降のイベントであること
			if(!isDateLater(searchResult.getStartedAt()))
			{
				continue;
			}

			// 曜日条件が合致していること
			int dayOfWeek = searchResult.getStartedAt().get(Calendar.DAY_OF_WEEK);
			switch(criteria.getTargetDayOfWeek())
			{
				case SearchCriteria.TARGET_DAY_OF_WEEK_WEEKDAY:
					if(dayOfWeek == Calendar.SATURDAY ||
					   dayOfWeek == Calendar.SUNDAY)
					{
						continue;
					}
					break;
				case SearchCriteria.TARGET_DAY_OF_WEEK_WEEKEND:
					if(dayOfWeek != Calendar.SATURDAY &&
					   dayOfWeek != Calendar.SUNDAY)
					{
						continue;
					}
					break;
				default:
			}

			// 開始時間条件が合致していること
			if(criteria.getTargetTimeFrom() != null && criteria.getTargetTimeFrom().length() != 0)
			{
				int criteriaTimeFrom = Integer.parseInt(criteria.getTargetTimeFrom().replace(":", ""));
				int startedAtTime = Integer.parseInt(TIME_FORMATTER.format(searchResult.getStartedAt().getTime()));
				if(startedAtTime < criteriaTimeFrom)
				{
					continue;
				}
			}

			// 終了時間条件が合致していること
			if(criteria.getTargetTimeTo() != null && criteria.getTargetTimeTo().length() != 0)
			{
				if(searchResult.getEndedAt() != null)
				{
					int criteriaTimeTo = Integer.parseInt(criteria.getTargetTimeTo().replace(":", ""));
					int endedAtTime = Integer.parseInt(TIME_FORMATTER.format(searchResult.getEndedAt().getTime()));
					if(endedAtTime > criteriaTimeTo)
					{
						continue;
					}
				}
			}

			// 場所条件が合致していること
			if(criteria.getAddress() != null && criteria.getAddress().length() != 0)
			{
				if(searchResult.getAddress() != null)
				{
					if(searchResult.getAddress().indexOf(criteria.getAddress()) == -1)
					{
						continue;
					}
				}
				else
				{
					// 場所条件が指定され、かつ、結果がnullの場合は抽出しない。
					continue;
				}
			}

			// 空席有条件の場合
			if(criteria.isOnlyExistsVacantSeat())
			{
				if(searchResult.getLimit() != 0)
				{
					if(searchResult.getLimit() <= searchResult.getAccepted())
					{
						continue;
					}
				}
			}
			
			// 除外キーワード
			if(criteria.getKeyword() != null) {
				boolean isSkip = false;

				String[] keywords = criteria.getKeyword().split(" ");
				for(String keyword : keywords) {
					if(keyword.startsWith("-")) {
						String excludeKeyword = keyword.substring(1);
						if(searchResult.getTitle() != null && searchResult.getTitle().indexOf(excludeKeyword) != -1) {
							isSkip = true;
							break;
						}
						if(searchResult.getDescription() != null && searchResult.getDescription().indexOf(excludeKeyword) != -1) {
							isSkip = true;
							break;
						}
					}
				}

				if(isSkip) {
					continue;
				}
			}

			resultList.add(searchResult);
		}

		return resultList.toArray(new SearchResult[0]);
	}

	public static void toRSS(HttpServletRequest req, OutputStream oStream, SearchCriteria criteria, SearchResult[] searchResult) throws ParseException, IOException, FeedException
	{
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		feed.setTitle("イベント検索結果");
		feed.setLink(req.getRequestURL() + "?" + req.getQueryString());
		feed.setDescription("イベント検索結果のフィード");

		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		for(SearchResult result : searchResult)
		{
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle(result.getTitle());
			entry.setLink(result.getEventUrl());
			entry.setPublishedDate(result.getUpdatedAt().getTime());
			entry.setAuthor(result.getOwnerNickname());
			SyndContent description = new SyndContentImpl();
			description.setType("text/plain");
			String strDescription = result.getDescription();
			if(strDescription != null) {
				description.setValue(strDescription.replace((char)0x1e, ' '));
			}
			entry.setDescription(description);

			entries.add(entry);
		}

		feed.setEntries(entries);

		OutputStreamWriter writer = new OutputStreamWriter(oStream, "UTF-8");
        SyndFeedOutput output = new SyndFeedOutput();
        output.output(feed, writer);
        writer.flush();
        writer.close();
	}

	public static void toJSON(OutputStream oStream, SearchResult[] searchResult) throws ParseException, IOException, FeedException
	{
		SearchResultForJSON[] result = new SearchResultForJSON[searchResult.length];
		for(int i=0; i<result.length; i++)
		{
			result[i] = new SearchResultForJSON();
			result[i].setEventSource(SearchHelper.convertEventSourceCodeToName(searchResult[i].getEventSource()));
			result[i].setTitle(searchResult[i].getTitle());
			result[i].setStartedAt(SearchHelper.formatYYYYMMDDHHMMSS(searchResult[i].getStartedAt().getTime()));
			if(searchResult[i].getEndedAt() != null)
			{
				result[i].setEndedAt(SearchHelper.formatYYYYMMDDHHMMSS(searchResult[i].getEndedAt().getTime()));
			}
			result[i].setEventId(searchResult[i].getEventId());
			result[i].setEventUrl(searchResult[i].getEventUrl());
			result[i].setPlace(SearchHelper.nullToSpace(searchResult[i].getPlace()));
			result[i].setAddress(SearchHelper.nullToSpace(searchResult[i].getAddress()));
		}
		JSON.encode(result, new OutputStreamWriter(oStream, "UTF-8"));
	}

	private static boolean isDateLater(Calendar calTarget)
	{
		// 現在日時
		Calendar calCurrent = Calendar.getInstance();
		calCurrent.set(Calendar.HOUR_OF_DAY, 0);
		calCurrent.set(Calendar.MINUTE, 0);
		calCurrent.set(Calendar.SECOND, 0);
		calCurrent.set(Calendar.MILLISECOND, 0);

		// 指定日時
		Calendar calTargetClone = Calendar.getInstance();
		calTargetClone.setTime(calTarget.getTime());
		calTargetClone.set(Calendar.HOUR_OF_DAY, 0);
		calTargetClone.set(Calendar.MINUTE, 0);
		calTargetClone.set(Calendar.SECOND, 0);
		calTargetClone.set(Calendar.MILLISECOND, 0);

		return calTargetClone.compareTo(calCurrent) >= 0;
	}
}
