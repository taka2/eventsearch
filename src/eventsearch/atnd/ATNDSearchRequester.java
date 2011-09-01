package eventsearch.atnd;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eventsearch.SearchCriteria;
import eventsearch.SearchResult;

/**
 *	ATND検索の検索API呼び出しを行うクラス
 */
public class ATNDSearchRequester
{
	private static final String ATND_SEARCH_API_URL = "http://api.atnd.org/events/?";
	private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 検索条件を指定して、ATND検索APIを呼び出す。
	 * 
	 * @param criteria 検索条件
	 * @return 結果DOM
	 */
	public static SearchResult[] request(SearchCriteria criteria) throws IOException
	{
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(buildRequestUrl(criteria));
			return process(doc);
		}
		catch(ParserConfigurationException e)
		{
			throw new RuntimeException(e);
		}
		catch(SAXException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 検索条件を指定して、ATND検索API呼び出しのリクエストURLを構築する。
	 * 
	 * @param criteria 検索条件
	 * @return リクエストURL
	 */
	public static String buildRequestUrl(SearchCriteria criteria)
	{
		return ATND_SEARCH_API_URL + new ATNDSearchCriteriaBuilder().buildCondition(criteria);
	}

	/**
	 * ATOM to Bean変換
	 * 
	 * @param result ATNDAPIで得られたATOM形式のドキュメント
	 * @return 各イベントをBeanに変換して格納したもの
	 */
	private static SearchResult[] process(Document result)
	{
		List<SearchResult> resultList = new ArrayList<SearchResult>();
		NodeList events = result.getElementsByTagName("event");
		for(int i=0; i<events.getLength(); i++)
		{
			Node eventNode = events.item(i);
			NodeList eventChildNodes = eventNode.getChildNodes();
			SearchResult searchResult = new SearchResult();
			searchResult.setEventSource(SearchResult.EVENT_SOURCE_ATND);

			for(int j=0; j<eventChildNodes.getLength(); j++)
			{
				Node childNode = eventChildNodes.item(j);

				// nullノードはスキップ
				if(childNode.getAttributes() != null && childNode.getAttributes().getNamedItem("nil") != null)
				{
					continue;
				}
				// 空ノードはスキップ
				if(childNode.getFirstChild() == null)
				{
					continue;
				}

				if(childNode.getNodeName().equals("place"))
				{
					searchResult.setPlace(childNode.getFirstChild().getNodeValue());
				}
				else if(childNode.getNodeName().equals("lon"))
				{
					searchResult.setLon(Double.parseDouble(childNode.getFirstChild().getNodeValue()));
				}
				else if(childNode.getNodeName().equals("accepted"))
				{
					searchResult.setAccepted(Integer.parseInt(childNode.getFirstChild().getNodeValue()));
				}
				else if(childNode.getNodeName().equals("event_id"))
				{
					searchResult.setEventId(childNode.getFirstChild().getNodeValue());
				}
				else if(childNode.getNodeName().equals("updated_at"))
				{
					searchResult.setUpdatedAt(parseATNDDate(childNode.getFirstChild().getNodeValue()));
				}
				else if(childNode.getNodeName().equals("title"))
				{
					searchResult.setTitle(childNode.getFirstChild().getNodeValue());
				}
				else if(childNode.getNodeName().equals("ended_at"))
				{
					searchResult.setEndedAt(parseATNDDate(childNode.getFirstChild().getNodeValue()));
				}
				else if(childNode.getNodeName().equals("waiting"))
				{
					searchResult.setWaiting(Integer.parseInt(childNode.getFirstChild().getNodeValue()));
				}
				else if(childNode.getNodeName().equals("event_url"))
				{
					searchResult.setEventUrl(childNode.getFirstChild().getNodeValue());
				}
				else if(childNode.getNodeName().equals("url"))
				{
					searchResult.setUrl(childNode.getFirstChild().getNodeValue());
				}
				else if(childNode.getNodeName().equals("owner_nickname"))
				{
					searchResult.setOwnerNickname(childNode.getFirstChild().getNodeValue());
				}
				else if(childNode.getNodeName().equals("catch"))
				{
					searchResult.setStrCatch(childNode.getFirstChild().getNodeValue());
				}
				else if(childNode.getNodeName().equals("description"))
				{
					searchResult.setDescription(childNode.getFirstChild().getNodeValue());
				}
				else if(childNode.getNodeName().equals("owner_id"))
				{
					searchResult.setOwnerId(childNode.getFirstChild().getNodeValue());
				}
				else if(childNode.getNodeName().equals("limit"))
				{
					searchResult.setLimit(Integer.parseInt(childNode.getFirstChild().getNodeValue()));
				}
				else if(childNode.getNodeName().equals("lat"))
				{
					searchResult.setLat(Double.parseDouble(childNode.getFirstChild().getNodeValue()));
				}
				else if(childNode.getNodeName().equals("address"))
				{
					searchResult.setAddress(childNode.getFirstChild().getNodeValue());
				}
				else if(childNode.getNodeName().equals("started_at"))
				{
					searchResult.setStartedAt(parseATNDDate(childNode.getFirstChild().getNodeValue()));
				}
			}

			resultList.add(searchResult);
		}

		// List to Array
		return resultList.toArray(new SearchResult[0]);
	}

	private static Calendar parseATNDDate(String atndDate)
	{
		try
		{
			if(atndDate != null && atndDate.length() != 0)
			{
				Date parsedDate = DATETIME_FORMATTER.parse(atndDate.substring(0, 19).replace('T', ' '));
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
