package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
	try {
	    Parent root = FXMLLoader.load(getClass().getResource("FXMLdocument.fxml"));
	    Scene scene = new Scene(root);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    primaryStage.setTitle("Darci source code count");
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
