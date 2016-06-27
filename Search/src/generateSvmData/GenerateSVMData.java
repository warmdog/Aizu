package generateSvmData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.CommonProperities;
import util.FileManager;

public class GenerateSVMData {

	public static void main(String[] args) throws IOException {

		MyDataReader readfile = new MyDataReader();
		List<String> documents1 = new ArrayList<String>();
		double div_rate = 0.5;

		documents1 = readfile.read("DataRepository/RawDataRepository/mytest1.txt");
		
		if (args.length == 3) {
			div_rate = Double.parseDouble(args[2]);
		}
		MyDataWriter writefile = new MyDataWriter();
		writefile.write(args[1], documents1, div_rate);

	}

	public static void generateSvmData(String trainFilePath, String resultFile) throws IOException{
		MyDataReader readfile = new MyDataReader();
		List<String> fileContent = new ArrayList<String>();
		double div_rate = 0.5;

		fileContent = FileManager.readFileAsList(trainFilePath);
		
		String resultFilePath = CommonProperities.Result_outPutFiles_from_SVM+resultFile;
		
		MyDataWriter writefile = new MyDataWriter();
		writefile.write(resultFilePath, fileContent, div_rate);
	}
}
