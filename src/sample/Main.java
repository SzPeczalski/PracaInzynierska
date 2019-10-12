package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Delta Rule Neuron Learning App");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }


    public void writeLog(){
        PrintWriter log = null;

        try{
            log = new PrintWriter("users.txt");
            BufferedWriter bw = new BufferedWriter(log);
            bw.write("Something new");
            bw.newLine();
            bw.write("Something more'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }


}
