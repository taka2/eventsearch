package eventsearch.zusaar;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.arnx.jsonic.JSON;
import eventsearch.SearchCriteria;
import eventsearch.SearchResult;

/**
 *	ZusaarAPI呼び出しを行うクラス
 */
public class ZusaarSearchRequester
{
	private static final String ZUSAAR_SEARCH_API_URL = "http://www.zusaar.com/api/event/?";
	private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 検索条件を指定して、ZusaarAPIを呼び出す。
	 * 
	 * @param criteria 検索条件
	 * @return 結果DOM
	 */
	public static SearchResult[] request(SearchCriteria criteria) throws IOException
	{
		InputStream in = null;

		try {
			URL url = new URL(buildRequestUrl(criteria));
			URLConnection urlCon = url.openConnection();
			in = urlCon.getInputStream();

			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)JSON.decode(in);

			return process(map);
		} finally {
			if(in != null) {
				in.close();
			}
		}
	}

	/**
	 * 検索条件を指定して、Zusaar検索API呼び出しのリクエストURLを構築する。
	 * 
	 * @param criteria 検索条件
	 * @return リクエストURL
	 */
	private static String buildRequestUrl(SearchCriteria criteria)
	{
		return ZUSAAR_SEARCH_API_URL + new ZusaarSearchCriteriaBuilder().buildCondition(criteria);
	}


	/**
	 * JSON to Bean変換
	 * 
	 * @param result ZusaarAPIで得られたJSON形式のドキュメント
	 * @return 各イベントをBeanに変換して格納したもの
	 */
	private static SearchResult[] process(Map<String, Object> result)
	{
		List<SearchResult> resultList = new ArrayList<SearchResult>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> eventList = (List<Map<String, Object>>)result.get("event");
		for(Map<String, Object> event : eventList) {
			SearchResult searchResult = new SearchResult();
			searchResult.setEventSource(SearchResult.EVENT_SOURCE_ZUSAAR);
			searchResult.setEventId((String)event.get("event_id"));
			searchResult.setTitle((String)event.get("title"));
			searchResult.setStrCatch((String)event.get("catch"));
			searchResult.setDescription((String)event.get("description"));
			searchResult.setEventUrl((String)event.get("event_url"));
			searchResult.setStartedAt(parseZusaarDate((String)event.get("started_at")));
			searchResult.setEndedAt(parseZusaarDate((String)event.get("ended_at")));
			searchResult.setUrl((String)event.get("url"));
			searchResult.setLimit(((BigDecimal)event.get("limit")).intValue());
			searchResult.setAddress((String)event.get("address"));
			searchResult.setPlace((String)event.get("place"));
			Object objLat = event.get("lat");
			if(objLat instanceof BigDecimal) {
				searchResult.setLat(((BigDecimal)objLat).doubleValue());
			} else if(objLat instanceof String) {
				if(((String)objLat).length() != 0) {
					searchResult.setLat(Double.parseDouble((String)objLat));
				}
			}
			Object objLon = event.get("lon");
			if(objLon instanceof BigDecimal) {
				searchResult.setLon(((BigDecimal)objLon).doubleValue());
			} else if(objLon instanceof String) {
				if(((String)objLon).length() != 0) {
					searchResult.setLon(Double.parseDouble((String)objLon));
				}
			}
			searchResult.setOwnerId((String)event.get("owner_id"));
			searchResult.setOwnerNickname((String)event.get("owner_nickname"));
			searchResult.setAccepted(((BigDecimal)event.get("accepted")).intValue());
			searchResult.setWaiting(((BigDecimal)event.get("waiting")).intValue());
			searchResult.setUpdatedAt(parseZusaarDate((String)event.get("updated_at")));

			resultList.add(searchResult);
		}

		// List to Array
		return resultList.toArray(new SearchResult[0]);
	}

	private static Calendar parseZusaarDate(String zusaarDate)
	{
		try
		{
			if(zusaarDate != null && zusaarDate.length() != 0)
			{
				Date parsedDate = DATETIME_FORMATTER.parse(zusaarDate.substring(0, 19).replace('T', ' '));
				Calendar cal = Calendar.getInstance();
				cal.setTime(parsedDate);
				return cal;
			}
			else
			{
				return null;
			}
		}
		catch(ParseException e)
		{
			return null;
		}
	}
}
