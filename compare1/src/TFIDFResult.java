
public class TFIDFResult {
	private String formerString;//原来的文本
	private String[] words;// 调整后的字符结果
	private double[] result;//double结果集
	
	
	
	
	public TFIDFResult(String[] words, double[] result) {
		//super();
		this.words = words;
		this.result = result;
	}
	public String getFormerString() {
		return formerString;
	}
	public void setFormerString(String formerString) {
		this.formerString = formerString;
	}
	public String[] getWords() {
		return words;
	}
	public void setWords(String[] words) {
		this.words = words;
	}
	public double[] getResult() {
		return result;
	}
	public void setResult(double[] result) {
		this.result = result;
	}
	
	

}
