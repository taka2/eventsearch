package eventsearch;

/**
 * ATND検索の検索条件を格納するクラス
 */
public class SearchCriteria {
	// 検索条件：曜日の定数
	/** 検索条件：曜日：平日 */
	public static final int TARGET_DAY_OF_WEEK_WEEKDAY = 1;

	/** 検索条件：曜日：土日 */
	public static final int TARGET_DAY_OF_WEEK_WEEKEND = 2;

	/** 検索条件：曜日：全て */
	public static final int TARGET_DAY_OF_WEEK_ALL = 3;

	// 検索条件：出力フォーマット
	/** 検索条件：出力フォーマット：RSS */
	public static final String OUTPUT_FORMAT_RSS = "rss";

	/** 検索条件：出力フォーマット：JSON */
	public static final String OUTPUT_FORMAT_JSON = "json";

	// フィールド
	/** 検索条件：キーワード */
	private String keyword;

	/** 検索条件：検索対象月(YYYY/MM) */
	private String targetMonth;

	/** 検索条件：曜日 */
	private int targetDayOfWeek;

	/** 検索条件：時間(From) */
	private String targetTimeFrom;

	/** 検索条件：時間(To) */
	private String targetTimeTo;

	/** 検索条件：住所 */
	private String address;

	/** 検索条件：空席有のみ */
	private boolean isOnlyExistsVacantSeat;

	/** 検索条件：出力フォーマット */
	private String outputFormat;

	/** 検索条件：スタート位置 */
	private int start;

	/** 検索条件：イベントID */
	private String eventId;

	public SearchCriteria() {
		this.targetDayOfWeek = TARGET_DAY_OF_WEEK_ALL;
		this.isOnlyExistsVacantSeat = false;
		this.start = 1;
	}

	/**
	 * @return address を返します。
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            address をセットします。
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return isOnlyExistsVacantSeat を返します。
	 */
	public boolean isOnlyExistsVacantSeat() {
		return isOnlyExistsVacantSeat;
	}

	/**
	 * @param isOnlyExistsVacantSeat
	 *            isOnlyExistsVacantSeat をセットします。
	 */
	public void setOnlyExistsVacantSeat(boolean isOnlyExistsVacantSeat) {
		this.isOnlyExistsVacantSeat = isOnlyExistsVacantSeat;
	}

	/**
	 * @return keyword を返します。
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            keyword をセットします。
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return targetMonth を返します。
	 */
	public String getTargetMonth() {
		return targetMonth;
	}

	/**
	 * @param targetMonth
	 *            targetMonth をセットします。
	 */
	public void setTargetMonth(String targetMonth) {
		this.targetMonth = targetMonth;
	}

	/**
	 * @return targetTimeFrom を返します。
	 */
	public String getTargetTimeFrom() {
		return targetTimeFrom;
	}

	/**
	 * @param targetTimeFrom
	 *            targetTimeFrom をセットします。
	 */
	public void setTargetTimeFrom(String targetTimeFrom) {
		this.targetTimeFrom = targetTimeFrom;
	}

	/**
	 * @return targetTimeTo を返します。
	 */
	public String getTargetTimeTo() {
		return targetTimeTo;
	}

	/**
	 * @param targetTimeTo
	 *            targetTimeTo をセットします。
	 */
	public void setTargetTimeTo(String targetTimeTo) {
		this.targetTimeTo = targetTimeTo;
	}

	/**
	 * @return targetDayOfWeek を返します。
	 */
	public int getTargetDayOfWeek() {
		return targetDayOfWeek;
	}

	/**
	 * @param targetDayOfWeek
	 *            targetDayOfWeek をセットします。
	 */
	public void setTargetDayOfWeek(int targetDayOfWeek) {
		this.targetDayOfWeek = targetDayOfWeek;
	}

	/**
	 * @return outputFormat を返します。
	 */
	public String getOutputFormat() {
		return outputFormat;
	}

	/**
	 * @param outputFormat
	 *            outputFormat をセットします。
	 */
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	/**
	 * @return start を返します。
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start
	 *            start をセットします。
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return eventId を返します。
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            eventId をセットします。
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
}