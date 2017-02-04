package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
    private int linesCount;
    
    
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
	System.out.println("Couting source files with extension "+ fileExt);
	
	
	printFiles(dir); // print the files in that direcotry
	
	
	
	// isDirectory()
	// isFile()
	
	
    }
    private void printFiles(File dir) {
	String[] files = dir.list();
	for(String file : files) {
	    System.out.println(file);
	}
    }
    
    
    
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
	
    }
	
}
