package dataGeneration;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import generateSvmData.GenerateSVMData;

public class GenerateData {
	// 1= Drama
	// 2 = Economy
	// 3= Politics
	// 4= Radiation
	// 5= Sports
	// 6= Weather

	public static void main(String org[]) throws IOException {

		// create data file for svm
		System.out.println("1 = Drama");
		System.out.println("2 = Econom");
		System.out.println("3 = Politics");
		System.out.println("4 = Radiation");
		System.out.println("5 = Sports");
		System.out.println("6 = Weather");

		Scanner kb = new Scanner(System.in);
		int first, second;
		System.out.println("Enter 2 different Correspondent Numbers of given Fields");
		System.out.println("Must be between 1 - 6");
		
		first = getInputOne(kb);
		second = getInputTwo(kb,first);

		String firstSelection = RawDataManagement.getSelectedType(String.valueOf(first));
		String secondSelection = RawDataManagement.getSelectedType(String.valueOf(second));
		List<String> trainTestFilePath = RawDataManagement.createDataFile(firstSelection, secondSelection, 400);

		// create train & test files
		GenerateSVMData.generateSvmData(trainTestFilePath.get(0), firstSelection + "-vs-" + secondSelection + ".dat");

	}

	private static int getInputOne(Scanner keyboard) {
		int startr;
		do {
			System.out.println("Enter the 1st Correspondent Field Number : ");
			startr = keyboard.nextInt();
		} while ((startr != 0) && (startr >= 7));// while (!(startr >= 0 &&
													// startr % 10 == 0));
		return startr;
	}
	
	private static int getInputTwo(Scanner keyboard, int startr) {
		int endr;
		do {
			System.out.println("Enter the 2nd Correspondent Field Number : ");
			endr = keyboard.nextInt();
		} while ((endr != 0) && (endr >= 7) || (endr == startr));
		return endr;
	}
}
