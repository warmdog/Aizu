//package jp.ebiz.u.aizu.factory.demo.tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import net.reduls.igo.Tagger;
import net.reduls.igo.Morpheme;

/*
import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.Tagger;
import net.moraleboost.mecab.impl.StandardTagger;
*/

public class FeatureVectorGenerator {
    Tagger tagger = null;

	public Map<String, double[]> generateTFIDFVectors(List<String> documents) throws IOException {

		// List<word>
		List<String> words = new ArrayList<String>();

		// Map<document, Map<word, tf>>
		Map<String, Map<String, Integer>> tfMap = new HashMap<String, Map<String, Integer>>();

		// Map<word, df>
		Map<String, Integer> dfMap = new HashMap<String, Integer>();

		// For Igo Mophological Analyzer
		tagger = new Tagger("ipadic");

		for(String document : documents){

			List<String> parseResult = parse(document);

			Map<String, Integer> docTFMap = new HashMap<String, Integer>();
			for(String word : parseResult){

				// 単語毎のTFを計算
				if(docTFMap.containsKey(word)){
					int tf = docTFMap.get(word);
					docTFMap.put(word, tf+1);
				} else{
					docTFMap.put(word, 1);
				}

				// 単語一覧に単語を追加
				if(!words.contains(word)){
					words.add(word);
				}
			}

			tfMap.put(document, docTFMap);

			// DFを計算
			for(String word : docTFMap.keySet()){
				if(dfMap.containsKey(word)){
					int df = dfMap.get(word);
					dfMap.put(word, df+1);
				} else{
					dfMap.put(word, 1);
				}
			}
		}


		Map<String, double[]> featureVectors = new HashMap<String, double[]>();
		for(String document : documents){

			// Map<word, tfidf>
			Map<String, Double> docTFIDFMap = new HashMap<String, Double>();

			// 単語毎のTF-IDFを求める
			Map<String, Integer> docTFMap = tfMap.get(document);
			for(String word : docTFMap.keySet()){

				double tf = (double)docTFMap.get(word);
				double df = (double)dfMap.get(word);
				double N = (double)documents.size();

				double tfidf = tf * Math.log(N/df);

				docTFIDFMap.put(word, tfidf);
			}

			// 特徴ベクトルを生成
			double[] featureVector = new double[words.size()];
			for(int i=0; i < words.size(); i++){
				String word = words.get(i);
				if(docTFIDFMap.containsKey(word)){
					featureVector[i] = docTFIDFMap.get(word);
				} else{
					featureVector[i] = 0;
				}
			}
			featureVectors.put(document, featureVector);
		}

		// 単語一覧を表示
		System.out.println("=== 単語一覧 ===");
		System.out.print("(");
		for(int i=0; i < words.size(); i++){
			System.out.print(words.get(i));
			if(i != words.size()-1){
				System.out.print(", ");
			}
		}
		System.out.println(")");
		System.out.println("");

		return featureVectors;
	}

	private List<String> parse(String str){
		List<String> result = new ArrayList<String>();

        for(Morpheme m : tagger.parse(str))
                result.add(m.surface);

		return result;
	} // end of parse

}
