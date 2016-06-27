package googlesearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

// Google Web Search API の利用は現在は推奨されない
// This API is officially deprecated as of November 1, 2010. We encourage you to use the Custom Search API for an alternative solution.
// https://developers.google.com/web-search/docs/

// 下記ページにレスポンスに含まれるパラメータの詳細が載っている
// ただし、Google Custom Search API の概要なので注意
// https://developers.google.com/custom-search/json-api/v1/reference/cse/list?hl=ja
class GoogleResults {
	private ResponseData responseData;
	public ResponseData getResponseData() { return responseData; }
	public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
	public String toString() { return "ResponseData[" + responseData + "]"; }

	static class ResponseData {
		private List<Result> results;
		public List<Result> getResults() { return results; }
		public void setResults(List<Result> results) { this.results = results; }
		public String toString() { return "Results[" + results + "]"; }
	}

	static class Result {
		private String url;
		private String title;

		public String getUrl() { return url; }
		public String getTitle() { return title; }
		public void setUrl(String url) { this.url = url; }
		public void setTitle(String title) { this.title = title; }
		public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
	}
}

/**
 *
 * @author Yuichi Yamada(m5191135@u-aizu.ac.jp)
 * @param
 * @return
 */
public class GoogleSearch {
	// 64(API search page limit)*16(domain string amount) = 1024(The theoretical upper limit)
	// SEARCH_WORDS.get([domain_name]).size() => 16
	// SEARCH_WORDS.size() => 6
	@SuppressWarnings("serial")
	static final HashMap<String, ArrayList<String>> SEARCH_DOMAIN_AND_WORDS =
	new HashMap<String, ArrayList<String>>() {
		{
			// Example data (translation)
			// drama = {"drama", "historical-drama", "TV-drama", "NHK-drama", "Japan-drama", "drama-watching", "drama-video" }
			// economy = {"economy", "exchange", "stock price", "stock", "Tokyo-Stock-Exchange", "Nikkei-average", "Japan-economy" }
			// sports = { "sports", "baseball", "football", "tennis", "ski", "skate", "kendo" }
			// radiation = { "radiation", "radioactivity", "nuclear-power-plant", "nuclear-power", "nuked", "radioactive-contamination", "radiation-harmful-rumors"}
			// weather = { "sunny", "rainy heaby-rain", "cloudy", "snow heavy-snow", "typhoon", "mild-winter", "severe-cold"}
			// politics = { "politics", "local-autonomy", "ruling-party", "opposition", "parliament", "constitution", "house-of-representatives"}
			put("drama", new ArrayList<>(Arrays.asList("ドラマ", "時代劇",   "テレビドラマ", "NHKドラマ", "ドラマ 日本", "ドラマ 視聴", "ドラマ ソフト", "","","","","","","","","")));
			put("economic", new ArrayList<>(Arrays.asList("経済",   "為替",     "株価", 		"株式", 	 "東証", 	 	"日経平均",    "日本 経済", "東証 TOPIX", "原油高", "製造業", "円高", "円高", "輸出", "輸入", "関税", "TPP")));
			put("sports", new ArrayList<>(Arrays.asList("野球",   "サッカー", "テニス", 		"スキー", 	 "スケート", 	"剣道",		   "スポーツ", "","","","","","","","",""  )));
			put("radiation", new ArrayList<>(Arrays.asList("放射線", "放射能", 	"原発", 		"原子力", 	 "被爆", 	 	"放射能 汚染", "放射能 風評被害", "チェルノブイリ","福島第一","原発","電力会社","東京電力","モニタリングポスト","ベクレル","空間線量","シーベルト"  )));
			put("weather", new ArrayList<>(Arrays.asList("晴れ", 	"雨 大雨", 	"曇り", 		"雪 大雪", 	 "台風",     	"暖冬", 	   "酷寒", "地震","大地震","雷","落雷", "竜巻","異常気象","温暖化", "海面上昇", "天気予報","週間天気" )));
			put("politics", new ArrayList<>(Arrays.asList("政治", 	"地方自治", "与党", 		"野党", 	 "国会",     	"憲法", 	   "衆議院", "","","","","","","","","" )));
		}
	};
	static final int GSEARCH_RESOLUSION = 8; // max value is 8 (api specification)
	static final int ARTICLE_AMOUNT_MAXIMUM = SEARCH_DOMAIN_AND_WORDS.get("economic").size()*GSEARCH_RESOLUSION*GSEARCH_RESOLUSION; // = 16*8*8 = 16*64 = 1024

