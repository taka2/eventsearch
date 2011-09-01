package eventsearch;

/**
 *	ATND検索の検索結果を格納するクラス
 */
public class SearchResultForJSON
{
	/** イベントソース */
	private String eventSource;
	/** イベントID */
	private String eventId;
	/** タイトル */
	private String title;
	/** ATNDのURL */
	private String eventUrl;
	/** イベント開催日時 */
	private String startedAt;
	/** イベント終了日時 */
	private String endedAt;
	/** 開催場所 */
	private String address;
	/** 開催会場 */
	private String place;

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
	 * @return endedAt を返します。
	 */
	public String getEndedAt() {
		return endedAt;
	}

	/**
	 * @param endedAt
	 *            endedAt をセットします。
	 */
	public void setEndedAt(String endedAt) {
		this.endedAt = endedAt;
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

	/**
	 * @return eventUrl を返します。
	 */
	public String getEventUrl() {
		return eventUrl;
	}

	/**
	 * @param eventUrl
	 *            eventUrl をセットします。
	 */
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	/**
	 * @return place を返します。
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place
	 *            place をセットします。
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return startedAt を返します。
	 */
	public String getStartedAt() {
		return startedAt;
	}

	/**
	 * @param startedAt
	 *            startedAt をセットします。
	 */
	public void setStartedAt(String startedAt) {
		this.startedAt = startedAt;
	}

	/**
	 * @return title を返します。
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            title をセットします。
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return eventSource を返します。
	 */
	public String getEventSource() {
		return eventSource;
	}

	/**
	 * @param eventSource
	 *            eventSource をセットします。
	 */
	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}
}
