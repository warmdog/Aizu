package newssearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//現在の日付からかこ一ヶ月の記事を取得し、それぞれの日の記事をhtmlファイルとして出力
public class GetHtml {
	private static File newfile;

	public static void main(String newsCite, String newsDomain, String OS, int expectNumber) {
		int numberYomiuriHtml;
		int numberAasahiHtml;

		if(newsCite == "excite"){
		// エキサイトニュースからデータ取得
		getExcite(newsDomain, OS, expectNumber);
		}else if(newsCite == "yomiuri"){
		// 読売から取得したhtmlの件数を取得
		 getYomiuri(newsDomain, OS);
		}else if(newsCite == "asahi"){
		// 朝日から取得したhtmlの件数を取得
		 getAsahi(newsDomain, OS);
		}
	}

	//URLからテキストデータをファイルに書き込み
	private static void getArticle(List<String> URLs, String newsDomain, String newsCite, String OS) {
		int numberHtml = 1;
		for (String str : URLs) {
			createfile(str, numberHtml, newsDomain, newsCite, OS);
			numberHtml++;
			if (numberHtml > 100)
				break;
		}
	}

	//朝日新聞からデータを取得する
	public static void getAsahi(String newsDomain, String OS) {
		List<String> asahiHtml;
		String asahiURL = "http://www.asahi.com/";
		// ディレクトリ作成
		createDirectory(newsDomain, "asahi", OS);

		// ドメイン毎にURLの設定を行う
		if (newsDomain == "sports")
			asahiURL += "sports/general/";
		else if (newsDomain == "politics")
			asahiURL += "politics/list/";
		else if (newsDomain == "economic")
			asahiURL += "business/list/";
		else if (newsDomain == "radiation") {
			asahiURL += "/special/energy/nuclearpower/";
		}
		//記事一覧の取得
		asahiHtml = Getlink.getlinkAsahi(asahiURL, newsDomain);
		//記事一覧からテキストデータを取得する
		getArticle(asahiHtml, newsDomain, "asahi", OS);
	}

	//読売新聞からデータを取得する
	public static void getYomiuri(String newsDomain, String OS) {
		List<String> YomiuriHtml = null;
		String DirectoryName = newsDomain;
       if (newsDomain == "economic")
			newsDomain = "economy";
		// ディレクトリ作成
		createDirectory(DirectoryName, "yomiuri", OS);
		// 読売からデータを取得
		// スポーツの記事を取得
		YomiuriHtml = Getlink.getlinkYomiuri("http://www.yomiuri.co.jp/" + newsDomain + "/", newsDomain);
		// 読売のすべての記事を取得
		getArticle(YomiuriHtml, DirectoryName, "yomiuri", OS);
	}

	// エキサイトニュースからニュースを取得
	public static void getExcite(String newsDomain, String OS, int expectNumber) {
		List<String> exciteHtml = null;
		int a = 1;
		// ディレクトリ作成
		createDirectory(newsDomain, "excite", OS);
		String URL="http://www.excite.co.jp/News/";
		//スポーツの設定
		if(newsDomain == "sports")URL += "sports_g/";
		//政治
		else if(newsDomain == "politics")URL += "politics_g/";
		//経済
		else if(newsDomain == "economic")URL += "economy_g/";
		//ドラマ
		else if(newsDomain == "drama")URL += "tv/";
		//天気
		else if(newsDomain == "wheather")URL += "weather/";
		
		for (int i = 1;; i++) {
			// エキサイトニュースからデータを取得
			exciteHtml = Getlink.getlink(URL + getdate(i), getdate(i),
					newsDomain);
			for (String str : exciteHtml) {
				createfile(str, a, newsDomain, "excite", OS);
				a++;
				System.out.println("fileNumber = "+a);
				if (a > expectNumber)
					break;
			}
			if (a > expectNumber)
				break;
		}
	}

	//ディレクトリの作成を行う
	public static void createDirectory(String newsDomain, String newsCite, String OS) {
		File newfile1 = new File("");
		File newfile2 = new File("");
		if (OS == "linux") {
			// ディレクトリ作成
			newfile1 = new File(System.getProperty("user.home") + "/デスクトップ/htmlData/" + newsCite + "/" + newsDomain);
			newfile2 = new File(System.getProperty("user.home") + "/デスクトップ/txtData/" + newsCite + "/" + newsDomain);
		} else {
			newfile1 = new File(System.getProperty("user.home") + "/Desktop/htmlData/" + newsCite + "/" + newsDomain);
			newfile2 = new File(System.getProperty("user.home") + "/Desktop/txtData/" + newsCite + "/" + newsDomain);
		}
		newfile1.mkdirs();
		newfile2.mkdirs();
	}

	//現在時刻から指定された日を引く関数
	//エキサイトニュースのみ使用
	public static String getdate(int a) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -a);
		Date yes = cal.getTime();
		return sdf.format(yes);
	}

	//htmlデータを作成
	public static void createfile(String ht, int page, String newsDomain, String newsCite, String OS) {
		try {
			// url取得
			URL url = new URL(ht);
			BufferedReader check = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			// file名主得意
			String name = page + ".html";
			File file = new File("");
			if (OS == "linux") {
				// 書き込み用ファイルの準備
				file = new File(System.getProperty("user.home") + "/デスクトップ/htmlData/" + newsCite + "/" + newsDomain
						+ "/" + name);
			} else {
				file = new File(System.getProperty("user.home") + "/Desktop/htmlData/" + newsCite + "/" + newsDomain
						+ "/" + name);
			}
			FileWriter filewriter = new FileWriter(file);
			String line;
			// ファイル書き込み
			while ((line = check.readLine()) != null) {
				filewriter.write(line);
			}
			filewriter.close();
			check.close();
		} catch (FileNotFoundException ex){
			System.out.println(ht+" が取得できませんでした。手動でhtmlを取得してください");
		} catch (MalformedURLException ex) {
			System.out.println("URL¥u304c¥u4e0d¥u6b63¥u3067¥u3059¥u3002");
			ex.printStackTrace();
		} catch (UnknownHostException ex) {
			System.out.println("¥u30b5¥u30a4¥u30c8¥u304c¥u898b¥u3064¥u304b¥u308a¥u307e¥u305b¥u3093¥u3002");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
