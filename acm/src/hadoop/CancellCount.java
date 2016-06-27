package hadoop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CancellCount {
	public static void main(String[] args) {
		CancellCount  cancell = new CancellCount();
		//path of AirplaneDataFormat.txt!
		String filePath ="C:/Users/18868/Desktop/REsearch Paper/AirplaneDataFormat.txt";
		System.out.println(cancell.count(filePath));
	}
	//count cancellation of flight
	private int count(String filePath){
		try{
			File myFile = new File(filePath);
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line =null;
			// number of cancellation
			int number = 0;
			while((line =reader.readLine())!= null){
				System.out.println(line);
				
				if(line.toLowerCase().contains("cancell")){
					number++;
				}
			}
			reader.close();
			return number;
		}catch(Exception e){
			System.out.println("read file wrong!");
			e.printStackTrace();
			return -1;
		}
		//return line;
	}
	
}
