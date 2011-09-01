package eventsearch;

import java.io.Serializable;
import java.util.Calendar;

/**
 *	検索結果を格納するクラス
 */
public class SearchResult implements Serializable
{
	private static final long serialVersionUID = 1L;

	// ATND
	public static final int EVENT_SOURCE_ATND = 1;
	// Zusaar
	public static final int EVENT_SOURCE_ZUSAAR = 2;
	// PARTAKE
	public static final int EVENT_SOURCE_PARTAKE = 3;

	/** イベントソース */
	private int eventSource;
	/** イベントID */
	private String eventId;
	/** タイトル */
	private String title;
	/** キャッチ */
	private String strCatch;
	/** 概要 */
	private String description;
	/** ATNDのURL */
	private String eventUrl;
	/** イベント開催日時 */
	private Calendar startedAt;
	/** イベント終了日時 */
	private Calendar endedAt;
	/** 参考URL */
	private String url;
	/** 定員 */
	private int limit;
	/** 開催場所 */
	private String address;
	/** 開催会場 */
	private String place;
	/** 開催会場の緯度 */
	private double lat;
	/** 開催会場の経度 */
	private double lon;
	/** 主催者のID */
	private String ownerId;
	/** 主催者のニックネーム */
	private String ownerNickname;
	/** 参加者 */
	private int accepted;
	/** 補欠者 */
	private int waiting;
	/** 更新日時 */
	private Calendar updatedAt;

	/**
	 * @return accepted を返します。
	 */
	public int getAccepted() {
		return accepted;
	}

	/**
	 * @param accepted
	 *            accepted をセットします。
	 */
	public void setAccepted(int accepted) {
		this.accepted = accepted;
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
	 * @return description を返します。
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            description をセットします。
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return endedAt を返します。
	 */
	public Calendar getEndedAt() {
		return endedAt;
	}

	/**
	 * @param endedAt
	 *            endedAt をセットします。
	 */
	public void setEndedAt(Calendar endedAt) {
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
	 * @return lat を返します。
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            lat をセットします。
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return limit を返します。
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            limit をセットします。
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return lon を返します。
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * @param lon
	 *            lon をセットします。
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}

	/**
	 * @return ownerId を返します。
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId
	 *            ownerId をセットします。
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return ownerNickname を返します。
	 */
	public String getOwnerNickname() {
		return ownerNickname;
	}

	/**
	 * @param ownerNickname
	 *            ownerNickname をセットします。
	 */
	public void setOwnerNickname(String ownerNickname) {
		this.ownerNickname = ownerNickname;
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
	public Calendar getStartedAt() {
		return startedAt;
	}

	/**
	 * @param startedAt
	 *            startedAt をセットします。
	 */
	public void setStartedAt(Calendar startedAt) {
		this.startedAt = startedAt;
	}

	/**
	 * @return strCatch を返します。
	 */
	public String getStrCatch() {
		return strCatch;
	}

	/**
	 * @param strCatch
	 *            strCatch をセットします。
	 */
	public void setStrCatch(String strCatch) {
		this.strCatch = strCatch;
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
	 * @return updatedAt を返します。
	 */
	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt
	 *            updatedAt をセットします。
	 */
	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return url を返します。
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            url をセットします。
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return waiting を返します。
	 */
	public int getWaiting() {
		return waiting;
	}

	/**
	 * @param waiting
	 *            waiting をセットします。
	 */
	public void setWaiting(int waiting) {
		this.waiting = waiting;
	}

	/**
	 * @return eventSource を返します。
	 */
	public int getEventSource() {
		return eventSource;
	}

	/**
	 * @param eventSource
	 *            eventSource をセットします。
	 */
	public void setEventSource(int eventSource) {
		this.eventSource = eventSource;
	}

	/**
	 * @return オブジェクトの文字列表現 
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("event-source = " + getEventSource() + "\n");
		sb.append("place = " + getPlace() + "\n");
		sb.append("lon = " + getLon() + "\n");
		sb.append("accepted = " + getAccepted() + "\n");
		sb.append("event-id = " + getEventId() + "\n");
		sb.append("updated-at = " + getUpdatedAt().getTime() + "\n");
		sb.append("title = " + getTitle() + "\n");
		if(getEndedAt() != null) {
			sb.append("ended-at = " + getEndedAt().getTime() + "\n");
		} else {
			sb.append("ended-at = null\n");
		}
		sb.append("waiting = " + getWaiting() + "\n");
		sb.append("event-url = " + getEventUrl() + "\n");
		sb.append("url = " + getUrl() + "\n");
		sb.append("owner-nickname = " + getOwnerNickname() + "\n");
		sb.append("catch = " + getStrCatch() + "\n");
		sb.append("description = " + getDescription() + "\n");
		sb.append("owner-id = " + getOwnerId() + "\n");
		sb.append("limit = " + getLimit() + "\n");
		sb.append("lat = " + getLat() + "\n");
		sb.append("address = " + getAddress() + "\n");
		sb.append("started-at = " + getStartedAt().getTime() + "\n");

		return sb.toString();
	}
}
