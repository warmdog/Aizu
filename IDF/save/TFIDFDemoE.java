package jp.ebiz.u.aizu.factory.demo.tfidf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.Tagger;
import net.moraleboost.mecab.impl.StandardTagger;

public class TFIDFDemoE {

	public static void main(String[] args){
		
		List<String> documents = new ArrayList<String>();
		documents.add("weather is fine rainy.");
		documents.add("weather cloudy fine.");
		documents.add("basketball baseball soccer baseball");
		
		FeatureVectorGeneratorE generator = new FeatureVectorGeneratorE();
		Map<String, double[]> featureVectors = generator.generateTFIDFVectors(documents);
		
		for(Map.Entry<String, double[]> entry : featureVectors.entrySet()){
			
			System.out.println("--- Document ---");
			System.out.println(entry.getKey());
			
			System.out.println("--- Feature Vector ---");
			
			double[] featureVector = entry.getValue();
			System.out.print("(");
			for(int i=0; i < featureVector.length; i++){
				System.out.print(String.format("%.2f", featureVector[i]));
				if(i != featureVector.length-1){
					System.out.print(", ");
				}
			}
			System.out.println(")");
			
			System.out.println("");
		}
	}
}
