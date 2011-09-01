package eventsearch;

import java.util.Comparator;

/**
 * SearchResultを開始日時の降順に並べ替えるComparator
 */
public class SearchResultComparator implements Comparator<SearchResult> {
	/**
	 * 順序付けのために 2 つの引数を比較します。
	 * 
	 * @param o1 比較対象の最初のオブジェクト
	 * @param o2 比較対象の 2 番目のオブジェクト
	 * @return 最初の引数が 2 番目の引数より小さい場合は負の整数、両方が等しい場合は 0、最初の引数が 2 番目の引数より大きい場合は正の整数
	 */
	public int compare(SearchResult o1, SearchResult o2)
	{
		return o1.getStartedAt().compareTo(o2.getStartedAt()) * (-1);
	}
	/**
	 * ほかのオブジェクトがこのコンパレータと「等しい」かどうかを示します。
	 * 
	 * @param obj 比較対象の参照オブジェクト
	 * @return 指定されたオブジェクトもコンパレータであり、それがこのコンパレータと同じ順序付けを行う場合にだけ true
	 */
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}
}
