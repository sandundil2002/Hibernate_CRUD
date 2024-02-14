package lk.ijse.hibernate_crud.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomerFormController {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDateTime;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMobile;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtFirstname;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtMobile;

    public void initialize(){
        lblDate.setText(LocalDate.now().toString());
        updateRealTime(lblTime);
    }

    @FXML
    private void btnClearOnAction() {

    }

    @FXML
    private void btnDeleteOnAction() {

    }

    @FXML
    private void btnSaveOnAction( ) {

    }

    @FXML
    private void btnUpdateOnAction() {
    }

    @FXML
    private void btnSearchOnAction() {
    }

    private void updateRealTime(Label label) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            String currentTime = LocalDateTime.now().format(timeFormatter);
            Platform.runLater(() -> label.setText(currentTime));
        }, 0, 1, TimeUnit.SECONDS);
    }

    @FXML
    private void btnBackOnAction() {
        System.exit(0);
    }
}