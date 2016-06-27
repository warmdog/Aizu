package svm;

import java.util.List;
import java.util.Map;

import svm.kernel.Kernel;

public interface SVMParamCalculation {
	
	/**
	 * Calculation of SVM Parameters
	 * 
	 * @param datas		Training data
	 * @param kernel	Kernel Function for SVM
	 * @return result of calculation of parameters
	 */
	public SVMParamCalculationResult calc(List<Map.Entry<Integer, double[]>> datas, Kernel kernel);
}
