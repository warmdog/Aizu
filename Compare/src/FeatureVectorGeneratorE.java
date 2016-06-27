
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.Tagger;
import net.moraleboost.mecab.impl.StandardTagger;
*/

public class FeatureVectorGeneratorE {

	public Map<String, double[]> generateTFIDFVectors(List<String> documents){
		
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
		
//		List<Entry<String,double[]>> list =
//				new ArrayList<Entry<String,double[]>>(featureVectors.entrySet());
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
		
		return featureVectors;
	}
	
	private List<String> parse(String str){
		String regEx="[_-`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*()——+|{}【】‘；：”“’。，、“”？]";
		Pattern   p   =   Pattern.compile(regEx);     
	    Matcher   m   =   p.matcher(str);
	    str= m.replaceAll(" ").trim();
		String[] splittedWords1 = str.split("\\s+");
//		String[] splittedWords = new String[splittedWords1.length] ;
//		int j =0;
//		for(int i =0; i<splittedWords1.length;i++){
//			if(splittedWords1[i]!=null){
//			    splittedWords[j] = splittedWords1[i];
//				j++;
//				
//			}
//		}
		List<String> strings = new ArrayList<String>();
		for(String s : splittedWords1){
			//System.out.print(s+'+');
			if(s!=null && s!=""){
				strings.add(s);
			//	System.out.print(s+'+');
			}
			//System.out.println();
			//strings.add(s);
			//System.out.print(s+'+');
		}
		
		
//		List<String> result = new ArrayList<String>();
//		for(String word : splittedWords) {
//			result.add(word);
//		}
		return strings;
	}
	
}
