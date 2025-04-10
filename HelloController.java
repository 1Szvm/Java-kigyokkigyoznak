package com.example.kigyogui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class HelloController {

    @FXML private ListView<String> lsEszkoz;
    @FXML private ListView<String> lsSort;
    @FXML private TextField sortInput;

    private FileChooser fc = new FileChooser();

    public void initialize(){
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Fájlok","*.csv"));
    }

    @FXML private void onMegnyitasClick(){
        File fbe=fc.showOpenDialog(lsEszkoz.getScene().getWindow());
        if(fbe!=null){
            kigyok.clear();
            lsEszkoz.getItems().clear();
            betolt(fbe);
            for (Kigyo kigyo: kigyok){
                lsEszkoz.getItems().add(String.format("%s (%dcm), %s",kigyo.nev,kigyo.hosz,kigyo.elohely));
            }
        }
    }

    @FXML private void onSzuresClick(){
        lsSort.getItems().clear();
        System.out.println(sortInput.getText());
        for (Kigyo kigyo: kigyok){
            if(kigyo.nev.toLowerCase().contains(sortInput.getText().toLowerCase())) {
                lsSort.getItems().add(kigyo.nev);
            }
        }
    }


    private void betolt(File fajl){
        Scanner be = null;
        try{
            be= new Scanner(fajl,"utf-8");
            be.nextLine();
            while (be.hasNextLine()){
                kigyok.add(new Kigyo(be.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(be != null) be.close();
        }
    }

    public class Kigyo{
        String nev;
        Integer hosz;
        String elohely;
        String merges;

        public Kigyo(String sor){
            String[] s =sor.split(";");
            nev=s[0];
            hosz=Integer.parseInt(s[1]);
            elohely=s[2];
            merges=s[3];
        }
    }

    public ArrayList<Kigyo> kigyok = new ArrayList<>();

    @FXML private void onKilépesClick(){
        Platform.exit();
    }

    @FXML private void onNevjegyzekClick(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Kígyok v1.0.0\n(C) Kandó");
        info.showAndWait();
    }
}