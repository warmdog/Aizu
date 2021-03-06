//package jp.ebiz.u.aizu.factory.demo.tfidf;

/**
   *  - Compile:  javac -cp .;igo-0.4.5.jar TFIDFDemo.java
   *   - Run:   java -cp .;igo-0.4.5.jar TFIDFDemo
   */

import java.util.ArrayList;
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

public class TFIDFDemo {

	public static void main(String[] args) throws IOException {

		List<String> documents = new ArrayList<String>();
		documents.add("僕は弁護士になる。");
		documents.add("僕は会計士になる。");
		documents.add("私は税理士になりたい。");

		FeatureVectorGenerator generator = new FeatureVectorGenerator();
		Map<String, double[]> featureVectors = generator.generateTFIDFVectors(documents);

		for(Map.Entry<String, double[]> entry : featureVectors.entrySet()){

			System.out.println("--- 文書 ---");
			System.out.println(entry.getKey());

			System.out.println("--- 特徴ベクトル ---");

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
