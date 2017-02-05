
/*******************************************************************
 * EXCRA CREDIT WAS ATTEMTED SEE LINE 71, 193-228 :)
 *
 * Name:       Darci Saucedo 
 * Assignment: Directory Searcher
 * Date:       8/1/2016
 * SaucedoTorresDHw7.zip
 *******************************************************************/
// for some reason the .regex library did not import with java.util.*;
// I had to import some libraries individually
// I used regular expression for string validation

import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;

/* book notes
page 1279 talks about regular expressions
*/

public class MySearch {
    public static ArrayList<File> fileList = new ArrayList<File>();
    public static Scanner inf; // used for reading files

    // this is main() but I changed to start() for testing
    public static void start(String... args) {
	Scanner kb = new Scanner(System.in);
	String path = null;
	String fileName = null;
	String fileExt = null;
	String content = null;
	String date = null;

	// get inputs from user
	path = getPath(kb);
	fileName = getFileName(kb);
	fileExt = getFileExt(kb);
	content = getFileRegex(kb);
	date = getDate(kb);

	// main program method
	runSearch(path, fileName, fileExt, content, date);

	// print files
	printArrayList(fileList);

    }// end main

    public static void runSearch(String path, String fileName, String fileExt, String content, String date) {

	findAllFiles(path);

	if (fileName != null && !fileName.isEmpty()) { // search by file name
	    getFilesByName(fileName);
	}
	if (fileExt != null && !fileExt.isEmpty()) { // search by file extesion
	    getFilesByExt(fileExt);
	}
	if (content != null && !content.isEmpty()) { // search by file content
	    getFilesByContent(content);
	}
	if (date != null && !date.isEmpty()) { // search by file date
	    getFilesByDate(date);
	}

    }// end method

    // get files by file name
    public static void getFilesByName(String fileName) {
	String strToMatch = null;

	ArrayList<File> tempList = new ArrayList<File>();

	for (int i = 0; i < fileList.size(); i++) {
	    strToMatch = fileList.get(i).getAbsolutePath();
	    // if the file name matches
	    if ((Pattern.matches(".*\\Q" + fileName + "\\E.*", strToMatch))) {
		// add it to the temp list
		tempList.add(fileList.get(i));
	    }
	} // end for
	fileList.clear();
	fileList = new ArrayList<File>(tempList);

    }// end getFilesByName()

    // get files by extension
    public static void getFilesByExt(String fileExt) {
	String strToMatch = null;
	ArrayList<File> tempList = new ArrayList<File>();

	for (int i = 0; i < fileList.size(); i++) {
	    strToMatch = fileList.get(i).getAbsolutePath();
	    // if the file name matches the regular expression
	    if (Pattern.matches(".*\\.\\Q" + fileExt.toUpperCase() + "\\E", strToMatch)
		    || Pattern.matches(".*\\.\\Q" + fileExt.toLowerCase() + "\\E", strToMatch)) {
		// add it to the temp list
		tempList.add(fileList.get(i));
	    }
	} // end for
	fileList.clear(); // delete files that did not match criteria
	fileList = tempList;
    }

    // get files by content
    public static void getFilesByContent(String content) {
	String strToMatch = null;
	ArrayList<File> tempList = new ArrayList<File>();

	for (int i = 0; i < fileList.size(); i++) {
	    // connect to the first file
	    String fname = fileList.get(i).getAbsolutePath();
	    try {
		inf = new Scanner(new File(fname));
	    } catch (Exception e) {
		System.out.println("file not found");
	    }

	    // read file
	    while (inf.hasNextLine()) {
		strToMatch = inf.nextLine();
		// the the line contains text that matches the pattern of
		// "content"
		if (Pattern.matches(".*\\Q" + content + "\\E.*", strToMatch)) {
		    // add it to the temp list
		    tempList.add(fileList.get(i));
		    break; // match was found break out of this file
		}
	    } // end while

	} // end for
	  // delete files that did not match criteria
	fileList.clear();
	fileList = tempList;
    }

    // Search and store all files
    public static void findAllFiles(String path) {
	File dir = new File(path);

	File[] fileArray = dir.listFiles();

	for (File file : fileArray) {
	    if (file.isFile()) {
		fileList.add(file);
	    } else if (file.isDirectory()) { // recursive call, go in the
					     // warmhole
		findAllFiles(file.getAbsolutePath());
	    }
	}

    }// end getFiles()

    // get files by date
    public static void getFilesByDate(String date) {
	ArrayList<File> tempList = new ArrayList<File>();

	// get date from user
	String[] dateArr = date.split("/");
	int month = Integer.parseInt(dateArr[0]);
	int day = Integer.parseInt(dateArr[1]);
	int year = Integer.parseInt(dateArr[2]);

	boolean fileDateMatches = false; // will be used to match file dates

	for (int i = 0; i < fileList.size(); i++) {
	    // connect to the first file
	    String fname = fileList.get(i).getAbsolutePath();
	    try {
		File file = new File(fname);

		Calendar fileDate = new GregorianCalendar();
		// Set the date according to the last modified date of file
		fileDate.setTimeInMillis(file.lastModified());
		fileDate.add(Calendar.MONTH, 1);

		// get date fields form file
		int lastMonth = fileDate.get(Calendar.MONTH);
		int lastDay = fileDate.get(Calendar.DAY_OF_MONTH);
		int lastYear = fileDate.get(Calendar.YEAR);

		// check if the dates match
		fileDateMatches = isMatch(month, day, year, lastMonth, lastDay, lastYear);

		if (fileDateMatches) { // save the file to the temp list
		    tempList.add(fileList.get(i));
		}
	    } catch (Exception e) {
		System.out.println("file not found");
	    }
	} // end for
	  // add the templist to the filesList and delete the rest
	fileList.clear();
	fileList = tempList;

    }// end method

    // helper method for matching file dates
    public static boolean isMatch(int month, int day, int year, int lastMonth, int lastDay, int lastYear) {
	if (month == lastMonth && day == lastDay && year == lastYear) {
	    return true;
	}
	return false;
    }

    // get file by content type
    public static String getFileRegex(Scanner kb) {
	System.out.print("Enter content to search for (like cscd211 or enter for any): ");
	String regex = kb.nextLine();
	return regex;
    }

    // get file extension from user
    public static String getFileExt(Scanner kb) {
	System.out.print("Enter the file extension(like txt or enter for all): ");
	String fileExt = kb.nextLine();
	return fileExt;
    }

    // get file name from user
    public static String getFileName(Scanner kb) {
	System.out.print("Enter the file name (like myFile or enter for all): ");
	// validate later
	String fileName = kb.nextLine();
	return fileName;
    }// end getFileName()

    // print the arrayList to the screen
    public static void printArrayList(ArrayList<File> list) {
	System.out.printf("%nResults - %d entries found%n", list.size());
	for (int i = 0; i < list.size(); i++) {
	    System.out.println(list.get(i));
	}
    }// end printArrayList()

    // get the path from the user
    public static String getPath(Scanner kb) {
	System.out.print("Enter starting directory for the search: ");
	String path = kb.nextLine();

	return path;
    }// end getPath()

    public static String getDate(Scanner kb) {
	System.out.print("Enter the last modified date(11/21/2013): ");
	String date = kb.nextLine();

	return date;
    }
}// end class
