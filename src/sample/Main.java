package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Delta Rule Neuron Learning App");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

    public void wykres() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("wykres.fxml"));
        Stage wykres = new Stage();
        wykres.setScene(new Scene(root, 700, 400));
        wykres.setTitle("Wykres");
        wykres.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
