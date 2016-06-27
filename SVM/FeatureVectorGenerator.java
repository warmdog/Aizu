//package jp.ebiz.u.aizu.factory.demo.svm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.Tagger;
import net.moraleboost.mecab.impl.StandardTagger;
*/

public class FeatureVectorGenerator {

	// Map<token, List<word>>
	private final Map<String, List<String>> tokenSet;

	// List<token> (Mapはキーの順序が保障されないので、リストに格納して順序を固定する)

	private final List<String> tokens = new ArrayList<String>();

	public FeatureVectorGenerator(Map<String, List<String>> tokenSet){
		this.tokenSet = tokenSet;
		for(String token : tokenSet.keySet()){
			tokens.add(token);
		}
	}

	public void addDocuments(List<String> documents){

	}

	public Map<String, double[]> generateTFIDFVectors(List<String> documents){

		// Map<document, Map<token, tf>>
		Map<String, Map<String, Integer>> tfMap = new HashMap<String, Map<String, Integer>>();

		// Map<token, df>
		Map<String, Integer> dfMap = new HashMap<String, Integer>();

		for(String document : documents){

			List<String> parseResult = parse(document);

			Map<String, Integer> docTFMap = new HashMap<String, Integer>();
			for(String word : parseResult){

				String token = getToken(word);

				// トークン毎のTFを計算
				if(docTFMap.containsKey(token)){
					int tf = docTFMap.get(token);
					docTFMap.put(token, tf+1);
				} else{
					docTFMap.put(token, 1);
				}
			}

			tfMap.put(document, docTFMap);

			// DFを計算
			for(String token : docTFMap.keySet()){
				if(dfMap.containsKey(token)){
					int df = dfMap.get(token);
					dfMap.put(token, df+1);
				} else{
					dfMap.put(token, 1);
				}
			}
		}

		// 文書毎の特徴ベクトルを求める
		Map<String, double[]> featureVectors = new HashMap<String, double[]>();
		for(String document : documents){

			// Map<token, tfidf>
			Map<String, Double> docTFIDFMap = new HashMap<String, Double>();

			// 単語毎のTF-IDFを求める
			Map<String, Integer> docTFMap = tfMap.get(document);
			for(String token : docTFMap.keySet()){

				double tf = (double)docTFMap.get(token);
				double df = (double)dfMap.get(token);
				double N = (double)documents.size();

				double tfidf = tf * Math.log(N/df);

				docTFIDFMap.put(token, tfidf);
			}

			// 特徴ベクトルを生成
			double[] featureVector = new double[tokens.size()];
			for(int i=0; i < tokens.size(); i++){
				String token = tokens.get(i);
				if(docTFIDFMap.containsKey(token)){
					featureVector[i] = docTFIDFMap.get(token);
				} else{
					featureVector[i] = 0;
				}
			}
			featureVectors.put(document, featureVector);
		}

		return featureVectors;
	}

	private String getToken(String word){
		for(String token : tokenSet.keySet()){
			for(String w : tokenSet.get(token)){
				if(w.equals(word)){
					//System.out.println("In getToken, token = " + token);
					return token;
				}
			}
		}
		return null;
	}

	private List<String> parse(String str){

			String[] splittedWords = str.split(" ");

			List<String> result = new ArrayList<String>();
			for(String word : splittedWords) {
				result.add(word);
			}
			return result;
	}

}
