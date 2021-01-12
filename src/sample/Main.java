package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.*;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Gestion Des Etudiants");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();

        Image icon = new Image(getClass().getResource("img/icon.png").toString());
        primaryStage.getIcons().add(icon);


    }



    public static void main(String[] args) {
        launch(args);
    }
}
