
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//import net.moraleboost.mecab.Lattice;
//import net.moraleboost.mecab.Node;
//import net.moraleboost.mecab.Tagger;
//import net.moraleboost.mecab.impl.StandardTagger;

public class TFIDFDemoE {

	public static void main(String[] args){
		
		List<String> documents = new ArrayList<String>();
		documents.add("weather is fine rainy.");
//		documents.add("weather cloudy fine.");
//		documents.add("basketball baseball soccer baseball");
		TFIDFDemoE tfid = new TFIDFDemoE();
		String sports  = tfid.count("C:/Users/18868/Desktop/test/sports2.txt");
		String sports1 = tfid.count("C:/Users/18868/Desktop/test/sports2.txt");
//		String sports2 = tfid.count("C:/Users/18868/Desktop/test/sports3.txt");
//		String sports3 = tfid.count("C:/Users/18868/Desktop/test/sports4.txt");
//		String sports4 = tfid.count("C:/Users/18868/Desktop/test/sports5.txt");
		documents.add(sports);
		documents.add(sports1);
//		documents.add(sports2);
//		documents.add(sports3);
//		documents.add(sports4);
        List<String> sportsfilepaths  = 	new ArrayList<String>();
      
		FeatureVectorGeneratorE generator = new FeatureVectorGeneratorE();
		List<TFIDFResult> resultList = generator.generateTFIDFVectors(documents);
		for(int i=0;i<resultList.size();i++)
		{
			System.out.println("--- Document ---");
			for(int j=0;j<resultList.get(i).getWords().length;j++)
			{
				
				System.out.print(resultList.get(i).getWords()[j]+", ");
			}
			System.out.println();
			for(int m=0;m<resultList.get(i).getResult().length;m++)
			{
				try{
				File writename = new File("C:/Users/18868/Desktop/test/test1.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
	           // writename.createNewFile(); // 创建新文件  
	            BufferedWriter out = new  BufferedWriter( new  OutputStreamWriter(  
	                    new  FileOutputStream(writename,  true )));  
	            out.write(String.format("%.2f", resultList.get(i).getResult()[m]));
	            out.write("\r\n"); // \r\n即为换行  
	            out.flush(); // 把缓存区内容压入文件  
	            out.close(); 
				}catch(Exception e)
					
				{
					e.printStackTrace();
				}
				System.out.print(String.format("%.2f", resultList.get(i).getResult()[m])+", ");
			}
		/*	for(String key:resultList.get(i).keySet())
			{
				System.out.print(String.format("%.2f", resultList.get(i).get(key))+", ");
			}*/
			System.out.println();
		}
	/*	Map<String, double[]> featureVectors = generator.generateTFIDFVectors(documents);
		
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
		}*/
	}
	private String count(String filePath){
		try{
			File myFile = new File(filePath);
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line =null;
			//number of cancellation
			String string ="";
			while((line =reader.readLine())!= null){
				string = string +line;
			}
			reader.close();
			System.out.println(string);
			return string;
		}catch(Exception e){
			System.out.println("read file wrong!");
			e.printStackTrace();
			return null;
		}
		//return line;
	}
//	public void aa(String bb){
//		String[] colums = bb.toString().split(" ");
//		if(colums.length>1 ){
//			System.out.println(colums[1]);
//		}
//		//System.out.println(colums[0]);
//	}

}

