package lk.ijse.hibernate_crud.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hibernate_crud.entity.Customer;
import lk.ijse.hibernate_crud.model.CustomerModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class CustomerFormController {

    @FXML
    private TableView tblCustomer;

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

    private int id;

    private String firstName;

    private String lastName;

    private String address;

    private String mobile;

    private final CustomerModel customerModel = new CustomerModel();

    public void initialize(){
        updateRealTime(lblTime);
        lblDate.setText(LocalDate.now().toString());
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        loadAllCustomers();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("addedDateTime"));
    }

    @FXML
    private void btnSaveOnAction() {
        if (validateCustomer()) {
            getData();
            Customer saveCustomer = new Customer(id,firstName,lastName,address,mobile);
            boolean isSaved = customerModel.saveCustomer(saveCustomer);

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION , "Customer Saved Successfully").show();
                btnClearOnAction();
            } else {
                new Alert(Alert.AlertType.ERROR , "Something Went Wrong").show();
            }
        }
    }

    @FXML
    private void btnUpdateOnAction() {
        if (validateCustomer()) {
            getData();
            Customer updateCustomer = new Customer(id, firstName, lastName, address, mobile);
            boolean isUpdated = customerModel.updateCustomer(updateCustomer);

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Update Successfully").show();
                btnClearOnAction();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Went Wrong").show();
                btnClearOnAction();
            }
        }
    }

    @FXML
    private void btnSearchOnAction() {
        if (Pattern.compile("\\d+").matcher(txtId.getText()).matches()) {
            getData();
            Customer getCustomer = customerModel.searchCustomer(id);

            if (getCustomer == null) {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
                btnClearOnAction();
            } else {
                txtId.setText(String.valueOf(getCustomer.getId()));
                txtFirstname.setText(getCustomer.getName().getFistName());
                txtLastname.setText(getCustomer.getName().getLastName());
                txtAddress.setText(getCustomer.getAddress());
                txtMobile.setText(getCustomer.getMobile());
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Please enter a valid id").show();
            txtId.setStyle("-fx-border-color:#ff0000;");
            txtId.requestFocus();
        }
    }

    @FXML
    private void btnDeleteOnAction() {
        if (Pattern.compile("\\d+").matcher(txtId.getText()).matches()) {
            getData();
            boolean isDeleted = customerModel.deleteCustomer(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Delete Successfully").show();
                btnClearOnAction();
            } else {
                btnClearOnAction();
            }
        }  else {
            new Alert(Alert.AlertType.ERROR,"Please enter a valid id").show();
            txtId.setStyle("-fx-border-color:#ff0000;");
            txtId.requestFocus();
        }
    }

    private void loadAllCustomers(){
        tblCustomer.setItems(customerModel.loadAllCustomers());
    }

    @FXML
    private void btnClearOnAction() {
        txtId.clear();
        txtFirstname.clear();
        txtLastname.clear();
        txtAddress.clear();
        txtMobile.clear();
        setCellValueFactory();
    }

    @FXML
    private void btnBackOnAction() {
        System.exit(0);
    }

    private void getData(){
        id = Integer.parseInt(txtId.getText());
        firstName = txtFirstname.getText();
        lastName = txtLastname.getText();
        address = txtAddress.getText();
        mobile = txtMobile.getText();
    }

    private boolean validateCustomer() {
        String id = txtId.getText();
        boolean isIdValidated = Pattern.compile("\\d+").matcher(id).matches();
        if (!isIdValidated){
            new Alert(Alert.AlertType.ERROR,"Please enter a valid id").show();
            txtId.setStyle("-fx-border-color:#ff0000;");
            txtId.requestFocus();
            return false;
        }

        String firstName = txtFirstname.getText();
        boolean isFirstNameValidated = Pattern.compile("^[A-Za-z]{1,20}$").matcher(firstName).matches();

        if (!isFirstNameValidated) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid name").show();
            txtFirstname.setStyle("-fx-border-color:#ff0000;");
            txtFirstname.requestFocus();
            return false;
        }

        String lastName = txtLastname.getText();
        boolean isLastNameValidated = Pattern.compile("^[A-Za-z]{1,20}$").matcher(lastName).matches();

        if (!isLastNameValidated) {
            new Alert(Alert.AlertType.WARNING,"Please enter a valid name").show();
            txtLastname.setStyle("-fx-border-color:#ff0000;");
            txtLastname.requestFocus();
            return false;
        }

        String address = txtAddress.getText();
        boolean isAddressValidated = Pattern.compile("^[A-z]{1,20}$").matcher(address).matches();
        if (!isAddressValidated) {
            txtAddress.requestFocus();
            txtAddress.setStyle("-fx-border-color:#ff0000;");
            return false;
        }

        String mobile = txtMobile.getText();
        boolean isMobileValidated = Pattern.compile("^[0-9]{10}$").matcher(mobile).matches();
        if (!isMobileValidated) {
            txtMobile.requestFocus();
            txtMobile.setStyle("-fx-border-color:#ff0000;");
            return false;
        }
        return true;
    }

    private void updateRealTime(Label label) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            String currentTime = LocalDateTime.now().format(timeFormatter);
            Platform.runLater(() -> label.setText(currentTime));
        }, 0, 1, TimeUnit.SECONDS);
    }
}