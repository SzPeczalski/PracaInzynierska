package sample;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {

    @FXML
    private Button end; //przyciski w menu po lewej stronie

    @FXML
    private TabPane pane; //zdefiniowany główny panel kart

    @FXML
    private Tab first; //zdefiniowane poszczególne karty

    @FXML
    private AnchorPane anchorpane3, anchorpane4; //zdefiniowane panele z Y1 oraz Z2 opcjonalnymi przy wyborze par

    @FXML
    private RadioButton vrb1, vrb2; //Radio buttony wykorzystywane przy wyborze jednej lub dwóch par uczących

    @FXML
    private Button save1, save2, save3, save4, learn, clear_results, clear_log; //Buttony zapisujące wybrane opcje aplikacji

    @FXML
    private RadioButton firstrb, secondrb; //Radio buttony wykorzystywane przy wyborze funkcji aktywacji

    @FXML
    private TextField x_1, x_2, x_3, x_4, z_1, z_2, z_3, z_4, y_1, y_2, y_3, y_4, z_11, z_12, z_13, z_14;//Pola tekstowe używane przy wprowadzaniu poszczególnych wartości elementów wektorów par uczących

    @FXML
    private TextField w_01, w_02, w_03, w_04, step, learning_index; //Pola tekstowe używane przy wprowdzaniu poszczególnych elementów wektora wag początkowych

    @FXML
    private FileWriter logstream, logstream1;

    @FXML
    private BufferedWriter bw, bw1;

    @FXML
    private Label labelCB;

    @FXML
    private CheckBox checkbox1;

    @FXML
    private void closeButtonAction() { //funkcja zamykająca aplikację
        Stage stage = (Stage) end.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void closesecondpow() { //funkcja, która po wyborze jednej pary uczącej dezaktywuje okna z wektorami Y i Z2
        anchorpane3.disableProperty().bind(vrb1.selectedProperty());
        anchorpane4.disableProperty().bind(vrb1.selectedProperty());
    }

    @FXML
    private void clickSave1() { //funkcja po kliknięciu przycisku zapisz w karcie "Wybór funkcji aktywacji" zapisuje ją w pliku log.txt i wyświetla w cmd
        save1.setOnAction((evt) -> {

            if (firstrb.isSelected()) {
                System.out.println("Wybrano funkcję liniową!");
            } else if (secondrb.isSelected()) {
                System.out.println("Wybrano funkcję sigmoidalną bipolarną!");
            } else System.out.println("Wybrano funkcję sigmoidalną unipolarną!");

            save1.setDisable(true);
        });
    }

    @FXML
    private void clickSave2() { //funkcja, która po kliknięciu zaakceptuj w karcie "Wektory uczące" wypisuje je wybrane w cmd
        save2.setOnAction((evt) -> {

            if (vrb1.isSelected()) { //gdy wybrano jedną parę uczącą
                System.out.println("Wprowadzono następującą parę wektorów: X = [" + x_1.getText() + " ; " + x_2.getText() + " ; " + x_3.getText() + " ; " + x_4.getText() + "] oraz Z = [" + z_1.getText() + " ; " + z_2.getText() + " ; " + z_3.getText() + " ; " + z_4.getText() + "]");
                save2.setDisable(true);
            } else if (vrb2.isSelected()) { //gdy wybrano dwie pary uczące
                System.out.println("Wprowadzono następujące pary wektorów: X = [" + x_1.getText() + " ; " + x_2.getText() + " ; " + x_3.getText() + " ; " + x_4.getText() + "] oraz Z1 = [" + z_1.getText() + " ; " + z_2.getText() + " ; " + z_3.getText() + " ; " + z_4.getText() + "] i Y = [" + y_1.getText() + " ; " + y_2.getText() + " ; " + y_3.getText() + " ; " + y_4.getText() + "] oraz Z2 = [" + z_11.getText() + " ; " + z_12.getText() + " ; " + z_13.getText() + " ; " + z_14.getText() + "]");
                save2.setDisable(true);
            } else {
                System.out.println("Nie wybrano żadnej funkcji, wybierz ponownie!");
            }

        });
    }

    @FXML
    private void clickSave3() { //funkcja, która po kliknięciu zapisz w karcie "Wektor wag początkowych" wypisuje go w cmd

        System.out.println("Wprowadzono następujący wektor wag początkowych: W= [ " + w_01.getText() + " ; " + w_02.getText() + " ; " + w_03.getText() + " ; " + w_04.getText() + "]");

        save3.setDisable(true);
    }

    @FXML
    private void clickSave4() {
        if (step.isDisabled()) {
            System.out.println("Wprowadzono następujący współczynnik uczenia: " + learning_index.getText());
        } else {
            System.out.println("Wprowadzono następujący współczynnik uczenia: " + learning_index.getText());
            System.out.println("Wprowadzono następujący krok: " + step.getText());
        }
        save4.setDisable(true);
    }

    @FXML
    private void writeLog() {
        try {

            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
            bw = new BufferedWriter(logstream);

            bw.write("--------------------------------------------------------------------------------------------------------");
            bw.newLine();
            bw.newLine();
            bw.write("                             LOG -  DELTA RULE NEURON LEARNING APPLICATION                              ");
            bw.newLine();
            bw.write("                                        " + dateFormat.format(date) + "r.  godz: " + timeFormat.format(date) + "                                     ");
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
    private void makingCalculations1() {
        double x1 = Integer.parseInt(x_1.getText());
        double x2 = Integer.parseInt(x_2.getText());
        double x3 = Integer.parseInt(x_3.getText());
        double x4 = Integer.parseInt(x_4.getText());
        double w1 = Integer.parseInt(w_01.getText());
        double w2 = Integer.parseInt(w_02.getText());
        double w3 = Integer.parseInt(w_03.getText());
        double w4 = Integer.parseInt(w_04.getText());
        double z = Integer.parseInt(z_1.getText());

        double wart_wyjsc;
        double blad_uczenia;
        double krok = Integer.parseInt(step.getText());
        double wspolczynnik_uczenia = Double.parseDouble(learning_index.getText());

        System.out.println("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
            bw = new BufferedWriter(logstream);
            logstream1 = new FileWriter("wynik.txt", true);
            bw1 = new BufferedWriter(logstream1);

            bw.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw.newLine();
            bw.close();
            bw1.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw1.newLine();
            bw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= krok; i++) {
            wart_wyjsc = x1 * w1 + x2 * w2 + x3 * w3 + x4 * w4;
            blad_uczenia = z - wart_wyjsc;
            w1 = w1 + wspolczynnik_uczenia * blad_uczenia * x1;
            w2 = w2 + wspolczynnik_uczenia * blad_uczenia * x2;
            w3 = w3 + wspolczynnik_uczenia * blad_uczenia * x3;
            w4 = w4 + wspolczynnik_uczenia * blad_uczenia * x4;

            DecimalFormat df = new DecimalFormat("#.######");

            System.out.println("Wektor wag początkowych kroku W(" + i + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");

            try {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                bw = new BufferedWriter(logstream);
                logstream1 = new FileWriter("wynik.txt",true);
                bw1 = new BufferedWriter(logstream1);

                bw.write("Wektor wag początkowych kroku W(" + i + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw.newLine();
                bw.close();
                bw1.write("Wektor wag początkowych kroku W(" + i + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw1.newLine();
                bw1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void makingCalculations2() {
        double x1 = Integer.parseInt(x_1.getText());
        double x2 = Integer.parseInt(x_2.getText());
        double x3 = Integer.parseInt(x_3.getText());
        double x4 = Integer.parseInt(x_4.getText());
        double w1 = Integer.parseInt(w_01.getText());
        double w2 = Integer.parseInt(w_02.getText());
        double w3 = Integer.parseInt(w_03.getText());
        double w4 = Integer.parseInt(w_04.getText());
        double z = Integer.parseInt(z_1.getText());

        double wart_sygnalu;
        double wart_wyjsc;
        double blad_uczenia;
        double krok = Integer.parseInt(step.getText());
        double wspolczynnik_uczenia = Double.parseDouble(learning_index.getText());

        double e_upper;
        double e_lower;


        double funkcja_pochodna;
        double wynik;
        double bigDelta;

        System.out.println("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
            bw = new BufferedWriter(logstream);
            logstream1 = new FileWriter("wynik.txt", true);
            bw1 = new BufferedWriter(logstream1);

            bw.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw.newLine();
            bw.close();
            bw1.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw1.newLine();
            bw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= krok; i++) {
            wart_sygnalu = x1 * w1 + x2 * w2 + x3 * w3 + x4 * w4;
            wart_wyjsc = 5 * (Math.pow(Math.E, wart_sygnalu) - Math.pow(Math.E, -wart_sygnalu)) / (Math.pow(Math.E, wart_sygnalu) + Math.pow(Math.E, -wart_sygnalu));
            blad_uczenia = z - wart_wyjsc;
            e_upper = Math.pow(Math.E, wart_sygnalu);
            e_lower = Math.pow(Math.E, -wart_sygnalu);
            funkcja_pochodna = Math.pow((e_upper + e_lower), 2);
            wynik = 20 / funkcja_pochodna;
            bigDelta = blad_uczenia * wynik;

            w1 = w1 + wspolczynnik_uczenia * bigDelta * x1;
            w2 = w2 + wspolczynnik_uczenia * bigDelta * x2;
            w3 = w3 + wspolczynnik_uczenia * bigDelta * x3;
            w4 = w4 + wspolczynnik_uczenia * bigDelta * x4;

            DecimalFormat df = new DecimalFormat("#.######");

            System.out.println("Wektor wag początkowych kroku W(" + i + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
            try {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                bw = new BufferedWriter(logstream);
                logstream1 = new FileWriter("wynik.txt", true);
                bw1 = new BufferedWriter(logstream1);

                bw.write("Wektor wag początkowych kroku W(" + i + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw.newLine();
                bw.close();
                bw1.write("Wektor wag początkowych kroku W(" + i + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw1.newLine();
                bw1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void makingCalculations3() {
        double x1 = Integer.parseInt(x_1.getText());
        double x2 = Integer.parseInt(x_2.getText());
        double x3 = Integer.parseInt(x_3.getText());
        double x4 = Integer.parseInt(x_4.getText());
        double w1 = Integer.parseInt(w_01.getText());
        double w2 = Integer.parseInt(w_02.getText());
        double w3 = Integer.parseInt(w_03.getText());
        double w4 = Integer.parseInt(w_04.getText());
        double z = Integer.parseInt(z_1.getText());

        double wart_sygnalu;
        double wart_wyjsc;
        double blad_uczenia;
        double krok = Integer.parseInt(step.getText());
        double wspolczynnik_uczenia = Double.parseDouble(learning_index.getText());

        double e_lower;

        double funkcja_pochodna;
        double bigDelta;

        System.out.println("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
            bw = new BufferedWriter(logstream);
            logstream1 = new FileWriter("wynik.txt", true);
            bw1 = new BufferedWriter(logstream1);

            bw.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw.newLine();
            bw.close();
            bw1.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw1.newLine();
            bw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= krok; i++) {
            wart_sygnalu = x1 * w1 + x2 * w2 + x3 * w3 + x4 * w4;
            wart_wyjsc = 2 / (1 + (Math.pow(Math.E, -wart_sygnalu)));//5*(Math.pow(Math.E,wart_sygnalu)-Math.pow(Math.E,-wart_sygnalu))/(Math.pow(Math.E,wart_sygnalu)+Math.pow(Math.E,-wart_sygnalu));
            blad_uczenia = z - wart_wyjsc;
            e_lower = Math.pow(Math.E, -wart_sygnalu);
            funkcja_pochodna = (2 * Math.pow(Math.E, -wart_sygnalu) / Math.pow(1 + e_lower, 2));
            bigDelta = blad_uczenia * funkcja_pochodna;

            w1 = w1 + wspolczynnik_uczenia * bigDelta * x1;
            w2 = w2 + wspolczynnik_uczenia * bigDelta * x2;
            w3 = w3 + wspolczynnik_uczenia * bigDelta * x3;
            w4 = w4 + wspolczynnik_uczenia * bigDelta * x4;

            DecimalFormat df = new DecimalFormat("#.######");

            System.out.println("Wektor wag początkowych kroku W(" + i + ") = {" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
            try {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                bw = new BufferedWriter(logstream);
                logstream1 = new FileWriter("wynik.txt", true);
                bw1 = new BufferedWriter(logstream1);

                bw.write("Wektor wag początkowych kroku W(" + i + ") = {" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw.newLine();
                bw.close();
                bw1.write("Wektor wag początkowych kroku W(" + i + ") = {" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw1.newLine();
                bw1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void makingCalculations4() {
        double x1 = Integer.parseInt(x_1.getText());
        double x2 = Integer.parseInt(x_2.getText());
        double x3 = Integer.parseInt(x_3.getText());
        double x4 = Integer.parseInt(x_4.getText());
        double w1 = Integer.parseInt(w_01.getText());
        double w2 = Integer.parseInt(w_02.getText());
        double w3 = Integer.parseInt(w_03.getText());
        double w4 = Integer.parseInt(w_04.getText());
        double y1 = Integer.parseInt(y_1.getText());
        double y2 = Integer.parseInt(y_2.getText());
        double y3 = Integer.parseInt(y_3.getText());
        double y4 = Integer.parseInt(y_4.getText());
        double z1 = Integer.parseInt(z_1.getText());
        double z2 = Integer.parseInt(z_11.getText());


        double wart_wyjsc;
        double blad_uczenia;
        int krok = Integer.parseInt(step.getText());
        double wspolczynnik_uczenia = Double.parseDouble(learning_index.getText());

        System.out.println("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
            bw = new BufferedWriter(logstream);
            logstream1 = new FileWriter("wynik.txt", true);
            bw1 = new BufferedWriter(logstream1);

            bw.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw.newLine();
            bw.close();
            bw1.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw1.newLine();
            bw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        w1 = 0;
        w2 = 0;
        w3 = 0;
        w4 = 0;

        for (int i = 0; i <= krok - 1; i++) {

            if (i % 2 == 0) {
                wart_wyjsc = x1 * w1 + x2 * w2 + x3 * w3 + x4 * w4;
                blad_uczenia = z1 - wart_wyjsc;
                w1 = w1 + wspolczynnik_uczenia * blad_uczenia * x1;
                w2 = w2 + wspolczynnik_uczenia * blad_uczenia * x2;
                w3 = w3 + wspolczynnik_uczenia * blad_uczenia * x3;
                w4 = w4 + wspolczynnik_uczenia * blad_uczenia * x4;
            } else {
                wart_wyjsc = y1 * w1 + y2 * w2 + y3 * w3 + y4 * w4;
                blad_uczenia = z2 - wart_wyjsc;
                w1 = w1 + wspolczynnik_uczenia * blad_uczenia * y1;
                w2 = w2 + wspolczynnik_uczenia * blad_uczenia * y2;
                w3 = w3 + wspolczynnik_uczenia * blad_uczenia * y3;
                w4 = w4 + wspolczynnik_uczenia * blad_uczenia * y4;
            }


            DecimalFormat df = new DecimalFormat("#.######");

            System.out.println("Wektor wag początkowych kroku W(" + (i + 1) + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");


            try {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                bw = new BufferedWriter(logstream);
                logstream1 = new FileWriter("wynik.txt", true);
                bw1 = new BufferedWriter(logstream1);

                bw.write("Wektor wag początkowych kroku W(" + (i + 1) + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw.newLine();
                bw.close();
                bw1.write("Wektor wag początkowych kroku W(" + (i + 1) + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw1.newLine();
                bw1.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void makingCalculations5() {
        double x1 = Integer.parseInt(x_1.getText());
        double x2 = Integer.parseInt(x_2.getText());
        double x3 = Integer.parseInt(x_3.getText());
        double x4 = Integer.parseInt(x_4.getText());
        double w1 = Integer.parseInt(w_01.getText());
        double w2 = Integer.parseInt(w_02.getText());
        double w3 = Integer.parseInt(w_03.getText());
        double w4 = Integer.parseInt(w_04.getText());
        double y1 = Integer.parseInt(y_1.getText());
        double y2 = Integer.parseInt(y_2.getText());
        double y3 = Integer.parseInt(y_3.getText());
        double y4 = Integer.parseInt(y_4.getText());
        double z1 = Integer.parseInt(z_1.getText());
        double z2 = Integer.parseInt(z_11.getText());

        double wart_sygnalu;
        double wart_wyjsc;
        double blad_uczenia;
        int krok = Integer.parseInt(step.getText());
        double wspolczynnik_uczenia = Double.parseDouble(learning_index.getText());

        double e_upper;
        double e_lower;


        double funkcja_pochodna;
        double wynik;
        double bigDelta;

        System.out.println("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
            bw = new BufferedWriter(logstream);
            logstream1 = new FileWriter("wynik.txt", true);
            bw1 = new BufferedWriter(logstream1);

            bw.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw.newLine();
            bw.close();
            bw1.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw1.newLine();
            bw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        w1 = 0;
        w2 = 0;
        w3 = 0;
        w4 = 0;

        for (int i = 0; i <= krok - 1; i++) {

            if (i % 2 == 0) {
                wart_sygnalu = x1 * w1 + x2 * w2 + x3 * w3 + x4 * w4;
                wart_wyjsc = 5 * (Math.pow(Math.E, wart_sygnalu) - Math.pow(Math.E, -wart_sygnalu)) / (Math.pow(Math.E, wart_sygnalu) + Math.pow(Math.E, -wart_sygnalu));
                blad_uczenia = z1 - wart_wyjsc;
                e_upper = Math.pow(Math.E, wart_sygnalu);
                e_lower = Math.pow(Math.E, -wart_sygnalu);
                funkcja_pochodna = Math.pow((e_upper + e_lower), 2);
                wynik = 20 / funkcja_pochodna;
                bigDelta = blad_uczenia * wynik;

                w1 = w1 + wspolczynnik_uczenia * bigDelta * x1;
                w2 = w2 + wspolczynnik_uczenia * bigDelta * x2;
                w3 = w3 + wspolczynnik_uczenia * bigDelta * x3;
                w4 = w4 + wspolczynnik_uczenia * bigDelta * x4;

            } else {
                wart_sygnalu = y1 * w1 + y2 * w2 + y3 * w3 + y4 * w4;
                wart_wyjsc = 5 * (Math.pow(Math.E, wart_sygnalu) - Math.pow(Math.E, -wart_sygnalu)) / (Math.pow(Math.E, wart_sygnalu) + Math.pow(Math.E, -wart_sygnalu));
                blad_uczenia = z2 - wart_wyjsc;
                e_upper = Math.pow(Math.E, wart_sygnalu);
                e_lower = Math.pow(Math.E, -wart_sygnalu);
                funkcja_pochodna = Math.pow((e_upper + e_lower), 2);
                wynik = 20 / funkcja_pochodna;
                bigDelta = blad_uczenia * wynik;

                w1 = w1 + wspolczynnik_uczenia * bigDelta * y1;
                w2 = w2 + wspolczynnik_uczenia * bigDelta * y2;
                w3 = w3 + wspolczynnik_uczenia * bigDelta * y3;
                w4 = w4 + wspolczynnik_uczenia * bigDelta * y4;

            }


            DecimalFormat df = new DecimalFormat("#.######");

            System.out.println("Wektor wag początkowych kroku W(" + (i + 1) + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");

            try {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                bw = new BufferedWriter(logstream);
                logstream1 = new FileWriter("wynik.txt", true);
                bw1 = new BufferedWriter(logstream1);

                bw.write("Wektor wag początkowych kroku W(" + (i + 1) + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw.newLine();
                bw.close();
                bw1.write("Wektor wag początkowych kroku W(" + (i + 1) + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw1.newLine();
                bw1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void makingCalculations6() {
        double x1 = Integer.parseInt(x_1.getText());
        double x2 = Integer.parseInt(x_2.getText());
        double x3 = Integer.parseInt(x_3.getText());
        double x4 = Integer.parseInt(x_4.getText());
        double w1 = Integer.parseInt(w_01.getText());
        double w2 = Integer.parseInt(w_02.getText());
        double w3 = Integer.parseInt(w_03.getText());
        double w4 = Integer.parseInt(w_04.getText());
        double y1 = Integer.parseInt(y_1.getText());
        double y2 = Integer.parseInt(y_2.getText());
        double y3 = Integer.parseInt(y_3.getText());
        double y4 = Integer.parseInt(y_4.getText());
        double z1 = Integer.parseInt(z_1.getText());
        double z2 = Integer.parseInt(z_11.getText());

        double wart_sygnalu;
        double wart_wyjsc;
        double blad_uczenia;
        double krok = Integer.parseInt(step.getText());
        double wspolczynnik_uczenia = Double.parseDouble(learning_index.getText());

        double e_lower;
        double funkcja_pochodna;
        double bigDelta;

        System.out.println("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
            bw = new BufferedWriter(logstream);
            logstream1 = new FileWriter("wynik.txt", true);
            bw1 = new BufferedWriter(logstream1);

            bw.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw.newLine();
            bw.close();
            bw1.write("Wektor wag początkowych kroku W(0) = [" + w1 + " ; " + w2 + " ; " + w3 + " ; " + w4 + "]");
            bw1.newLine();
            bw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        w1 = 0;
        w2 = 0;
        w3 = 0;
        w4 = 0;

        DecimalFormat df = new DecimalFormat("#.######");

        for (int i = 0; i <= krok - 1; i++) {

            if (i % 2 == 0) {
                wart_sygnalu = x1 * w1 + x2 * w2 + x3 * w3 + x4 * w4;
                wart_wyjsc = 1 / (1 + (Math.pow(Math.E, -wart_sygnalu)));
                blad_uczenia = z1 - wart_wyjsc;
                e_lower = Math.pow(Math.E, -wart_sygnalu);
                funkcja_pochodna = (Math.pow(Math.E, -wart_sygnalu) / Math.pow(1 + e_lower, 2));
                bigDelta = blad_uczenia * funkcja_pochodna;

                w1 = w1 + wspolczynnik_uczenia * bigDelta * x1;
                w2 = w2 + wspolczynnik_uczenia * bigDelta * x2;
                w3 = w3 + wspolczynnik_uczenia * bigDelta * x3;
                w4 = w4 + wspolczynnik_uczenia * bigDelta * x4;
            } else {
                wart_sygnalu = y1 * w1 + y2 * w2 + y3 * w3 + y4 * w4;
                wart_wyjsc = 1 / (1 + (Math.pow(Math.E, -wart_sygnalu)));
                blad_uczenia = z2 - wart_wyjsc;
                e_lower = Math.pow(Math.E, -wart_sygnalu);
                funkcja_pochodna = (Math.pow(Math.E, -wart_sygnalu) / Math.pow(1 + e_lower, 2));
                bigDelta = blad_uczenia * funkcja_pochodna;

                w1 = w1 + wspolczynnik_uczenia * bigDelta * y1;
                w2 = w2 + wspolczynnik_uczenia * bigDelta * y2;
                w3 = w3 + wspolczynnik_uczenia * bigDelta * y3;
                w4 = w4 + wspolczynnik_uczenia * bigDelta * y4;
            }


            System.out.println("Wektor wag początkowych kroku W(" + (i + 1) + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");

            try {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                bw = new BufferedWriter(logstream);
                logstream1 = new FileWriter("wynik.txt", true);
                bw1 = new BufferedWriter(logstream1);

                bw.write("Wektor wag początkowych kroku W(" + (i + 1) + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw.newLine();
                bw.close();
                bw1.write("Wektor wag początkowych kroku W(" + (i + 1) + ") = [" + df.format(w1) + " ; " + df.format(w2) + " ; " + df.format(w3) + " ; " + df.format(w4) + "]");
                bw1.newLine();
                bw1.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void clickLearn() {
        learn.setDisable(true);

        writeLog();

        if (vrb1.isSelected()) {
            if (firstrb.isSelected()) {
                try {
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                    bw = new BufferedWriter(logstream);

                    bw.write("Wybrano funkcję liniową!");
                    bw.newLine();
                    bw.write("Wprowadzono następującą parę wektorów: X = [" + x_1.getText() + "," + x_2.getText() + "," + x_3.getText() + "," + x_4.getText() + "] oraz Z=[" + z_1.getText() + "," + z_2.getText() + "," + z_3.getText() + "," + z_4.getText() + "]");
                    bw.newLine();
                    bw.write("Wprowadzono następujący wektor wag początkowych: W = [" + w_01.getText() + "," + w_02.getText() + "," + w_03.getText() + "," + w_04.getText() + "]");
                    bw.newLine();
                    bw.write(("--------------------------------------------------------------------------------------------------------"));
                    bw.newLine();
                    bw.close();

                    makingCalculations1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (secondrb.isSelected()) {
                try {
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                    bw = new BufferedWriter(logstream);

                    bw.write("Wybrano funkcję simgmoidalną bipolarną!");
                    bw.newLine();
                    bw.write("Wprowadzono następującą parę wektorów: X = [" + x_1.getText() + "," + x_2.getText() + "," + x_3.getText() + "," + x_4.getText() + "] oraz Z = [" + z_1.getText() + "," + z_2.getText() + "," + z_3.getText() + "," + z_4.getText() + "]");
                    bw.newLine();
                    bw.write("Wprowadzono następujący wektor wag początkowych: W = [" + w_01.getText() + "," + w_02.getText() + "," + w_03.getText() + "," + w_04.getText() + "]");
                    bw.newLine();
                    bw.write(("--------------------------------------------------------------------------------------------------------"));
                    bw.newLine();
                    bw.close();

                    makingCalculations2();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                    bw = new BufferedWriter(logstream);

                    bw.write("Wybrano funkcję simgmoidalną unipolarną!");
                    bw.newLine();
                    bw.write("Wprowadzono następującą parę wektorów: X = [" + x_1.getText() + "," + x_2.getText() + "," + x_3.getText() + "," + x_4.getText() + "] oraz Z = [" + z_1.getText() + "," + z_2.getText() + "," + z_3.getText() + "," + z_4.getText() + "]");
                    bw.newLine();
                    bw.write("Wprowadzono następujący wektor wag początkowych: W = [" + w_01.getText() + "," + w_02.getText() + "," + w_03.getText() + "," + w_04.getText() + "]");
                    bw.newLine();
                    bw.write(("--------------------------------------------------------------------------------------------------------"));
                    bw.newLine();
                    bw.close();

                    makingCalculations3();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (firstrb.isSelected()) {
                try {
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                    bw = new BufferedWriter(logstream);

                    bw.write("Wybrano funkcję liniową!");
                    bw.newLine();
                    bw.write("Wprowadzono następujące pary wektorów: X=[" + x_1.getText() + "," + x_2.getText() + "," + x_3.getText() + "," + x_4.getText() + "] oraz Z1=[" + z_1.getText() + "," + z_2.getText() + "," + z_3.getText() + "," + z_4.getText() + "] i Y=[" + y_1.getText() + "," + y_2.getText() + "," + y_3.getText() + "," + y_4.getText() + "] oraz Z2=[" + z_11.getText() + "," + z_12.getText() + "," + z_13.getText() + "," + z_14.getText() + "]");
                    bw.newLine();
                    bw.write("Wprowadzono następujący wektor wag początkowych: W=[" + w_01.getText() + "," + w_02.getText() + "," + w_03.getText() + "," + w_04.getText() + "]");
                    bw.newLine();
                    bw.write("--------------------------------------------------------------------------------------------------------");
                    bw.newLine();
                    bw.close();

                    makingCalculations4();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (secondrb.isSelected()) {
                try {
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                    bw = new BufferedWriter(logstream);

                    bw.write("Wybrano funkcję simogidalną bipolarną!");
                    bw.newLine();
                    bw.write("Wprowadzono następujące pary wektorów: X=[" + x_1.getText() + "," + x_2.getText() + "," + x_3.getText() + "," + x_4.getText() + "] oraz Z1=[" + z_1.getText() + "," + z_2.getText() + "," + z_3.getText() + "," + z_4.getText() + "] i Y=[" + y_1.getText() + "," + y_2.getText() + "," + y_3.getText() + "," + y_4.getText() + "] oraz Z2=[" + z_11.getText() + "," + z_12.getText() + "," + z_13.getText() + "," + z_14.getText() + "]");
                    bw.newLine();
                    bw.write("Wprowadzono następujący wektor wag początkowych: W=[" + w_01.getText() + "," + w_02.getText() + "," + w_03.getText() + "," + w_04.getText() + "]");
                    bw.newLine();
                    bw.close();

                    makingCalculations5();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    logstream = new FileWriter("log-" + dateFormat.format(date) + ".txt", true);
                    bw = new BufferedWriter(logstream);

                    bw.write("Wybrano funkcję simogidalną unipolarną!");
                    bw.newLine();
                    bw.write("Wprowadzono następujące pary wektorów: X=[" + x_1.getText() + "," + x_2.getText() + "," + x_3.getText() + "," + x_4.getText() + "] oraz Z1=[" + z_1.getText() + "," + z_2.getText() + "," + z_3.getText() + "," + z_4.getText() + "] i Y=[" + y_1.getText() + "," + y_2.getText() + "," + y_3.getText() + "," + y_4.getText() + "] oraz Z2=[" + z_11.getText() + "," + z_12.getText() + "," + z_13.getText() + "," + z_14.getText() + "]");
                    bw.newLine();
                    bw.write("Wprowadzono następujący wektor wag początkowych: W=[" + w_01.getText() + "," + w_02.getText() + "," + w_03.getText() + "," + w_04.getText() + "]");
                    bw.newLine();
                    bw.close();

                    makingCalculations6();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        learning_index.clear();
        step.clear();

        vrb1.setSelected(false);
        vrb2.setSelected(false);
        firstrb.setSelected(false);
        secondrb.setSelected(false);

        pane.getSelectionModel().select(first);

        save1.setDisable(false);
        save2.setDisable(false);
        save3.setDisable(false);
        save4.setDisable(false);
        learn.setDisable(false);

    }

    @FXML
    private void openLog() {

        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            ProcessBuilder ol = new ProcessBuilder("notepad.exe", "log-" + dateFormat.format(date) + ".txt");
            ol.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openResults() {

        try {

            ProcessBuilder ol1 = new ProcessBuilder("notepad.exe", "wynik.txt");
            ol1.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearResults(){

        clear_results.setOnAction((evt) -> {
            try {
                FileOutputStream writer = new FileOutputStream("wynik.txt");
                writer.write(("").getBytes());
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void clearLog(){
        clear_log.setOnAction((evt) -> {
            try {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                FileOutputStream writer = new FileOutputStream("log-" + dateFormat.format(date) + ".txt");
                writer.write(("").getBytes());
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}