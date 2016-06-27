//package jp.ebiz.u.aizu.factory.demo.svm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import svm.*;

public class SVMDemo {

	public static void main(String[] args){

		//--------------------------------
		// �w�K
		//--------------------------------

		// �g�[�N���𐶐�
		Map<String, List<String>> tokenSet = createTokenSet();

		// ���t�f�[�^�𐶐�
		List<String> lightWeightMessages = createLightWeightMessages();
		List<String> heavyWeightMessages = createHeavyWeightMessages();

		// ���t�f�[�^������
		List<String> allMessages = new ArrayList<String>();
		allMessages.addAll(lightWeightMessages);
		allMessages.addAll(heavyWeightMessages);

		// ���t�f�[�^�̓����x�N�g���𐶐�
		FeatureVectorGenerator generator = new FeatureVectorGenerator(tokenSet);
		Map<String, double[]> vectors = generator.generateTFIDFVectors(allMessages);

		// SVM�𐶐�
		SVM svm = new SVM();

		// ���t�f�[�^��SVM�Ɋw�K������ (�y�߂̘b��true�ɁA�d�߂̘b��false�ɂ���)
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
		// ����
		//--------------------------------

		// ���ނ��郁�b�Z�[�W
		// String message = "want be baseball player";
		// String message = "soccer what";
		 String message = "budget should be considered";

		// ���ނ��郁�b�Z�[�W��������x�N�g���𐶐�
		List<String> classifiedMessages = new ArrayList<String>();
		classifiedMessages.addAll(allMessages);
		classifiedMessages.add(message);
		Map<String, double[]> classifiedVectors = generator.generateTFIDFVectors(classifiedMessages);
		double[] vector = classifiedVectors.get(message);

		// ���b�Z�[�W�𕪗ނ���
		boolean result = svm.classify(vector);

		if(result){
			System.out.println("lightSpeaking");
		} else{
			System.out.println("heavySpeaking");
		}
	}

	private static Map<String, List<String>> createTokenSet(){

		Map<String, List<String>> tokenSet = new HashMap<String, List<String>>();

		// [�y�߂̘b]�X�|�[�c�g�[�N��(sports): �T�b�J�[, �싅
		List<String> sportsToken = new ArrayList<String>();
		sportsToken.add("soccer");
		sportsToken.add("baseball");
		tokenSet.put("sports", sportsToken);

		// [�y�߂̘b]�|�\�g�[�N��(entertainment): �f��, �h���}
		List<String> entertainmentToken = new ArrayList<String>();
		entertainmentToken.add("movie");
		entertainmentToken.add("drama");
		tokenSet.put("entertainment", entertainmentToken);

		// [�d�߂̘b]�����g�[�N��(government): �@��, �\�Z��, ���{, �O��
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
