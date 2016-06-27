package svm;

import java.util.List;
import java.util.Map;

import svm.kernel.Kernel;

public class SMO implements SVMParamCalculation{
	
	private double b;
	private double c = 10;//Double.MAX_VALUE;
	private final double tol = 0.001;
	private final double eps = 0.001;
	private double[] lambda;
	private List<Map.Entry<Integer, double[]>> datas = null;
	private double[] err_cache;
	private int knum = 2;
	
	private Kernel kernel = null;
	
	
	//-----------------------------------------------------
	// public methods
	//-----------------------------------------------------
	
	/**
	 * Calculation of SVM Parameters
	 * 
	 * @param datas		Training data
	 * @param kernel	Kernel Function for SVM
	 * @return result of calculation of parameters
	 */
	public SVMParamCalculationResult calc(List<Map.Entry<Integer, double[]>> datas, Kernel kernel){
		
		this.datas = datas;
		this.kernel = kernel;
		
		// run SMO;
		runSMO();
		
		// generate result
		SVMParamCalculationResult result = new SVMParamCalculationResult(lambda,-b);
		
		return result;
	}
	
	private double kernel(double[] x1, double[] x2, int knum){
		return kernel.calc(x1, x2);
	}
	
	//Calculation of SMO algorithm proposed by John Platt
	private void runSMO() {
		
		b = 0;
		lambda = new double[datas.size()];
		err_cache = new double[lambda.length];
		
		
		for(int i = 0; i < lambda.length; ++i){
			lambda[i] = 0;
		}

		boolean examineAll = true;
		int numChanged = 0;
		
		int m=0;
		while(numChanged>0 || examineAll)  {
		    m++;
		    //System.out.println(m);
		    System.out.print(".");
		    
			numChanged = 0;
			if(examineAll){
				for(int i=0;i<datas.size();i++){
					numChanged+=examineExample(i);
				}
			}else{
				for(int i=0;i<datas.size();i++){
					if(lambda[i]!=0&&lambda[i]!=c){
						numChanged+=examineExample(i);
					}
				}
			}
			if(examineAll){
				examineAll=false;
			}else if(numChanged==0){
				examineAll=true;
			}
			//System.out.println("" + count++ + ": examineAll=" + examineAll + ", numChanged=" + numChanged);
		}
	}
		
	private int examineExample(int x1){
		double y1,e1,r1;

		y1=datas.get(x1).getKey();
		
		// x1‚É‘Î‚·‚émƒÓ(x)‚ð‹‚ß‚é
		if(lambda[x1]>0&&lambda[x1]<c){
			e1=err_cache[x1];
		}else{
			e1= calcE(x1);
		}
		r1=y1*e1;

		if((lambda[x1] < c && r1 < -tol) || (lambda[x1] > 0 && r1 > tol)){
			int x2=-1;
			double max = -1;
			for(int k = 0; k < datas.size(); k++){
				
				double e2,temp=-1;
				if(lambda[k]>0&&lambda[k]<c){
					e2=calcE(k);
					temp = Math.abs(e1-e2);
				}
				if(temp > max){
					max = temp;
					x2 = k;
				}
			}
			if(max >= 0){
				if(step(x2, x1, 1)){
					return 1;
				}
			}
			int randx = (int)(Math.random() * datas.size());
			for(int k = 0; k < datas.size(); k++){
				x2 = (k + randx) % datas.size();
				if(lambda[x2] > 0&& lambda[x2] < c){
					if(step(x2, x1,2)){
						return 1;
					}
				}
			}
			randx = (int)(Math.random() * datas.size());
			for(int k = 0; k < datas.size(); k++){
				x2 = (k + randx) % datas.size();
				if(step(x2, x1,3)){
					return 1;
				}
			}
		}
		return 0;
	}
	
	/**
	 * mƒÓ(x)‚ð‹‚ß‚é
	 */
	private double calcE(int i){
		double e = -b - datas.get(i).getKey();
		for(int j = 0; j < lambda.length; ++j){
			e += lambda[j] * datas.get(j).getKey() *
				kernel(datas.get(j).getValue(), datas.get(i).getValue(),knum);
		}
		err_cache[i] = e;
		return e;
	}
	
