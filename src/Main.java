import eventsearch.SearchCriteria;
import eventsearch.SearchResult;
import eventsearch.partake.PartakeSearchRequester;

public class Main {
	public static void main(String[] args) throws Exception {
		SearchCriteria criteria = new SearchCriteria();
		SearchResult[] result = PartakeSearchRequester.request(criteria);
		for(SearchResult r : result) {
			System.out.println(r);
		}

	}
}
