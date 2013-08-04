package eventsearch.atnd;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import eventsearch.SearchCriteria;
import eventsearch.SearchCriteriaBuilder;
import eventsearch.SearchHelper;
import eventsearch.Util;

/**
 *	ATND検索の検索条件を格納するクラス
 */
public class ATNDSearchCriteriaBuilder extends SearchCriteriaBuilder
{
	// その他の定数
	/** ATND検索APIに使うエンコーディング */
	protected static final String REQUEST_ENCODING = "UTF-8";

	/** ATND検索APIで取得する件数 */
	public static final int MAXIMUM_FETCH_COUNT = 100;

	/**	ATND検索APIの年月指定フォーマット */
	protected static final SimpleDateFormat YYYYMM_FORMATTER = new SimpleDateFormat("yyyyMM");

	/**
	 * ATND検索APIのパラメータを組み立てる
	 * @return ATND検索APIのパラメータ 
	 */
	public String buildCondition(SearchCriteria criteria)
	{
		try
		{
			StringBuffer strCondition = new StringBuffer();

			if(criteria.getEventId() != null)
			{
				strCondition.append("event_id=");
				strCondition.append(criteria.getEventId());
			}
			else
			{
				// 最大取得件数
				strCondition.append("count=");
				strCondition.append(MAXIMUM_FETCH_COUNT);

				// 取得開始位置
				strCondition.append("&start=");
				strCondition.append(criteria.getStart());

				// 検索対象月
				strCondition.append("&ym=");
				if(!Util.isStringNull(criteria.getTargetMonth()))
				{
					strCondition.append(criteria.getTargetMonth().replace("/", ""));
				}
				else
				{
					// 指定なしの場合は、むこう三ヶ月を検索する
					Calendar[] cals = SearchHelper.getMonthList(3);
					
					StringBuffer condYm = new StringBuffer();
					for(Calendar cal : cals)
					{
						if(condYm.length() != 0)
						{
							condYm.append(",");
						}
						condYm.append(YYYYMM_FORMATTER.format(cal.getTime()));
					}
					strCondition.append(condYm.toString());
				}

				// キーワード
				if(!Util.isStringNull(criteria.getKeyword()))
				{
					strCondition.append("&keyword_or=");
					String keyword = criteria.getKeyword();
					int spaceIndex = keyword.indexOf(" ");
					if(spaceIndex != -1) {
						keyword = keyword.substring(0, spaceIndex);
					}
					if(!keyword.startsWith("-")) {
						strCondition.append(URLEncoder.encode(keyword, REQUEST_ENCODING));
					}
				}
			}

			return strCondition.toString();
		}
		catch(UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}
}