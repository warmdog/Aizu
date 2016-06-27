import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TFIDdemo {
	public Map<String,double[]> getTFID(List<String> documents){
		Map<String,double[]> tfmap = new HashMap<String,double[]>();
		for(String document:documents){
			String[] words = parse(document);
			
		}
		return tfmap;
	}
	public String[] parse(String document){
		document = "a a a ";
		String[] word = document.split(" ");
		return word;
		
	}
	public static void main(String[] args){
		TFIDdemo aa = new TFIDdemo();
		String bb[] = aa.parse("aa");
		for(int i =0; i<bb.length;i++){
			
		}
		
	}
	
	
}
