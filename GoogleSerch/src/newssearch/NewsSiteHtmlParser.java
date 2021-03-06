/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newssearch;

//input directoryから複数のドキュメントを取得し、形態素解析を行う
//ex)経済directoryから経済のニュースを取得し、形態素解析を行う
//ディレクトリに存在する分をそのままoutputディレクトリに形態素解析の結果を反映する

/**
 *
 * @author student
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utilities.MultiByteCharacterChecker;
import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;

public class NewsSiteHtmlParser {

	public static void main(String newsSite, String newsDomain, String OS)
			throws Exception {
		// 辞書ディレクトリを引数で指定
		Tagger tagger = new Tagger("ipadic");
		String outname = null;
		File dir1 = new File("");

		if (OS == "linux") {
			dir1 = new File(System.getProperty("user.home")
					+ "/デスクトップ/htmlData/" + newsSite + "/" + newsDomain + "/");
			// dir1 = new File(System.getProperty("user.home") +
			// "/デスクトップ/htmlData/excite/raw_html/");
			outname = System.getProperty("user.home") + "/デスクトップ/txtData/"
					+ newsSite + "/" + newsDomain + "/";
		} else {
			dir1 = new File(System.getProperty("user.home")
					+ "/Desktop/htmlData/" + newsSite + "/" + newsDomain + "/");
			outname = System.getProperty("user.home") + "/Desktop/txtData/"
					+ newsSite + "/" + newsDomain + "/";
		}
		File[] file1 = dir1.listFiles();

		BufferedReader br = null;
		String fileName = "";
		for (int i = 1; i <= file1.length; i++) {
			StringBuffer buf = new StringBuffer();
			try {
				// 入力元ファイル
				File file = new File(file1[i-1].getPath());
				fileName = file.getName().replaceAll("[.].*?$", "");
				// 改行タグチェック
				int tag = 0;
				// javascriptチェック
				int JS = 0;

				InputStreamReader in = new InputStreamReader(
						new FileInputStream(file), "UTF-8");
				br = new BufferedReader(in);
				String line;

				while ((line = br.readLine()) != null) {
					// exciteニュース
					if(newsSite == "excite")
					line = line.split("articleBody")[1].split("</script>")[0];
					// Yomiuri、Asahi
					else if(newsSite == "yomiuri" || newsSite == "asahi")
					 line = line.split("<meta name=\"description\" content=\"")[1].split("itemprop=\"description\" />")[0];
					buf.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
			List<Morpheme> parsed = tagger.parse(buf.toString());
			List<String> wakati = new ArrayList<String>();
			for (Morpheme morph : parsed) {
				if (morph.feature.startsWith("名詞")) {
					if (morph.feature.indexOf("数") == -1
							&& morph.feature.indexOf("非自立") == -1
							&& morph.feature.indexOf("サ変接続") == -1) {
						wakati.add(morph.surface);
					}
				}
			}

			FileOutputStream xyz = new FileOutputStream(outname + fileName+ ".txt");
			OutputStreamWriter out = new OutputStreamWriter(xyz, "UTF-8");
			if (!wakati.isEmpty()) {
				for (String str : wakati) {
					if ((new MultiByteCharacterChecker().isZenkakuOnly(str))) {
						out.write(str + "\n");
					}
				}
				out.close();
			}
		}
		System.out.println("Finish!!!!!!!!!!!");
	}

	// pタグの要素に含まれるHTMLのタグを取り除くクラス
	public static String HtmlTagRemover(String str) {
		char x = '\n';
		// 文字列のすべてのタグを取り除く
		str = str.replaceAll("<.+?>", " ");
		str = str.replaceAll("RT ", " ");
		str = str.replaceAll("[０-９]", " ");
		str = str.replaceAll("[a-zA-Z0-9].+/.+[a-zA-Z0-9]+", " ");
		str = str.replaceAll(".+to/[a-zA-Z0-9]+", " ");
		str = str.replaceAll("http.+? ", " ");
		str = str.replaceAll("http.+[/a-zA-Z0-9]+", " ");
		str = str.replaceAll("http", " ");
		str = str.replaceAll("www", " ");
		str = str.replaceAll("co", " ");
		str = str.replaceAll("@.+? ", " ");
		str = str.replaceAll("@.+?" + x, " ");
		str = str.replaceAll("/d", " ");
		str = str.replaceAll("ーー", "ー");
		// str = str.replcaeAll("()")
		str = str.replaceAll("[、。「」　■□**?）（)(⇒]", " ");

		return str;
	}

	// Pタグ,brタグの要素抽出クラス
	public static String GetPTag(String html) {
		StringBuffer newHtml = new StringBuffer(html.length());
		String pattern = "twitter.com/([^\"]+)\">";
		Pattern elementPattern2 = Pattern.compile(pattern,
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher2 = elementPattern2.matcher(html);
		while (matcher2.find()) {
			String content = matcher2.group(1);
			// System.out.println(content);
			newHtml.append(content);
			newHtml.append('\n');
		}
		return newHtml.toString();
	}
}

// 指定した文字コードでhtmlを取得するクラス
class MyFileUtil {
	@SuppressWarnings("finally")
	public static StringBuffer fileGetContents(String url, String encode) {
		StringBuffer buffer = new StringBuffer();
		try {
			InputStream is = new URL(url).openStream();
			InputStreamReader isr = new InputStreamReader(is, encode);
			BufferedReader in = new BufferedReader(isr);
			String s = null;
			while ((s = in.readLine()) != null) {
				buffer.append(s).append("\n");
			}
		} catch (Exception e) {
			// System.out.println(e.toString());
			buffer = null;
		} finally {
			return buffer;
		}
	}

	// UTF-8として取得※他の文字コードでは文字化けする
	public static StringBuffer fileGetContents(String url) {
		return fileGetContents(url, "Shift_JIS");
	}
}
