
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.SliderUI;

//import net.moraleboost.mecab.Lattice;
//import net.moraleboost.mecab.Node;
//import net.moraleboost.mecab.Tagger;
//import net.moraleboost.mecab.impl.StandardTagger;

public class TFIDFDemoE {

	public static void main(String[] args){
		TFIDFDemoE tfid = new TFIDFDemoE();
		List<String> documents ;
		documents = tfid.getList();
		
//		List<String> documents = new ArrayList<String>();
//		documents.add("weather is fine rainy.");
//		documents.add("weather cloudy fine.");
//		documents.add("basketball baseball soccer baseball");
		
		System.out.println(documents);
		FeatureVectorGeneratorE generator = new FeatureVectorGeneratorE();
		Map<String, double[]> featureVectors = generator.generateTFIDFVectors(documents);
		
		for(Map.Entry<String, double[]> entry : featureVectors.entrySet()){
			
			System.out.println("--- Document ---");
			System.out.println(entry.getKey());
			
			System.out.println("--- Feature Vector ---");
			
			double[] featureVector = entry.getValue();
			System.out.print("(");
			for(int i=0; i < featureVector.length; i++){
				System.out.print(String.format("%.2f", featureVector[i]));
				if(i != featureVector.length-1){
					System.out.print(", ");
				}
			}
			System.out.println(")");
			
			System.out.println("");
		}
	}

	public List<String> getList()  {
		
		try {
			File myfile = new File("C:/Users/18868/Desktop/REsearch Paper/qqq.txt");
			BufferedReader list;
			list = new BufferedReader( new FileReader(myfile));
			String line ;
			List<String> mylist =  new ArrayList<String>();
			while((line=list.readLine())!= null){
				System.out.println(line);
				mylist.add(line);
			}
			//list.close();
			
			//System.out.println(mylist);
			list.close();
			return mylist;
		} catch (IOException e) {
			List<String> mylist =null;
			// TODO Auto-generated catch block
		
			e.printStackTrace();
			return mylist;
		}
		

	
	}
}
