package googlesearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import utilities.MultiByteCharacterChecker;

class HtmlUtilities {
	final static String LINE_SEPARATOR_PATTERN = "\r\n|[\n\r\u2028\u2029\u0085]";
	final static String CASE_INSENSITIVE = "(?i)";
	final static String MULTILINE = "(?m)";

	@SuppressWarnings("resource")
	public static void ReadLocalHTMLWriteTextFile(String domainName, String os) throws IOException {
		boolean append = false;
		boolean auto_flush = true;
		Tagger tagger = new Tagger("ipadic");

		File inputDir = new File("");
		File outputDir = new File("");

		if(os == "windows"){
			inputDir = new File(System.getProperty("user.home") + "/Desktop/htmlData/google/" + domainName + "/");
			outputDir = new File(System.getProperty("user.home") + "/Desktop/txtData/google/" + domainName + "/");
		}else if(os == "linux"){
			inputDir = new File(System.getProperty("user.home") + "/デスクトップ/htmlData/google/" + domainName + "/");
			outputDir = new File(System.getProperty("user.home") + "/デスクトップ/txtData/google/" + domainName + "/");
		}

		if(outputDir != null && !outputDir.exists()) outputDir.mkdirs();

		File fi[] = inputDir.listFiles();
		File fo[] = outputDir.listFiles();
		if(fi == null) {
			System.out.println("Input file is not found!");
			System.exit(-1); // 入力ファイルが存在しない場合
		}

		BufferedReader br = null;

		for(int i = 0; i < fi.length; i++) {
		//for(int i = 90; i < 94; i++) {
			StringBuffer buf = new StringBuffer(16*256*8192);
			//File file = new File(inputDir+"/"+String.valueOf(i)+".html");
			File file = new File(fi[i].getPath());
			String fileName = file.getName().replaceAll("[.].*?$", "");

			InputStreamReader in = new InputStreamReader(new FileInputStream(file), "UTF-8");
			br = new BufferedReader(in, 256*8192);
			String line = "";

			while ((line = br.readLine()) != null) {
				// ここ(HtmlTagRemover)が実行上のボトルネック
				// 直後に全角文字だけを取り除く処理を行う関係上、
				// タグの個別削除をしなくても日本語の単語は取り出せる
				// (ただし記事本文とは関係ない日本語まで引っ張ってきてしまう)
				// line = HtmlTagRemover(line);

				// 01.16 解決：
				// ページによって一行に異常な量の空白ページが含まれていることが分かった。
				// そのようなページをそのままbufに格納すると、
				// tagger.parseを行う際にオーバーフローを引き起こしてしまう可能性がある。
				// line.trim()で先頭・末尾の不要な空白は除去する
				buf.append(line.trim());
			}

			List<Morpheme> parsed = tagger.parse(buf.toString());
			//if(parsed.size() < 0) throw new MyException();

			// System.out.println("parsed :" + parsed.size());
			List<String> wakati = new ArrayList<String>();
			for (Morpheme morph : parsed) {
				if (morph.feature.startsWith("名詞")
						// || morph.feature.startsWith("動詞")
						) {
					if (morph.feature.indexOf("数") == -1 && morph.feature.indexOf("非自立") == -1
							&& morph.feature.indexOf("サ変接続") == -1) {
						//System.out.println(morph.surface + " " + morph.feature);
						wakati.add(morph.surface);
					}
				}
			}

			//System.out.println(wakati);
			// わかち書きしたものを単語毎に改行表示
			String outname = outputDir.getPath();
			FileOutputStream xyz = new FileOutputStream(outname + "/" + fileName + ".txt");
			OutputStreamWriter out = new OutputStreamWriter(xyz, "UTF-8");
			if (!wakati.isEmpty()) {
				for (String str : wakati) {
					if((new MultiByteCharacterChecker().isZenkakuOnly(str)))
						out.write(str + "\n");
					//else
						//System.out.println(str);
				}
			}

			System.out.println(file.getPath() + " -> "+fileName+".txt");
			in.close();
			out.close();
			/*
						PrintWriter pw = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								Tnew FileOutputStream(
										fileName
										,append)
								,"utf-8"))
				,auto_flush);

		pw.print(3);
		if(pw != null) pw.close();
			 */
		}
	}

