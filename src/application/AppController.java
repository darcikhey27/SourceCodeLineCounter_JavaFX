package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AppController implements Initializable {
    
    @FXML private Text pathTextLabel;
    @FXML private Text fileExtLabel;
    @FXML private TextField enterPathTextField;
    @FXML private TextField fileExtTextField;
    @FXML private Button buttonEnter;
    
    Scanner inputFile; 
    private int linesCount;
    private int filesCount;
    
    
    /* method will get the String from the TextFiled on button click*/
    @FXML 
    public void buttonClick(ActionEvent event) {
	String path = enterPathTextField.getText();
	String fileExt = fileExtTextField.getText();
	
	//validatePath(path);
	System.out.println("the path you enter was "+ path);
	
	
	
	startProgram(path, fileExt);
	
	
	
	
    }
    
    private void startProgram(String path, String fileExt) {
	// C:\testDir
	/* pre-test path */
	File dir = new File(path); // create a File direcotry object
	
	// count files in this directory
	File[] sourceFilesArray = dir.listFiles();
	
	// save the number of files in this dir
	filesCount = sourceFilesArray.length;
	
	// read files
	
	readFiles(sourceFilesArray);
	
	
	System.out.println("Couting source files with extension "+ fileExt);
	
	
	printFiles(sourceFilesArray); // print the files in that direcotry
	
	
	
	// isDirectory()
	// isFile()
	
	
    }
    private void readFiles(File[] sourceFiles) {
	int lines = 0;
	for(File file : sourceFiles) {
	    
	    //read file
	    processFile(file);
	
	}
	
    }

    private void processFile(File file) {
	try {
	    // connect to one file 
	    inputFile = new Scanner(file);
	    int linesCount = 0;
	    while(inputFile.hasNextLine()) {
		
		String lineOfCode = inputFile.nextLine();
		linesCount = processLineOfCode(lineOfCode);
		
		//System.out.println(inputFile.nextLine());
		
		linesCount++;
	    }
	    System.out.println("done counting file: "+ file.getName());
	    System.out.println("Total lines for this file were: "+ linesCount);
	    
	    
	    //inputFile.close();
	    
	} 
	catch (FileNotFoundException e) {
	    
	    System.out.println("Error reading file ");
	}
	
    }
    private int processLineOfCode(String lineOfCode) {
	
	// TODO if the line of code is blank do nothing
	// if the line is a \n do nothing
	// if the line is comment do nothing etc...
	//
	int lineCounter = 0;
	if(lineOfCode != null && !lineOfCode.equals("\n")){
	    
	}
	
	return 0;
    }

    private void printFiles(File[] files) {
	for(File file : files) {
	    System.out.println(file.getName());
	}
    }
    
    
    
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
	
    }
	
}
