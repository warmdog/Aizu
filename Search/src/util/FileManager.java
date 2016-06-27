package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {
	
	public static List<File> getFilePaths(String rawfilePath) {
		File directory = new File(rawfilePath);

		List<File> allFilesList = new ArrayList<File>();

		// get all the files from a directory
		File[] fList = directory.listFiles();
		
		for (File file : fList) {
			if (file.isFile()) {
				allFilesList.add(file);//add txt files
			} else if (file.isDirectory()) {
				getSubFolderFilePaths(file.getAbsolutePath());
				allFilesList.addAll(getSubFolderFilePaths(file.getAbsolutePath()));
			}
		}
		// System.out.println(fList);
		return allFilesList;
	}
	
	public static List<File> getSubFolderFilePaths(String rawfilePath) {
		File directory = new File(rawfilePath);

		List<File> mainSubFolderList = new ArrayList<File>();
		List<File> subSubFolderList = new ArrayList<File>();
		List<File> allFilesList = new ArrayList<File>();

		// get all the files from a directory
		File[] fList = directory.listFiles();
		
		mainSubFolderList.addAll(Arrays.asList(fList));//add main sub folder paths
	
		return mainSubFolderList;
	}
	
	public static List<String> readFileAsList(String filePath) throws IOException {

		List<String> fileContent = new ArrayList<String>();
		
        BufferedReader readin = new BufferedReader(new FileReader(filePath));
        if (readin == null) {
            System.out.println("Input file has problem...");
            System.exit(0);
        }

        String lineString = new String();
        while ((lineString = readin.readLine()) != null) {
        	fileContent.add(lineString);
        }
        readin.close();

        return fileContent;
    }
	
	public static String readFileAsString(String filePath) throws IOException {

		String content = "";
		
        BufferedReader readin = new BufferedReader(new FileReader(filePath));
        if (readin == null) {
            System.out.println("Input file has problem...");
            System.exit(0);
        }

        String lineString = new String();
        while ((lineString = readin.readLine()) != null) {
        	if(lineString.length()!=0)
        		content = content+" "+lineString;
        }
        readin.close();

        return content;
    }
	
	public static List<File> listf(String directoryName) {
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }
        //System.out.println(fList);
        return resultList;
    }
	
	public static long fileCount(String folderPath) throws IOException{
		long count = Files.find(
			    Paths.get(folderPath), 
			    5,  // how deep do we want to descend
			    (path, attributes) -> attributes.isRegularFile()
			).count(); // '-1' because '/tmp' is also counted in
		
		return count;
	}
	
	public static long folderCount(String folderPath) throws IOException{
		long count = Files.find(
			    Paths.get(folderPath), 
			    1,  // how deep do we want to descend
			    (path, attributes) -> attributes.isDirectory()
			).count() - 1; // '-1' because '/tmp' is also counted in
		
		return count;
	}
	
	public static List<String> retrieveFirstHalf(String rawfilePath, int divideNumber) throws IOException {
		// TODO Auto-generated method stub
		List<File> mainSubfolderPaths = FileManager.getSubFolderFilePaths(rawfilePath);
		List<String> fileContent = new ArrayList<String>();
		
		
		int txtFileCount = 0;
		for(File subFolder : mainSubfolderPaths){
			List<File> txtFilePath =  FileManager.getFilePaths(subFolder.getAbsolutePath());
			
			List<String> tempFileContent = new ArrayList<String>();
			for(File txt_file_path : txtFilePath){
				txtFileCount++;
				fileContent.add(FileManager.readFileAsString(txt_file_path.getAbsolutePath()));
				if(txtFileCount >= divideNumber) break;
			}
		}
//		System.out.println("\n"+"Rendered = "+txtFileCount+ " number of files in given Selection");
		return fileContent;
	}
	
	public static List<String> retrieveBottomHalf(String rawfilePath, int divideNumber) throws IOException {
		// TODO Auto-generated method stub
		List<File> mainSubfolderPaths = FileManager.getSubFolderFilePaths(rawfilePath);
		List<String> fileContent = new ArrayList<String>();
		
		
		int txtFileCount = 0;
		for(File subFolder : mainSubfolderPaths){
			List<File> txtFilePath =  FileManager.getFilePaths(subFolder.getAbsolutePath());
			
			List<String> tempFileContent = new ArrayList<String>();
			for(File txt_file_path : txtFilePath){
				txtFileCount++;
				if(txtFileCount>=divideNumber)fileContent.add(FileManager.readFileAsString(txt_file_path.getAbsolutePath()));
				if(txtFileCount >= 2*divideNumber)break;
			}
		}
//		System.out.println("Rendered = "+divideNumber+" to "+(txtFileCount-1)+ " number of files in given Selection");
		return fileContent;
	}

	public static String createFile(String fileTop, String fileBottom, List<String> topRawContent, List<String> bottomRawContent, String type) {
		// TODO Auto-generated method stub
		try {
//			File file = new File("/users/mkyong/filename.txt");
			String filePath = CommonProperities.Result_InputFiles_to_SVM+type+"_DataSet_"+fileTop+"-vs-"+fileBottom;
			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(String firstHalf: topRawContent){
				bw.write(firstHalf);
				bw.write("\n");
			}
//			bw.write("\n");bw.write("\n");bw.write("\n");bw.write("\n");bw.write("\n");
			for(String secondHalf: bottomRawContent){
				bw.write(secondHalf);
				bw.write("\n");
			}
			
			bw.close();
			return file.getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Done");
		return null;
	}
}
