package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = new FXMLLoader().load(new FileInputStream("D:\\JavaProjects\\CourseWork_Java\\CourseWork_KiraHovorukha\\FunctionPlotter.fxml"));
            Scene scene = new Scene(root, 800, 655);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Курсова робота");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
