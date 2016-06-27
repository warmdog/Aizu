//package jp.ebiz.u.aizu.factory.demo.svm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import svm.*;

public class SVMDemo {

	public static void main(String[] args){

		//--------------------------------
		// 学習
		//--------------------------------

		// トークンを生成
		Map<String, List<String>> tokenSet = createTokenSet();

		// 教師データを生成
		List<String> lightWeightMessages = createLightWeightMessages();
		List<String> heavyWeightMessages = createHeavyWeightMessages();

		// 教師データを結合
		List<String> allMessages = new ArrayList<String>();
		allMessages.addAll(lightWeightMessages);
		allMessages.addAll(heavyWeightMessages);

		// 教師データの特徴ベクトルを生成
		FeatureVectorGenerator generator = new FeatureVectorGenerator(tokenSet);
		Map<String, double[]> vectors = generator.generateTFIDFVectors(allMessages);

		// SVMを生成
		SVM svm = new SVM();

		// 教師データをSVMに学習させる (軽めの話をtrueに、重めの話をfalseにする)
		for(String message : vectors.keySet()){
			double[] vector = vectors.get(message);
			if(lightWeightMessages.contains(message)){
				svm.addTrainingData(vector, true);
			} else if(heavyWeightMessages.contains(message)){
				svm.addTrainingData(vector, false);
			}
		}
		svm.learn();




		//--------------------------------
		// 分類
		//--------------------------------

		// 分類するメッセージ
		// String message = "want be baseball player";
		// String message = "soccer what";
		 String message = "budget should be considered";

		// 分類するメッセージから特徴ベクトルを生成
		List<String> classifiedMessages = new ArrayList<String>();
		classifiedMessages.addAll(allMessages);
		classifiedMessages.add(message);
		Map<String, double[]> classifiedVectors = generator.generateTFIDFVectors(classifiedMessages);
		double[] vector = classifiedVectors.get(message);

		// メッセージを分類する
		boolean result = svm.classify(vector);

		if(result){
			System.out.println("lightSpeaking");
		} else{
			System.out.println("heavySpeaking");
		}
	}

	private static Map<String, List<String>> createTokenSet(){

		Map<String, List<String>> tokenSet = new HashMap<String, List<String>>();

		// [軽めの話]スポーツトークン(sports): サッカー, 野球
		List<String> sportsToken = new ArrayList<String>();
		sportsToken.add("soccer");
		sportsToken.add("baseball");
		tokenSet.put("sports", sportsToken);

		// [軽めの話]芸能トークン(entertainment): 映画, ドラマ
		List<String> entertainmentToken = new ArrayList<String>();
		entertainmentToken.add("movie");
		entertainmentToken.add("drama");
		tokenSet.put("entertainment", entertainmentToken);

		// [重めの話]政治トークン(government): 法律, 予算案, 政府, 外交
		List<String> governmantToken = new ArrayList<String>();
		governmantToken.add("law");
		governmantToken.add("budget");
		governmantToken.add("government");
		governmantToken.add("diplomat");
		tokenSet.put("government", governmantToken);

		return tokenSet;

	}

	private static List<String> createLightWeightMessages(){

		List<String> messages = new ArrayList<String>();
		messages.add("like soccer very much");
		messages.add("baseball is national sports");
		messages.add("yester movie is very good");
		messages.add("about yesterday drama");

		return messages;
	}

	private static List<String> createHeavyWeightMessages(){

		List<String> messages = new ArrayList<String>();
		messages.add("about this year budget");
		messages.add("occur diplomat problem");
		messages.add("that law will start next month");
		messages.add("this can be solve by government");

		return messages;
	}
}
