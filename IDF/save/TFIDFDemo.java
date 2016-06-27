package jp.ebiz.u.aizu.factory.demo.tfidf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.Tagger;
import net.moraleboost.mecab.impl.StandardTagger;

public class TFIDFDemo {

	public static void main(String[] args){
		
		List<String> documents = new ArrayList<String>();
		documents.add("�l�ٌ͕�m�ɂȂ�B");
		documents.add("�l�͉�v�m�ɂȂ�B");
		documents.add("���͐ŗ��m�ɂȂ肽���B");
		
		FeatureVectorGenerator generator = new FeatureVectorGenerator();
		Map<String, double[]> featureVectors = generator.generateTFIDFVectors(documents);
		
		for(Map.Entry<String, double[]> entry : featureVectors.entrySet()){
			
			System.out.println("--- ���� ---");
			System.out.println(entry.getKey());
			
			System.out.println("--- �����x�N�g�� ---");
			
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
