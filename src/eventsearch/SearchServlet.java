package eventsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eventsearch.atnd.ATNDSearchRequester;
import eventsearch.partake.PartakeSearchRequester;
import eventsearch.zusaar.ZusaarSearchRequester;

/**
 * 検索Servlet
 */
public class SearchServlet extends HttpServlet {
	static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		search(req, resp);
	}

	private void search(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String targetMonth = req.getParameter("target_month");
		String targetDayOfWeek = req.getParameter("target_day_of_week");
		int intTargetDayOfWeek = Integer.parseInt(targetDayOfWeek);
		String targetTimeFrom = req.getParameter("target_time_from");
		String targetTimeTo = req.getParameter("target_time_to");
		String address = req.getParameter("address");
		String keyword = req.getParameter("keyword");
		String onlyExistsVacantSeat = req
				.getParameter("only_exists_vacant_seat");
		boolean isOnlyExistsVacantSeat = onlyExistsVacantSeat != null
				&& ("true".equals(onlyExistsVacantSeat) || "on"
						.equals(onlyExistsVacantSeat));
		String outputFormat = req.getParameter("output_format");

		// TODO: JSON or RSSの場合はどうする？
		if (!Util.isHHMM(targetTimeFrom)) {
			req.setAttribute("message", "時間FromはHH:MM形式で入力してください。");
			req.getRequestDispatcher("/index_old.jsp").forward(req, resp);
			return;
		}
		if (!Util.isHHMM(targetTimeTo)) {
			req.setAttribute("message", "時間ToはHH:MM形式で入力してください。");
			req.getRequestDispatcher("/index_old.jsp").forward(req, resp);
			return;
		}

		try {
			// 検索条件の構築
			SearchCriteria criteria = new SearchCriteria();
			criteria.setTargetMonth(targetMonth);
			criteria.setTargetDayOfWeek(intTargetDayOfWeek);
			criteria.setTargetTimeFrom(targetTimeFrom);
			criteria.setTargetTimeTo(targetTimeTo);
			criteria.setAddress(address);
			criteria.setKeyword(keyword);
			criteria.setOnlyExistsVacantSeat(isOnlyExistsVacantSeat);
			criteria.setOutputFormat(outputFormat);

			// ATND APIの呼び出し
			SearchResult[] resultArray = ATNDSearchRequester.request(criteria);

			// 結果をリストに追加
			List<SearchResult> resultList = new ArrayList<SearchResult>();
			for (SearchResult result : resultArray) {
				resultList.add(result);
			}

			// Zusaar APIの呼び出し
			resultArray = ZusaarSearchRequester.request(criteria);

			// 結果をリストに追加
			for (SearchResult result : resultArray) {
				resultList.add(result);
			}

			// Partake iCalの呼び出し
			resultArray = PartakeSearchRequester.request(criteria);

			// 結果をリストに追加
			for (SearchResult result : resultArray) {
				resultList.add(result);
			}

			// 結果リストを配列に変換
			resultArray = resultList.toArray(new SearchResult[0]);

			// APIで絞り込みできない条件をさらに絞り込み
			resultArray = SearchResultProcessor.postFilter(resultArray,
					criteria);

			if (Util.isStringNull(criteria.getOutputFormat())) {
				// 開始日時でソート
				Arrays.sort(resultArray, new SearchResultComparator());
				req.setAttribute("resultArray", resultArray);
				req.getRequestDispatcher("/showResult.jsp").forward(req, resp);
			} else if (criteria.getOutputFormat().equals(
					SearchCriteria.OUTPUT_FORMAT_RSS)) {
				// UpdatedAtでソート
				Arrays.sort(resultArray, new SearchResultComparatorForUpdatedAt());
				// RSS出力
				SearchResultProcessor.toRSS(req, resp.getOutputStream(),
						criteria, resultArray);
			} else if (criteria.getOutputFormat().equals(
					SearchCriteria.OUTPUT_FORMAT_JSON)) {
				// 開始日時でソート
				Arrays.sort(resultArray, new SearchResultComparator());
				// JSON出力
				SearchResultProcessor.toJSON(resp.getOutputStream(),
						resultArray);
			} else {
				// 想定外フォーマット
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