	public static void main(String[] args) throws Exception {
		//System.out.println(ARTICLE_AMOUNT_MAXIMUM);
		// If you input args[0] = "drama", args[2] = 320, then the programa
		// for debug test setting
		//if(args.length < 3) args = new String[]{ "economic", "windows", "1000", "-d"}; // "t"ext "d"debug option
		if(!(args.length > 3 && args[3].equals("-td"))) {
			try {

				String hostAdress = "163.143.92.164";//InetAddress.getLocalHost().getHostAddress();
				String charset = "utf-8";


				if(args.length < 3)
					throw new IllegalAccessException("\nToo few arguments.\nUsage: GoogleSearch [domain name(drama|economy|sports|radiation|weather|politics)]" +
							"[Amount of article(max="+ARTICLE_AMOUNT_MAXIMUM+")]");

				ArrayList<String> searchDomain = SEARCH_DOMAIN_AND_WORDS.get(args[0].toLowerCase());
				int totalFileAmount = 1;

				if(searchDomain==null || searchDomain.isEmpty())
					throw new IllegalArgumentException("\nThe input domain \"" + args[0] + "\" is not valid name.\n" +
							"Correct domain names are (drama|economy|sports|radiation|weather|politics)");

				// If the requirement amount is over the limit, then amount is replace by ARTICLE_AMOUT_MAXIMUM
				if(Integer.valueOf(args[2]) > ARTICLE_AMOUNT_MAXIMUM) {
					System.out.println("Warning: The required amount is too higher.");
					args[2] = Integer.toString(ARTICLE_AMOUNT_MAXIMUM);
				}

				for(int wordIdx = 0; wordIdx < searchDomain.size(); wordIdx++) {
					// Using one word from domain array.
					String target = searchDomain.get(wordIdx);

					//System.out.println("Target String:"+"\""+target+"\"");
					// 最終的にmax(queryIdx)*GSEARCH_RESOLUSION=検索セット*サイズ分の検索結果が得られる

					// 検索セ繝?ト??ｮサイズを表示?ｼ??Nエリオプションrsz=largeをして縺?なければ繝?フォルトでは4?ｼ?
					// int GSEARCH_RESOLUSION = results.getResponseData().getResults().size();

					// Google Ajax Seach APIの仕讒?(荳?回につ縺?10ペ??ｼジ=10検索サイズ、合險?64件まで)
					// Finally, we can get the search results (search set size = max(queryIdx)*GSEARCH_RESOLUSION)
					for(int queryIdx = 0; queryIdx < GSEARCH_RESOLUSION; queryIdx++) {
						// 途中でリクエストパラメータにユーザーのIPを指定してスパム認定を回避する
						// 参考: http://okozukai39.seesaa.net/article/180872448.html
						// 今回は日本のページのみを対象とするのでhl=jaをオプション設定し国内のページだけを検索する
						// 参考: http://www.garunimo.com/program/linux/linux43.xhtml
						String googleSearchQuery = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&hl=ja&rsz="
								+ GSEARCH_RESOLUSION // A size of one time search unit
								+ "&start=" + (queryIdx*GSEARCH_RESOLUSION) // Search start index will incerase by this option
								+ "&userip=" + hostAdress // To avoid the spam certification by Google
								+ "&q="; 				  // The &q option means target search string

						URL url = new URL(googleSearchQuery + URLEncoder.encode(target, "utf-8"));
						Reader reader = new BufferedReader(
								new InputStreamReader(
										url.openStream(), charset));
						GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
						// for debug usage
						// String entireResult = results.getResponseData().getResults().toString();
						// System.out.println(entireResult);

						// If you set the argument of restlts.get(n), get the #n's search result. In case of n=0 it is top result.
						// get()に入れる引数を指定することで検索第n位を取り出せる。n=0なら??ｰトップ??ｮ検索結果
						//for(int pageIdx = 0; pageIdx < GSEARCH_RESOLUSION; pageIdx++) {
						for(int pageIdx = 0; pageIdx < results.getResponseData().getResults().size(); pageIdx++) {
							// Show title and URL of #(pageIdx) result.
							//System.out.println((pageIdx+queryIdx*GSEARCH_RESOLUSION)+":"+results.getResponseData().getResults().get(pageIdx).getTitle());
							String obtainUrl = results.getResponseData().getResults().get(pageIdx).getUrl();
							//System.out.println(obtainUrl);

							//Boolean result = ReadHTMLWriteFile(obtainUrl, (pageIdx+queryIdx*GSEARCH_RESOLUSION)+".html");

							// URL Decoding by utf-8 for domain contains Japanese characters (Internationalized domain name)
							// ex.) https://ja.wikipedia.org/wiki/会津
							//Boolean result = ReadHTMLWriteFile(URLDecoder.decode(obtainUrl, charset), (pageIdx+queryIdx*GSEARCH_RESOLUSION)+".html");
							Boolean result = HtmlUtilities.ReadHTMLWriteFile(URLDecoder.decode(obtainUrl, "utf-8"), target+"/"+totalFileAmount+".html", args[0].toLowerCase(),args[1]);

							if(result) {
								System.out.println("Total file: " + totalFileAmount);
								totalFileAmount++;
							}
							// 不正な処逅?が行われた(=htmlファイルが生成されなかっ縺?)場合???
							// ファイル名??ｮ連番を保証するために失敗ファイルの蛻?Indexを減ら縺?
							//if(totalFileAmount > Integer.valueOf(args[2])) break;
						} // End of for(PageIdx)
						//System.out.println("Query No."+queryIdx+" is ended.");
						//if(totalFileAmount >= Integer.valueOf(args[2])) break;
					} // End of for(queryIdx)
					System.out.println("Searching sequence for \"" + target + "\" is end.");
					HtmlUtilities.ReadLocalHTMLWriteTextFile(args[0].toLowerCase()+"/"+searchDomain.get(wordIdx), args[1]);

					if(totalFileAmount >= Integer.valueOf(args[2])) break;

					long waitMoment = 30*60000+1000; // = {(X*60000msec => X*60sec => Xmin)} + 1sec
					Date currentTime = new Date();
					System.out.println("Wait a minute " + waitMoment/1000.0 + " [sec] from " + currentTime.toString() + " (this interval for api limitation)");
					Thread.sleep(waitMoment);
				} // End of for(wordIdx)
			} catch(IllegalArgumentException e) {
				System.out.println(e.toString());
				System.exit(-1); // Failure exit
			} catch(NullPointerException e) {
				System.out.println("クエリの要求数がAPIの上限数(=64)を超えました。クローリング処理を終了します");
				System.out.println("All of crawling sequences is failure exit.");
			} finally {
			}
			System.out.println("All of crawling sequences is end.");
			System.out.println("Text generate sequence started...");

			Date currentTime = new Date();
			System.out.println("Wait a minute from " + currentTime.toString());

		//	HtmlUtilities.ReadLocalHTMLWriteTextFile(args[0].toLowerCase()+"/"+SEARCH_DOMAIN_AND_WORDS.get(args[0]).get(wordIdx), args[1]);

			currentTime = new Date();
			System.out.println("All of text file output sequence is end, " + currentTime.toString());
			System.exit(1);
		}
		else {
			Date currentTime = new Date();
			System.out.println("Wait a minute from " + currentTime.toString());
			HtmlUtilities.ReadLocalHTMLWriteTextFile(args[0].toLowerCase(), args[1]);
			currentTime = new Date();
			System.out.println("All of text file output sequence is end, " + currentTime.toString());
			System.exit(1);
		}

	}
}