	private boolean step(int x1, int x2, int step) {
		if(x1 == x2) return false;
		int y1 = datas.get(x1).getKey();
		int y2 = datas.get(x2).getKey();

		double e1,e2;

		if(lambda[x1]>0&&lambda[x1]<c){
			e1=err_cache[x1];
		}else{
			e1 = calcE(x1);
		}
		if(lambda[x2]>0&&lambda[x2]<c){
			e2=err_cache[x2];
		}else{
			e2 = calcE(x2);
		}

		int s=y1*y2;

		double l,h,a1,a2;

		if(y1 == y2){
			l = Math.max(0, lambda[x2] + lambda[x1] - c);
			h = Math.min(c, lambda[x2] + lambda[x1]);
		}else{
			l = Math.max(0, lambda[x2] - lambda[x1]);
			h = Math.min(c, c + lambda[x2] - lambda[x1]);
		}
		if(l == h) return false;

		double k11 = kernel(datas.get(x1).getValue(), datas.get(x1).getValue(),knum);
		double k22 = kernel(datas.get(x2).getValue(), datas.get(x2).getValue(),knum);
		double k12 = kernel(datas.get(x1).getValue(), datas.get(x2).getValue(),knum);
		double eta =  2 * k12-k11 - k22;
	
		if(eta < 0){
			a2=lambda[x2] + y2 * (e2 - e1) / eta;
			if(a2<l){
				a2=l;
			}else if(a2>h){
				a2=h;
			}
		}else{
			//double c1=eta/2;double c2=y2*(e1-e2)-eta*lambda[x2];double lObj=c1*l*l+c2*l;double hObj=c1*h*h+c2*h;
			double f1=y1*(e1+b)-lambda[x1]*k11-s*lambda[x2]*k12;
			double f2=y2*(e2+b)-s*lambda[x1]*k12-lambda[x2]*k22;
			double l1=lambda[x1]+s*(lambda[x2]-l);
			double h1=lambda[x1]+s*(lambda[x2]-h);
			double lObj=l1*f1+l*f2+0.5*l1*l1*k11+0.5*l*l*k22+s*l*l1*k12;
			double hObj=h1*f1+h*f2+0.5*h1*h1*k11+0.5*h*h*k22+s*h*h1*k12;
			if(lObj>hObj+eps){
				a2=l;
			}else if(lObj < hObj - eps){
				a2=h;
			}else{
				a2=lambda[x2];
			}
		}

		if(Math.abs((a2-lambda[x2]))<eps*(a2+lambda[x2]+eps)){return false;}

		a1=lambda[x1]-s*(a2 - lambda[x2]);
	

		if(a1<0){
			a2 += -a1;
			a1=0;
		}else if(a1>c){
			a2+=(a1-c);
			a1=c;
		}
		
		double delta_b=0;
		double bnew=0;

		if(a1>0&& a1<c){
			bnew = b + e1 + y1*(a1 - lambda[x1])*k11 + y2*(a2-lambda[x2])*k12;
		}else{
			if(a2>0&& a2<c){
				bnew = b + e2 + y1*(a1 - lambda[x1])*k12 + y2*(a2-lambda[x2])*k22;
			}else{
				double b1=b + e1 + y1 * (a1 - lambda[x1])*k11+y2*(a2-lambda[x2])*k12;
				double b2=b + e2 + y1 * (a1 - lambda[x1])*k12+y2*(a2-lambda[x2])*k22;
				bnew=(b1+b2)*0.5;
			}
		}
		delta_b=b-bnew;
		b=bnew;
	
		for(int i=0;i<lambda.length;i++){
			if(lambda[i]>0&&lambda[i]<c){
				err_cache[i]+=y1*kernel(datas.get(x1).getValue(),datas.get(i).getValue(),knum)+y2*kernel(datas.get(x2).getValue(),datas.get(i).getValue(),knum)-delta_b;
			}
		}
		//Why the err_cache's to 0??
		//err_cache[x1]=0;
		//err_cache[x2]=0;

		lambda[x1] = a1;
		lambda[x2] = a2;
		return true;
	}
}
