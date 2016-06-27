import newssearch.GetHtml;
import newssearch.NewsSiteHtmlParser;
import googlesearch.GoogleSearch;

public class AutomaticController {

	public static void main(String[] args) throws Exception {

		args = new String[] { "reuters", "sports", "windows", "1000"};

		String newsCite = args[0];

		if (newsCite == "google") {
			changeArgs(args);
			// googleからデータ取得
			GoogleSearch.main(args);
		} else if (newsCite == "excite") {
			// エキサイトからデータ取得
			exciteSearch(args);
		} else if (newsCite == "asahi") {
			// 朝日からデータ取得
			asahiSearch(args);
		} else if (newsCite == "yomiuri") {
			// 読売からデータ取得
			yomiuriSearch(args);
		}else if(newsCite=="reuters"){
			reutersSearch(args);
		}else
			System.out.println("ニュースサイトの入力ミス");
	}

	private static String[] changeArgs(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<3;i++){
			args[i] = args[i+1];
		}
		return args;
	}

	//読売からデータ取得
	//取得データの件数は指定できない
	private static void yomiuriSearch(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// 読売新聞からデータ取得
		 GetHtml.main("yomiuri", args[1], args[2], 1);	 
		// 読売新聞からデータ取得
		NewsSiteHtmlParser.main("yomiuri", args[1], args[2]);
	}

	//朝日からデータ取得
	//取得データの件数は指定できない
	private static void asahiSearch(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// 朝日新聞からデータ取得
		GetHtml.main("asahi", args[1], args[2], 1);
		// 朝日新聞のデータを形態素解析
		NewsSiteHtmlParser.main("asahi", args[1], args[2]);
	}

	//エキサイトからデータ取得
	//取得データの件数は指定できる
	private static void exciteSearch(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// エキサイトニュースのデータを取得
		GetHtml.main("excite",  args[1], args[2], Integer.parseInt(args[3]));
		// エキサイトニュースのデータを形態解析
		NewsSiteHtmlParser.main("excite", args[1],args[2]);
	}
	private static void reutersSearch(String[] args)throws Exception{
		
		GetHtml.main("reuters", args[1], args[2], Integer.parseInt(args[3]));
		//NewsSiteHtmlParser.main("reuters", args[1],args[2]);
	}
}
