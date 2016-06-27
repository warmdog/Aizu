package svm.kernel;

public interface Kernel {
	
	/**
	 * Kernel Function
	 * 
	 * @param x1	Calculated Vector 1
	 * @param x2	Calculated Vector 2
	 * @return		Calculation Result
	 */
	public double calc(double[] x1, double[] x2);
}
