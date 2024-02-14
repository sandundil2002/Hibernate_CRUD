package lk.ijse.hibernate_crud.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.hibernate_crud.entity.Customer;
import lk.ijse.hibernate_crud.util.CustomerIdGenerator;
import lk.ijse.hibernate_crud.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

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
        updateRealTime(lblTime);
        lblDate.setText(LocalDate.now().toString());
        setId();
    }

    @FXML
    private void btnSaveOnAction() {
        String id = txtId.getText();
        String name = txtFirstname.getText()+" "+txtLastname.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();

        if (validateCustomer()) {
            Session saveSession = SessionFactoryConfig.getInstance().getSession();
            Transaction transaction = saveSession.beginTransaction();
            Customer saveCustomer = new Customer(id, name, address, mobile);
            saveSession.save(saveCustomer);
            transaction.commit();
            System.out.println("Saved Customer : " + saveCustomer);
            saveSession.close();
            btnClearOnAction();
            setId();
        }
    }

    @FXML
    private void btnUpdateOnAction() {
        String id = txtId.getText();
        String name = txtFirstname.getText()+" "+txtLastname.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();

        Session updateSession = SessionFactoryConfig.getInstance().getSession();
        Transaction updateTransaction = updateSession.beginTransaction();
        Customer updateCustomer = updateSession.get(Customer.class, id);
        updateCustomer.setId(id);
        updateCustomer.setName(name);
        updateCustomer.setAddress(address);
        updateCustomer.setMobile(mobile);
        updateSession.update(updateCustomer);
        updateTransaction.commit();
        System.out.println("Updated Customer : " + updateCustomer);
        updateSession.close();
    }

    @FXML
    private void btnClearOnAction() {
        txtId.clear();
        txtFirstname.clear();
        txtLastname.clear();
        txtAddress.clear();
        txtMobile.clear();
    }

    @FXML
    private void btnDeleteOnAction() {

    }

    @FXML
    private void btnSearchOnAction() {
    }

    @FXML
    private void btnBackOnAction() {
        System.exit(0);
    }

    private boolean validateCustomer() {
        String id = txtId.getText();
        boolean isIdValidated = Pattern.compile("^C\\d{3}$").matcher(id).matches();
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

    private void setId(){
        txtId.setText(CustomerIdGenerator.generateCustomerId());
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