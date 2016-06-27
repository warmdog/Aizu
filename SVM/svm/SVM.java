package svm;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import svm.kernel.GaussianKernel;
import svm.kernel.Kernel;

public class SVM {
	
	// Training Data
	private List<Map.Entry<Integer, double[]>> trainingData = new ArrayList<Map.Entry<Integer, double[]>>();
	
	// Kernel Function
	private Kernel kernel = null;
	
	private double[] lambda = null;					// SVM parameter (lambda)
	private double b = 0;								// SVM parameter (b)
	private SVMParamCalculation lcMethod = null;		// SVM parameters Calculation Method
	
	
	
	
	//-----------------------------------------------------
	// public methods
	//-----------------------------------------------------
	
	/**
	 * Initialize SVM with default parameters
	 * 
	 */
	public SVM(){
		this.lcMethod = new SMO();
		this.kernel = new GaussianKernel(11.8);
	}
	
	/**
	 * Initialize SVM
	 * 
	 * @param lcMethod		Lambda Calculation Method for this SVM
	 * @param kernel		Kernel Function for this SVM
	 */
	public SVM(SVMParamCalculation lcMethod, Kernel kernel){
		this.lcMethod = lcMethod;
		this.kernel = kernel;
	}
	
	/**
	 * Add training data (Feature Vector)
	 * 
	 * @param fVector	Feature Vector
	 * @param clazz		Class which the feature vector belongs.
	 */
	public void addTrainingData(double[] fVector, boolean clazz){
		
		// Add training data
		trainingData.add(new AbstractMap.SimpleEntry<Integer, double[]>((clazz?1:-1), fVector));
	}
	
	/**
	 * Learn training data
	 */
	public void learn(){
		
		System.out.print("Learning.");
		
		// Learn training data
		SVMParamCalculationResult learningResult = lcMethod.calc(trainingData, kernel);
		
		lambda = learningResult.getLambda();
		b = learningResult.getB();
		
		System.out.println("");
		
		// for Debug
		/*
		for(int i=0; i < lambda.length; i++){
			System.out.println("lambda[" + i + "] = " + lambda[i]);
		}
		System.out.println("b = " + b);
		*/
	}
	
	/**
	 * Classify a feature vector
	 * 
	 * @param fVector		Classified feature vector
	 * @return				Classification result
	 */
	public boolean classify(double[] fVector){
		
		//---------------------------------------
		// g(x) = sigma(w_i * K(x,xt_i)) + b
		// Here:
		// 		w_i = lambda_i * y_i
		// 		y_i = class of xt_i
		//		xt_i = training data
		//		K = Kernel Function
		//---------------------------------------
		
		double g = b;		// g(x)
		
		for(int i=0; i < trainingData.size(); i++){
			double w_i = lambda[i] * trainingData.get(i).getKey();
			double K_i = kernel.calc(fVector, trainingData.get(i).getValue());
			g += w_i * K_i;
		}
		
		//---------------------------------------
		// return sign(g(x))
		//---------------------------------------
		
		return (g > 0);
	}
	
	// for debug
	public double classify_for_debug(double[] fVector){
		
		//---------------------------------------
		// g(x) = sigma(w_i * K(x,xt_i)) + b
		// Here:
		// 		w_i = lambda_i * y_i
		// 		y_i = class of xt_i
		//		xt_i = training data
		//		K = Kernel Function
		//---------------------------------------
		
		double g = b;		// g(x)
		double w = 0;		// ||w||
		
		for(int i=0; i < trainingData.size(); i++){
			double w_i = lambda[i] * trainingData.get(i).getKey();
			double K_i = kernel.calc(fVector, trainingData.get(i).getValue());
			w += Math.pow(w_i, 2);
			g += w_i * K_i;
		}
		
		//---------------------------------------
		// return g(x) / ||w||
		//---------------------------------------
		
		return (double)(g/Math.sqrt(w));
	}
	
	
}
