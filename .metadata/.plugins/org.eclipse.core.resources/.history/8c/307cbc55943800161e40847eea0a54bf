package newssearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//htmlファイルを受け取り、リンク（href）を抽出し、リストに入れて返す
public class Getlink {
	public static List<String> getlink(String ht, String date, String newsDomain){
		List<String> links = new ArrayList<String>();
		//パターンにニュースサイトの特徴となるURLを入力
		//String pattern = "<a href=\"([^\"]+)\"><";
		String pattern = "<a href=\"([^\"]+)\">.*?</a>";
			//"<a href" で始まりダブルクォートでない文字が一文字以上ある文字列を抽出
			//<a href= \" ( [ ^ \" ] + \">
			//a href= 
			// \" ←　これでダブルクォート
			// () ←　表現方法の一つ「この中で一つ」
			// ^ ←　否定　ダブるクォーテーション
			//
		try{
			//ht = "http://www.excite.co.jp/News/tv/20160122";
			URL url = new URL(ht);
			BufferedReader check = new BufferedReader(new InputStreamReader(url.openStream()));
			String kari;
			Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
			
			if(newsDomain == "sports")newsDomain = "sports_g";
			//政治
			else if(newsDomain == "politics")newsDomain = "politics_g";
			//経済
			else if(newsDomain == "economic")newsDomain = "economy_g";
			//ドラマ
			else if(newsDomain == "drama")newsDomain = "tv";
			//天気
			else if(newsDomain == "wheather")newsDomain = "weather";

			while((kari = check.readLine()) != null){
				//System.out.println(kari);
				Matcher mat = pat.matcher(kari);
				while(mat.find()){
					//System.out.println("mat.group(1) = "+mat.group(1));
					if(mat.group(1).contains("/News/"+newsDomain+"/" + date)){
						if(!mat.group(1).contains("http")){
							links.add("http://www.excite.co.jp" + mat.group(1));
						}
						else links.add(mat.group(1));
						//System.out.println("http://www.excite.co.jp" + mat.group(1));
					}
				}
			}
			check.close();
		}catch(IOException e){
			System.out.println(e);
		}
		return links;
	}
	
	public static List<String> getlinkAsahi(String ht,String newsDomain){
		List<String> links = new ArrayList<String>();
		System.out.println("URL="+ht);
		try{

			URL url = new URL(ht);
			BufferedReader check = new BufferedReader(new InputStreamReader(url.openStream()));
			String kari;
			String line;
			String patternString = "<li><a href=\"/articles/.+";
			Pattern domainPattern = 
					Pattern.compile(patternString);
			//文字列にhttp://www.yomiuri.co.jp/newsdomain/.*が含まれているか確認
			while((line = check.readLine()) != null){
				Matcher matcher = domainPattern.matcher(line);
				if(matcher.find()){
					//System.out.println("match point = "+"http://www.asahi.com"+matcher.group(0).split("\"")[1]);
					//条件にマッチしたURL部分をリンクに追加
					links.add("http://www.asahi.com"+matcher.group(0).split("\"")[1]);
				}
			}
			check.close();
		}catch(IOException e){
			System.out.println(e);
		}
		return links;
	}
	
	public static List<String> getlinkYomiuri(String ht,String newsDomain){
		List<String> links = new ArrayList<String>();
		System.out.println("ht"+ht);
		//パターンにニュースサイトの特徴となるURLを入力
		String pattern = "<a href=\"([^\"]+)\"><";
			//"<a href" で始まりダブルクォートでない文字が一文字以上ある文字列を抽出
			//<a href= \" ( [ ^ \" ] + \">
			//a href= 
			// \" ←　これでダブルクォート
			// () ←　表現方法の一つ「この中で一つ」
			// ^ ←　否定　ダブるクォーテーション
			//
		try{

			URL url = new URL(ht);
			BufferedReader check = new BufferedReader(new InputStreamReader(url.openStream()));
			String kari;
			String line;
			Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
			String patternString = "<a href=\"http://www.yomiuri.co.jp/"+newsDomain+"/.+/.+|"+ "<li><a href=\"http://www.yomiuri.co.jp/"+newsDomain+"/.+/.+"
			                        + "|<a href=\"http://www.yomiuri.co.jp/"+newsDomain+"/.+";
			Pattern domainPattern = 
					Pattern.compile(patternString);
			//文字列にhttp://www.yomiuri.co.jp/newsdomain/.*が含まれているか確認
			while((line = check.readLine()) != null){
				Matcher matcher = domainPattern.matcher(line);
				if(matcher.find()){
					//条件にマッチしたURL部分をリンクに追加
					links.add(matcher.group(0).split("\"")[1]);
				}
			}
			check.close();
		}catch(IOException e){
			System.out.println(e);
		}
		return links;
	}
}
