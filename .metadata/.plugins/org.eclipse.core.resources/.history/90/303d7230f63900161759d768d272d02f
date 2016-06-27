package newssearch;
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
import org.jsoup.examples.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import utilities.MultiByteCharacterChecker;
import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;
import org.jsoup.*;

public class ReutersTxt {

	public static void main(String newsSite, String newsDomain, String OS) throws IOException {
		// TODO Auto-generated method stub
		
		Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
		Elements newsHeadlines = doc.select("#mp-itn b a");
		System.out.println(newsHeadlines);
	}

}
