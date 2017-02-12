package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
	try {
	    Parent root = FXMLLoader.load(getClass().getResource("FXMLdocument.fxml"));
	    
	    Scene scene = new Scene(root);
	    //primaryStage.getIcons().add(new Image("javaicon.ico"));
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    
	    
	    primaryStage.setTitle("DK SourceCode LineCounter");
	  
	    
	   Image icon = new Image(getClass().getResourceAsStream("java_icon.png"));
	    primaryStage.getIcons().add(icon);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    primaryStage.setResizable(false);

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	launch(args);
    }
}
