package test11;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import net.moraleboost.mecab.Lattice;
//import net.moraleboost.mecab.Node;
//import net.moraleboost.mecab.Tagger;
//import net.moraleboost.mecab.impl.StandardTagger;

public class TFIDFDemoE {

	public static void main(String[] args){
		
		List<String> documents = new ArrayList<String>();
		
//		documents.add("weather is fine rainy.");
//		documents.add("weather cloudy fine.");
//		documents.add("basketball baseball soccer baseball");
		File dir1 = new File(System.getProperty("user.home")
				+ "/Desktop/TxtData/reuters/entertainment/");
		File dir2 = new File(System.getProperty("user.home")
				+ "/Desktop/TxtData/reuters/business/");
		File[] file1 = dir1.listFiles();
		File[] file2 = dir2.listFiles();
		
		BufferedReader br = null;
		BufferedReader br2 = null;
		for (int i = 101; i <= 200; i++) {
			try {
				// 入力元ファイル
				//Thread.sleep(500);
				File file = new File(file1[i-1].getPath());
				//String fileName = file.getName().replaceAll("[.].*?$", "");
				// 改行タグチェック
				InputStreamReader in = new InputStreamReader(
						new FileInputStream(file), "UTF-8");
				br = new BufferedReader(in);
				String line;
				String content ="";
				while((line = br.readLine()) != null){
					content +=line +" ";
				}
				documents.add(content);
				System.out.println(content);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		for (int i = 101; i <= 200; i++) {
			try {
				// 入力元ファイル
				//Thread.sleep(500);
				File file = new File(file2[i-1].getPath());
				//String fileName2 = file.getName().replaceAll("[.].*?$", "");
				// 改行タグチェック
				InputStreamReader in = new InputStreamReader(
						new FileInputStream(file), "UTF-8");
				br2 = new BufferedReader(in);
				String line;
				String content ="";
				while((line = br2.readLine()) != null){
					content +=line +" ";
				}
				documents.add(content);
				System.out.println(content);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br2.close();
				} catch (IOException e) {
				}
			}
		}
		FeatureVectorGeneratorE generator = new FeatureVectorGeneratorE();
		System.out.println(documents.size());
		Map<String, double[]> featureVectors = generator.generateTFIDFVectors(documents);
		
//		for(Map.Entry<String, double[]> entry : featureVectors.entrySet()){
//			
//			System.out.println("--- Document ---");
//			System.out.println(entry.getKey());
//			
//			System.out.println("--- Feature Vector ---");
//			
//			double[] featureVector = entry.getValue();
//			System.out.print("(");
  
       try {
        	File writename = new File("C:/Users/18868/Desktop/SVM_Light/SVM_Light/example1/test2.dat");
			BufferedWriter out = new  BufferedWriter( new  OutputStreamWriter(  
			        new  FileOutputStream(writename,  true)));//add 
			//out.write("# Reuters category (test examples: 100 positive/ 100 negative)"+ "\n");
			int n =1;
			for(Map.Entry<String, double[]> entry : featureVectors.entrySet()){
				
				System.out.println("--- Document ---");
				System.out.println(entry.getKey());
				System.out.println(n+".............");
				System.out.println("--- Feature Vector ---");
				double[] featureVector = entry.getValue();
				System.out.print("(");
				if(n>100){
					out.write("-1"+" ");
				}else{
					out.write("+1"+" ");
				}
				
				for(int i=0; i < featureVector.length; i++){
					String it = String.format("%.16f", featureVector[i]);
					//Double.valueOf(it)== 0);
					if(!(Double.valueOf(it)== 0)){
						int b= i+1;
						out.write(b + ":" + it + " ");
					}
					System.out.print(String.format("%.2f", featureVector[i]));
					if(i != featureVector.length-1){
						System.out.print(", ");
					}
				}
				out.write("\n");
				System.out.println(")");
				
				System.out.println("");
				n++;
			}
			//out.write("\n");
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
