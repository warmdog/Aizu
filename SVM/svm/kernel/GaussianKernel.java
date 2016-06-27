package svm.kernel;


public class GaussianKernel implements Kernel {
	
	// Parameter for Gaussian Kernel
	private double sig = 0;
	
	
	/**
	 * Initialize Gaussian Kernel
	 * 
	 * @param sig	parameter for Gaussian Kernel 
	 */
	public GaussianKernel(double sig){
		
		// Initialize the parameter sig
		this.sig = sig;
	}
	
	/**
	 * Gaussian Kernel Function
	 * 
	 * @param x1	Calculated Vector 1
	 * @param x2	Calculated Vector 2
	 * @return		Calculation Result
	 */
	public double calc(double[] x1, double[] x2){
		
		double total = 0;
		
		for(int i = 0; i < x1.length; ++i){
			total += (x1[i] - x2[i]) * (x1[i] - x2[i]);
		}
		
		return Math.exp(-total / (sig * sig));
	}
}
