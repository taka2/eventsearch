package eventsearch;

/**
 * 検索条件を構築するクラス
 */
public abstract class SearchCriteriaBuilder {
	/**
	 * 検索処理のパラメータを組み立てる
	 * 
	 * @return 検索処理のパラメータ
	 */
	public abstract String buildCondition(SearchCriteria criteria);
}