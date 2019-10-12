package sample;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Controller {

    @FXML
    private Button end, chooseFunction, learningVectors, resetApp, beginningVectors; //przyciski w menu po lewej stronie

    @FXML
    private TabPane pane; //zdefiniowany główny panel kart

    @FXML
    private Tab first, second, third, fourth; //zdefiniowane poszczególne karty

    @FXML
    private AnchorPane anchorpane3, anchorpane4, anchorpane5; //zdefiniowane panele z Y1 oraz Z2 opcjonalnymi przy wyborze par

    @FXML
    private RadioButton vrb1, vrb2; //Radio buttony wykorzystywane przy wyborze jednej lub dwóch par uczących

    @FXML
    private Button save1, save2, save3, learn, reset; //Buttony zapisujące wybrane opcje aplikacji

    @FXML
    private RadioButton firstrb, secondrb; //Radio buttony wykorzystywane przy wyborze funkcji aktywacji

    @FXML
    private TextField x_1, x_2, x_3, x_4, z_1, z_2, z_3, z_4,y_1, y_2, y_3, y_4, z_11, z_12, z_13, z_14;//Pola tekstowe używane przy wprowadzaniu poszczególnych wartości elementów wektorów par uczących

    @FXML
    private TextField w_01, w_02, w_03, w_04, allItems; //Pola tekstowe używane przy wprowdzaniu poszczególnych elementów wektora wag początkowych

    @FXML
    private FileWriter logstream;

    @FXML
    private BufferedWriter bw;

    @FXML
    private void closeButtonAction() { //funkcja zamykająca aplikację
        Stage stage = (Stage) end.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickCF() { //po kliknięciu przycisku "Wybór funkcji aktywacji" otwiera odpowiednią kartę z panelu

        chooseFunction.setOnAction((evt) -> {
            pane.getSelectionModel().select(first);
        });
    }

    @FXML
    private void clickLV() { //po kliknięciu przycisku "Wektory uczące" otwiera odpowiednią kartę z panelu

        learningVectors.setOnAction((evt) -> {
            pane.getSelectionModel().select(second);
            vrb1.setSelected(true);
        });
    }

    @FXML
    private void clickBV() { //po kliknięciu przycisku "Wektor wag początkowych" otwiera odpowiednią kartę z panelu

        beginningVectors.setOnAction((evt) -> {
            pane.getSelectionModel().select(third);
        });
    }

    /*@FXML
    private void clickLN() { //po kliknięciu przycisku "Uczenie neuronów" otwiera odpowiednią kartę z panelu
        learningNeurons.setOnAction((evt) -> {
            pane.getSelectionModel().select(fourth);
        });
    }*/

    @FXML
    private void closesecondpow(){ //funkcja, która po wyborze jednej pary uczącej dezaktywuje okna z wektorami Y i Z2

        anchorpane3.disableProperty().bind(vrb1.selectedProperty());
        anchorpane4.disableProperty().bind(vrb1.selectedProperty());
    }

    @FXML
    private void clickSave1() { //funkcja po kliknięciu przycisku zapisz w karcie "Wybór funkcji aktywacji" zapisuje ją w pliku log.txt i wyświetla w cmd
        save1.setOnAction((evt) -> {

            try {

                FileWriter fstream = new FileWriter("log.txt");
                BufferedWriter output = new BufferedWriter(fstream);

                if (firstrb.isSelected()){
                    System.out.println("Wybrano funkcję liniową!");
                    output.write("Wybrano funkcję liniową!");
                }
                else if (secondrb.isSelected()) {
                    System.out.println("Wybrano funkcję sigmoidalną bipolarną!");
                    output.write("Wybrano funkcję sigmoidalną bipolarną!");
                    output.close();
                }
                else System.out.println("Wybrano funkcję sigmoidalną unipolarną!");
                output.write("Wybrano funkcję sigmoidalną unipolarną!");
                output.close();
            } catch (Exception e){
                System.err.println("Error: " + e.getMessage());
            }

            save1.setDisable(true);
        });


    }

    @FXML
    private void clickSave2(){ //funkcja, która po kliknięciu zaakceptuj w karcie "Wektory uczące" wypisuje je wybrane w cmd
        save2.setOnAction((evt) -> {
            try {
                FileWriter fstream = new FileWriter("log.txt");
                BufferedWriter output = new BufferedWriter(fstream);


            if(vrb1.isSelected()) { //gdy wybrano jedną parę uczącą
                System.out.println("Wprowadzono następującą parę wektorów: X=["+x_1.getText()+","+x_2.getText()+","+x_3.getText()+","+x_4.getText()+"] oraz Z=["+z_1.getText()+","+z_2.getText()+","+z_3.getText()+","+z_4.getText()+"]");
                output.write("Wprowadzono następującą parę wektorów: X=["+x_1.getText()+","+x_2.getText()+","+x_3.getText()+","+x_4.getText()+"] oraz Z=["+z_1.getText()+","+z_2.getText()+","+z_3.getText()+","+z_4.getText()+"]");
                output.close();
                save2.setDisable(true);}
            else if (vrb2.isSelected()){ //gdy wybrano dwie pary uczące
                System.out.println("Wprowadzono następujące pary wektorów: X=["+x_1.getText()+","+x_2.getText()+","+x_3.getText()+","+x_4.getText()+"] oraz Z1=["+z_1.getText()+","+z_2.getText()+","+z_3.getText()+","+z_4.getText()+"] i Y=["+y_1.getText()+","+y_2.getText()+","+y_3.getText()+","+y_4.getText()+"] oraz Z2=["+z_11.getText()+","+z_12.getText()+","+z_13.getText()+","+z_14.getText()+"]");
                save2.setDisable(true);}
            else{
                System.out.println("Nie wybrano żadnej funkcji, wybierz ponownie!");
            }
            }catch (Exception e){
                System.err.println("Error: " + e.getMessage());
            }
        });
    }

    @FXML
    private void clickSave3(){ //funkcja, która po kliknięciu zapisz w karcie "Wektor wag początkowych" wypisuje go w cmd

                System.out.println("Wprowadzono następujący wektor wag początkowych: W=[" + w_01.getText() + "," + w_02.getText() + "," + w_03.getText() + "," + w_04.getText() + "]");

            save3.setDisable(true);
    }

    @FXML
    private void writeLog(){
        try{

            Date date = new Date() ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

            logstream = new FileWriter("log-"+dateFormat.format(date)+".txt", true);
            bw = new BufferedWriter(logstream);

            bw.write("--------------------------------------------------------------------------------------------------------");
            bw.newLine();
            bw.newLine();
            bw.write("                             LOG -  DELTA RULE NEURON LEARNING APPLICATION                              ");
            bw.newLine();
            bw.newLine();
            bw.write("--------------------------------------------------------------------------------------------------------");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickLearn(){

        learn.setDisable(true);

        writeLog();

        if(vrb1.isSelected() || firstrb.isSelected()){
            try{
                Date date = new Date() ;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

                logstream = new FileWriter("log-"+dateFormat.format(date)+".txt", true);
                bw = new BufferedWriter(logstream);

                bw.write("Wybrano funkcję liniową!");
                bw.newLine();
                bw.write("Wprowadzono następującą parę wektorów: X=["+x_1.getText()+","+x_2.getText()+","+x_3.getText()+","+x_4.getText()+"] oraz Z=["+z_1.getText()+","+z_2.getText()+","+z_3.getText()+","+z_4.getText()+"]");
                bw.newLine();
                bw.write("Wprowadzono następujący wektor wag początkowych: W=[" + w_01.getText() + "," + w_02.getText() + "," + w_03.getText() + "," + w_04.getText() + "]");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void resetApp() {

        x_1.clear();
        x_2.clear();
        x_3.clear();
        x_4.clear();
        z_1.clear();
        z_2.clear();
        z_3.clear();
        z_4.clear();
        y_1.clear();
        y_2.clear();
        y_3.clear();
        y_4.clear();
        z_11.clear();
        z_12.clear();
        z_13.clear();
        z_14.clear();
        w_01.clear();
        w_02.clear();
        w_03.clear();
        w_04.clear();

        vrb1.setSelected(false);
        vrb2.setSelected(false);
        firstrb.setSelected(false);
        secondrb.setSelected(false);

        pane.getSelectionModel().select(first);

        save1.setDisable(false);
        save2.setDisable(false);
        save3.setDisable(false);
        learn.setDisable(false);

    }

}

