package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppController implements Initializable {

    @FXML
    private Text pathTextLabel;
    @FXML
    private Text fileExtLabel;
    @FXML
    private TextField enterPathTextField;
    @FXML
    private TextField fileExtTextField;
    @FXML
    private Button buttonEnter;
    @FXML
    private Button buttonExit;

    private static ArrayList<File> keepFiles = new ArrayList<File>();
    private Scanner input;
    private String pathOuput;

    /* method will get the String from the TextFiled on button click */
    @FXML
    public void buttonClick(ActionEvent event) {
	// get the path from the user
	String path = enterPathTextField.getText();
	if(!isValidPath(path)) {
	    // show error message box
	    showErrorMsgBox(path);
	    enterPathTextField.clear();
	    return;
	}
	// good path
	
	pathOuput = path;
	String fileExt = fileExtTextField.getText();

	readFiles(path, fileExt);
	input = new Scanner(System.in);
	int count = processFiles(input);
	
	// show the results message box
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("SourceCode LineCounter");
	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	stage.getIcons().add(new Image(this.getClass().getResource("java_icon.png").toString()));
	alert.setHeaderText("Directory: "+ pathOuput);
	alert.setContentText(printMsg(count));

	alert.showAndWait();
    }
    
    @FXML
    public void exitButtonClick(ActionEvent event) {
	Stage stage = (Stage) buttonExit.getScene().getWindow();
	stage.close();
    }
    
    private void showErrorMsgBox(String path) {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("Invalid path");
	alert.setHeaderText(null);
	alert.setContentText("Path: "+ path +" is invalid");

	alert.showAndWait();
    }

    private boolean isValidPath(String path) {
	if(path == null || path.isEmpty()) {
	    return false;
	}
	File file = new File(path);
	if(file.isDirectory()) {
	   return true;
	}
	return false;
    }

    private int processFiles(Scanner fileInput) {
	int lineCount = 0;
	int blockCommentCount = 0;
	boolean startedBlock = false;
	
	/* main algorithm */
	for (File file : keepFiles) {
	    try {
		fileInput = new Scanner(file);
		while (fileInput.hasNextLine()) {

		    String line = fileInput.nextLine().trim();
		    if (line.equals("") || line.startsWith("//")) {
			continue;
		    }
		    lineCount++;
		    // System.out.println(line);

		    if (line.startsWith("/**") || line.startsWith("/*")) {
			blockCommentCount++;
			startedBlock = true;
		    } 
		    else if (line.endsWith("*/") || line.startsWith("*/")) {
			blockCommentCount++;
			lineCount = getCount(lineCount, blockCommentCount, startedBlock);
			// reset block comment variables
			startedBlock = false;
			blockCommentCount = 0;
		    } 
		    else {
			if (startedBlock) {
			    blockCommentCount++;
			}
		    }
		}
		//System.out.printf("Lines for this %s file %d\n", file.getName(), lineCount);
	    } catch (FileNotFoundException e) {
		System.out.println("Error conencting to: " + file.getName());
	    }
	}// end reading all the files
	return lineCount;
    }

    // subtract the block comment lines
    private int getCount(int lineCount, int blockCount, boolean startedBlock) {
	if(startedBlock) {
	    return lineCount - blockCount;
	}
	return lineCount;
    }

    /* save the files we want to the keepFiles ArrayList */
    private void readFiles(String dirPath, String fileExt) {
	File dir = new File(dirPath);
	File[] filesArray = dir.listFiles();

	for (File f : filesArray) {

	    if (f.isFile()) {
		if (f.getName().endsWith(fileExt)) {
		    // if the files match our file extension add them to our arraylist
		    keepFiles.add(f);
		}
	    }
	    // making the recursive call on a directory
	    else if (f.isDirectory()) {
		readFiles(f.getAbsolutePath(), fileExt);
	    }
	}
    }

    private String printFiles() {
	String retString = "";
	for (File file : keepFiles) {
	    retString += file.getName() + "\n";
	}
	return retString;
    }
    
    public String printMsg(int sourceCodeLines) {
	String retString = "";
	retString += "Source lines of code: " + sourceCodeLines+"\n";
	retString += "Files processed: " + keepFiles.size() + "\n";
	retString += "\n"+ printFiles();
	return retString;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub

    }

}
