
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/*
import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.Tagger;
import net.moraleboost.mecab.impl.StandardTagger;
*/

public class FeatureVectorGeneratorE {

	public List<TFIDFResult> generateTFIDFVectors(List<String> documents){
		
		// List<word>
		List<String> words = new ArrayList<String>();
		
		// Map<document, Map<word, tf>>
		Map<String, Map<String, Integer>> tfMap = new HashMap<String, Map<String, Integer>>();
		
		// Map<word, df>
		Map<String, Integer> dfMap = new HashMap<String, Integer>();
		
		for(String document : documents){
			
			List<String> parseResult = parse(document);
			
			Map<String, Integer> docTFMap = new HashMap<String, Integer>();
			for(String word : parseResult){
				
				// �P�ꖈ��TF���v�Z
				if(docTFMap.containsKey(word)){
					int tf = docTFMap.get(word);
					docTFMap.put(word, tf+1);
				} else{
					docTFMap.put(word, 1);
				}
				
				// �P��ꗗ�ɒP���ǉ�
				if(!words.contains(word)){
					words.add(word);
				}
			}
			
			tfMap.put(document, docTFMap);
			
			// DF���v�Z
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
		
		List<Entry<String,double[]>> list =
				new ArrayList<Entry<String,double[]>>(featureVectors.entrySet());
		//如何排序 featureVectors  并返回 featureVectors
		//list.add(featureVectors);

		for(String document : documents){
			
			// Map<word, tfidf>
			Map<String, Double> docTFIDFMap = new HashMap<String, Double>();
			
			// �P�ꖈ��TF-IDF�����߂�
			Map<String, Integer> docTFMap = tfMap.get(document);
			for(String word : docTFMap.keySet()){
				
				double tf = (double)docTFMap.get(word);
				double df = (double)dfMap.get(word);
				double N = (double)documents.size();
				
				double tfidf = tf * Math.log(N/df);
				
				docTFIDFMap.put(word, tfidf);
			}
			
			// �����x�N�g���𐶐�
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
		
		// �P��ꗗ��\��
		System.out.println("=== �P��ꗗ ===");
		System.out.print("(");
		for(int i=0; i < words.size(); i++){
			System.out.print(words.get(i));
			if(i != words.size()-1){
				System.out.print(", ");
			}
		}
		System.out.println(")");
		System.out.println("");
		
		return sortMap(featureVectors,words);
	}
	
	private List<String> parse(String str){
		
		String[] splittedWords = str.split(" ");

		List<String> result = new ArrayList<String>();
		for(String word : splittedWords) {
			result.add(word);
		}
		return result;
	}
//	public static Map softMap(Map featureVectors){
//		
//		return featureVectors;
//		
//	}
	/**
	 * 按照Value排序,string为word,Double为结果值
	 * @param map
	 * @return
	 */
	public  List<TFIDFResult> sortMap(Map<String, double[]> featureVectors,List<String> words)
	{
		//List<TreeMap<String, Double>> resultList = new ArrayList<TreeMap<String,Double>>();
		List<TFIDFResult> tfidfResults = new ArrayList<TFIDFResult>();//存放结果
		for(Map.Entry<String, double[]> entry : featureVectors.entrySet()){
			HashMap<String, Double> MapTest = new HashMap<String, Double>();//每一篇对应的结果，插入可以自动排序
			double[] valueArray = entry.getValue();//获取值
			String[] wordsTest = new String[words.size()];
			
			for(int i=0;i<words.size();i++)
			{
				wordsTest[i] =words.get(i);
			}
			//冒泡排序
			double 	temp=0.0;
			String tempString="";
			for (int i = valueArray.length - 1; i > 0; --i)
	        {
	            for (int j = 0; j < i; ++j)
	            {
	                if (valueArray[j + 1] > valueArray[j])
	                {
	                    temp = valueArray[j];
	                    valueArray[j] = valueArray[j + 1];
	                    valueArray[j + 1] = temp;
	                    
	                    tempString=wordsTest[j];
	                    wordsTest[j] = wordsTest[j + 1];  //String跟着变化
	                    wordsTest[j + 1] = tempString;
	                    
	                }
	            }
	        }
			
			TFIDFResult tfidfResult = new TFIDFResult(wordsTest,valueArray);
			tfidfResults.add(tfidfResult);
			/*for(int i=0;i<words.size();i++)
			{
				MapTest.put(words.get(i), valueArray[i]);
			}
			ByValueComparator bvc = new ByValueComparator(MapTest);
			TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
			sorted_map.putAll(MapTest);*/
		}
		return tfidfResults;
	}
}
