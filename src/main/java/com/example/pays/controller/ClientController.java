package com.example.pays.controller;

import com.example.pays.service.CountryService;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    private Stage stage;
    private double yOffset;
    private double xOffset;

    @FXML
    public MFXFontIcon btnClose;

    @FXML
    public HBox windowHeader;

    public ClientController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnClose.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
        windowHeader.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        windowHeader.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        try {
            this.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getData() throws Exception {
        String url_service = "rmi://localhost:1099/countryService";
        CountryService countryService = (CountryService) Naming.lookup(url_service);

        System.out.println(countryService.countries());

    }

}
