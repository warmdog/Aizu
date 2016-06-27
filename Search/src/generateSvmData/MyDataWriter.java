package generateSvmData;

import java.io.*;
import java.util.List;
import java.util.Map;

public class MyDataWriter {
	public void write(String outFileName, List<String> documents1, double rate) throws IOException {
		FeatureVectorGenerator generator = new FeatureVectorGenerator();
		Map<String, double[]> featureVectors = generator.generateTFIDFVectors(documents1);
		File file = new File(outFileName);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

		for (int idx = 0; idx < featureVectors.size(); idx++) {
			String keyValue = documents1.get(idx);
			// System.out.println("KeyValue: " + keyValue);
			double[] featureVector = featureVectors.get(keyValue);
			if (idx < (featureVectors.size() / 2)) {
				pw.write("+1 ");
			} else {
				pw.write("-1 ");
			}
			for (int i = 0; i < featureVector.length; i++) {
				if (featureVector[i] > 0.0000000000001) {
					pw.write(String.format("%d:%f ", i + 1, featureVector[i]));
					// System.out.print(String.format("%d:%f ", i+1,
					// featureVector[i]));
				} else {
					// pw.write("0.0 ");
					// System.out.print(String.format("%d:%f ", i+1, 0.));
				}

				if (i == featureVector.length - 1) {
					pw.write("\n");
					// System.out.print("\n");
				}

			} // end of for (int i = 0; i < featureVector.length; i++)
		} // end of for (int idx=0; idx < featureVectors.size(); idx++) {
		pw.close();
		String[] filetemp = outFileName.split("\\.");
		// for(String e: filetemp) System.out.println("Splited String: " + e);
		String baseFileName = filetemp[0];
		// System.out.println("Base file name = " + baseFileName);
		String trainFileName = baseFileName + "-train.dat";
		String testFileName = baseFileName + "-test.dat";
		File trainFile = new File(trainFileName);
		File testFile = new File(testFileName);
		PrintWriter trainpw = new PrintWriter(new BufferedWriter(new FileWriter(trainFile)));
		PrintWriter testpw = new PrintWriter(new BufferedWriter(new FileWriter(testFile)));
		int fileLen = featureVectors.size();
		int halfFileLen = fileLen / 2;
		BufferedReader readin = new BufferedReader(new FileReader(outFileName));
		String lineString = new String();
		for (int i = 0; i < halfFileLen; i++) {
			if (i < (int) (halfFileLen * rate)) { // for train data, less than
													// rate, for + data
				lineString = readin.readLine();
				lineString.trim();
				lineString = lineString + "\n";
				trainpw.write(lineString);
			} else { // for test data, for + data
				lineString = readin.readLine();
				lineString.trim();
				lineString = lineString + "\n";
				testpw.write(lineString);
			}
		} // end of for (int i..)

		for (int i = halfFileLen; i < fileLen; i++) {
			if (i < (int) (halfFileLen + halfFileLen * rate)) { // for train
																// data, less
																// than rate,
																// for - data
				lineString = readin.readLine();
				lineString.trim();
				lineString = lineString + "\n";
				trainpw.write(lineString);
			} else { // for test data, for - data
				lineString = readin.readLine();
				lineString.trim();
				lineString = lineString + "\n";
				testpw.write(lineString);
			}
		} // end of for (int i..)
		readin.close();
		trainpw.close();
		testpw.close();
		System.out.println("\n"+"Successfully Generate SVM Data File in : "+outFileName);
		// System.out.println("Data file - " + trainFileName + " wrote..");
		// System.out.println("Data file - " + testFileName + " wrote..");
	} // end of write method
} // end of DataWriter class