	public static Boolean ReadHTMLWriteFile(String obtainUrl, String outFileName, String domainName, String os) {
		try {
			String aHTML = new String("".getBytes("utf-8"), "utf-8");

			// 読み込んだ蜈?のhtmlの譁?字コードを識別し??ｼ納す繧?
			Document document = Jsoup.connect(obtainUrl).get();
			aHTML = document.html();
			String outPath = null;
			if(os == "windows") {
				outPath = System.getProperty("user.home") + "/Desktop/htmlData/google/" + domainName + "/";
			}
			else if(os == "linux") {
				outPath = System.getProperty("user.home") + "/デスクトップ/htmlData/google/" + domainName + "/";
			}

			boolean append = false;
			boolean auto_flush = true;

			File fi = new File(outPath+outFileName);
			File dir = fi.getParentFile();

			if(dir != null && !dir.exists()) dir.mkdirs();
			PrintWriter pw = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(
											fi
											,append)
									,"utf-8"))
					,auto_flush);
			pw.print(aHTML);
			if(pw != null) pw.close();
			return true;
		} catch (UnknownHostException e) {
			System.out.println("ホストのIPの解決ができませんでした"+obtainUrl);
			return false;
		} catch (MalformedURLException e) {
			System.out.println("不正な形式のファイルです"+obtainUrl);
			return false;
		} catch (IOException e) {
			return false;
		} finally {
		} // finally close
	}

	// 文字列のhtmlタグを取り除く
	//
	// 注意：
	// 		処理がかなり重く現状ではファイル生成に不具合を及ぼす原因だと思われる
	//		考えられる要因→
	//		１：処理する文字列長が大きすぎる
	//		２：正規表現式の使い方のまずさ(式の誤り、後負荷なMULTILINEオプションの使い過ぎetc)
	//		３：Java標準の正規表現ライブラリ(java.util.regex)の仕様?
	//
	// 問題点：
	// 		Ａ：正規表現の重複(=かけすぎ)で生成する文字列が空になり結果txtが作られないことがある
	// 		Ｂ：処理するhtmlファイルの数が増えるとorサイズが大きいと処理に非常に時間がかかることがある
	//
	// 参考：
	// 		http://d.hatena.ne.jp/n_shuyo/20111020/regular_expression
	// 		http://blog.cybozu.io/entry/8757
	public static String HtmlTagRemover(String str) {
		// 大文字小文字の区別をしないようにするフラグ用文字列
		// 適用する正規表現のパターン文字列の先頭にこれを連結させる
		// 複数行にパターンマッチを行うフラグ用文字列
/*
		str = str.replaceAll("<!--"+LINE_SEPARATOR_PATTERN+"*?.*?-->", " ");
		str = str.replaceAll(CASE_INSENSITIVE+MULTILINE+"</html>.*?\0", " ");
		str = str.replaceAll(CASE_INSENSITIVE+MULTILINE+".*?<script.*?>.*?</script>.*?", " ");
		str = str.replaceAll(CASE_INSENSITIVE+MULTILINE+".*?<style.*?>.*?</style>.*?", " ");
		str = str.replaceAll(CASE_INSENSITIVE+MULTILINE+".*?<xml.*?>.*?</xml>.*?", " ");
		str = str.replaceAll(MULTILINE+".*?<!--.*?-->.*?", " ");
		str = str.replaceAll(MULTILINE+".*?[{].*?[}].*?", " ");
	*/
		str = str.replaceAll("/.*?/>", " ");
		str = str.replaceAll(".*?;.*?$", " "); // 行末の寸前がセミコロンならスクリプトだと判断
			str = str.replaceAll(".*?//.*?", "");
			str = str.replaceAll(".*?var.*?$", "");
		str = str.replaceAll(".*?[+].*?$", " "); // スクリプトの複数行にわたる箇所(「+」記号による連結)
		str = str.replaceAll(".*?\".*?\",.*?", " ");
		str = str.replaceAll(".*?#.+?\",.*?", " ");
		str = str.replaceAll(".*?\'.*?\',.*?", " ");
		str = str.replaceAll(".*?/[*].*?[*]/.*?", " "); // xml文書のCDATAセクション対策 (例：/* <![CDATA[ */) これで完全はわからない
		str = str.replaceAll("<.*?>", "");
		// ここまで付け足し
		/*

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
		str = str.replaceAll("#.+? ", " ");
		str = str.replaceAll("@.+?"+LINE_SEPARATOR_PATTERN, " ");
		str = str.replaceAll("/d", " ");
		str = str.replaceAll("ーー", "ー");
		// str = str.replcaeAll("()")
		str = str.replaceAll("[、。「」　■□**?）（)(⇒]", " ");
*/
		return str;
	}
}
