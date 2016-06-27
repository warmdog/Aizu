package dataGeneration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;

import generateSvmData.MyDataReader;
import util.CommonProperities;
import util.FileManager;

public class RawDataManagement {

	// 1= Drama
	// 2= Economy
	// 3= Politics
	// 4= Radiation
	// 5= Sports
	// 6= Weather

	public static List<String> createDataFile(String fileTop, String fileBottom, int numberOfFiles_perFile)
			throws IOException {

		List<String> trainTestFilePath = new ArrayList<String>();
		String fileTopRawDataPath = getRawDataPath(fileTop);
		String fileBottomRawDataPath = getRawDataPath(fileBottom);

		long numOfFiles_inTopContent = FileManager.fileCount(fileTopRawDataPath);
		long numOfFiles_inBottomContent = FileManager.fileCount(fileBottomRawDataPath);
		int divideNumber = getFileDivideNumber(numOfFiles_inTopContent, numOfFiles_inBottomContent);

		System.out.println("--------NOTIFICATION--------");
		System.out.println(fileTop.toUpperCase() + " Folder Contained : " + numOfFiles_inTopContent + " Files");
		System.out.println(fileBottom.toUpperCase() + " Folder Contained : " + numOfFiles_inBottomContent + " Files");
		System.out.println("Therefore, Final Test & Train Data files will be contained contents of the : "
				+ divideNumber * 2 + " Files  ( "+divideNumber+" from each other)");

		trainTestFilePath.add(
				createTraingDataFile(fileTop, fileBottom, fileTopRawDataPath, fileBottomRawDataPath, divideNumber));
		trainTestFilePath
				.add(createTestDataFile(fileTop, fileBottom, fileTopRawDataPath, fileBottomRawDataPath, divideNumber));

		return trainTestFilePath;
	}

	private static int getFileDivideNumber(long numberTop, long numberBottom) {
		// TODO Auto-generated method stub
		int divider = (numberTop < numberBottom) ? (int) (numberTop / 2) : (int) (numberBottom / 2);
		return divider;
	}

	private static String createTraingDataFile(String fileTop, String fileBottom, String fileTopRawDataPath,
			String fileBottomRawDataPath, int divideNumber) throws IOException {
		// TODO Auto-generated method stub
		List<String> TopRawContent = FileManager.retrieveFirstHalf(fileTopRawDataPath, divideNumber);
		List<String> BottomRawContent = FileManager.retrieveFirstHalf(fileBottomRawDataPath, divideNumber);
		String resultFilePath = FileManager.createFile(fileTop.toUpperCase(), fileBottom.toUpperCase(), TopRawContent,
				BottomRawContent, "TRAIN");
		System.out.println("File for TRAINING purpose PATH = " + resultFilePath);
		return resultFilePath;
	}

	private static String createTestDataFile(String fileTop, String fileBottom, String fileTopRawDataPath,
			String fileBottomRawDataPath, int divideNumber) throws IOException {
		// TODO Auto-generated method stub
		List<String> TopRawContent = FileManager.retrieveBottomHalf(fileTopRawDataPath, divideNumber);
		List<String> BottomRawContent = FileManager.retrieveBottomHalf(fileBottomRawDataPath, divideNumber);
		String resultFilePath = FileManager.createFile(fileTop.toUpperCase(), fileBottom.toUpperCase(), TopRawContent,
				BottomRawContent, "TEST");
		System.out.println("FILE for TESTING purpose PATH = " + resultFilePath);
		return resultFilePath;
	}

	private static String getRawDataPath(String fileTop) {
		// TODO Auto-generated method stub
		String filePath = "";

		if (fileTop.equalsIgnoreCase("drama")) {
			return filePath = CommonProperities.DramaRawDataPath;
		} else if (fileTop.equalsIgnoreCase("economy")) {
			return filePath = CommonProperities.EconomyRawDataPath;
		} else if (fileTop.equalsIgnoreCase("politics")) {
			return filePath = CommonProperities.PoliticsRawDataPath;
		} else if (fileTop.equalsIgnoreCase("radiation")) {
			return filePath = CommonProperities.RadiationRawDataPath;
		} else if (fileTop.equalsIgnoreCase("sports")) {
			return filePath = CommonProperities.SportsRawDataPath;
		} else if (fileTop.equalsIgnoreCase("weather")) {
			return filePath = CommonProperities.WeatherRawDataPath;
		}
		return null;
	}

	public static String getSelectedType(String seleciton) {
		// TODO Auto-generated method stub
		String type = "";

		switch (seleciton) {
		case "1":
			type = "drama";
			break;
		case "2":
			type = "economy";
			break;
		case "3":
			type = "politics";
			break;
		case "4":
			type = "radiation";
			break;
		case "5":
			type = "sports";
			break;
		case "6":
			type = "weather";
			break;
		default:
			type = "Wrong Selection, Please ENTER Correct Number";
			break;
		}
		return type;
	}
}
