package svm;

public class SVMParamCalculationResult {

	private double[] lambda = null;
	private double b = 0;
	
	/**
	 * Calculation of SVM Parameters
	 * 
	 * @param lambda	SVM parameter lambda (result value)
	 * @param b			SVM parameter b (result value)
	 * @param datas		Training data
	 * @param kernel	Kernel Function for SVM
	 * 
	 * @return result of calculation of parameters
	 */
	
	/**
	 * Generate result of calculation of SVM parameters
	 * 
	 * @param lambda
	 * @param b
	 */
	public SVMParamCalculationResult(double[] lambda, double b) {
		this.lambda = lambda;
		this.b = b;
	}
	
	/**
	 * get lambda which is one of parameters
	 * 
	 * @return lambda
	 */
	public double[] getLambda() {
		return lambda;
	}
	
	/**
	 * set lambda which is one of parameters
	 * 
	 * @param lambda lambda
	 */
	public void setLambda(double[] lambda) {
		this.lambda = lambda;
	}
	
	/**
	 * get b which is one of parameters
	 * 
	 * @return b
	 */
	public double getB() {
		return b;
	}
	
	/**
	 * set b which is one of parameters
	 * 
	 * @param b b
	 */
	public void setB(double b) {
		this.b = b;
	}
	
	
}
