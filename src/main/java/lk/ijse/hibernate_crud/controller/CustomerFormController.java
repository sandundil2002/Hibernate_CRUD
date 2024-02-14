package lk.ijse.hibernate_crud.controller;

import jakarta.persistence.criteria.CriteriaQuery;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hibernate_crud.entity.Customer;
import lk.ijse.hibernate_crud.util.IdGenerator;
import lk.ijse.hibernate_crud.util.DbConnection;
import lk.ijse.hibernate_crud.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    private String id;

    private String name;

    private String address;

    private String mobile;

    public void initialize(){
        updateRealTime(lblTime);
        lblDate.setText(LocalDate.now().toString());
        setId();
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
            Session saveSession = SessionFactoryConfig.getInstance().getSession();
            Transaction transaction = saveSession.beginTransaction();
            Customer saveCustomer = new Customer();
            saveCustomer.setId(id);
            saveCustomer.setName(name);
            saveCustomer.setAddress(address);
            saveCustomer.setMobile(mobile);
            saveSession.save(saveCustomer);
            transaction.commit();
            System.out.println("Saved Customer : " + saveCustomer);
            saveSession.close();
            btnClearOnAction();
        }
    }

    @FXML
    private void btnUpdateOnAction() {
        if (validateCustomer()) {
            getData();
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
            btnClearOnAction();
        }
    }

    @FXML
    private void btnSearchOnAction() {
        getData();
        Session getSession = SessionFactoryConfig.getInstance().getSession();
        Customer getCustomer = getSession.get(Customer.class,id);
        txtId.setText(getCustomer.getId());
        txtFirstname.setText(getCustomer.getName());
        txtLastname.setText(getCustomer.getName());
        txtAddress.setText(getCustomer.getAddress());
        txtMobile.setText(getCustomer.getMobile());
        System.out.println("Get Customer : " + getCustomer);
        getSession.close();
    }

    @FXML
    private void btnDeleteOnAction() {
        getData();
        Session deleteSession = SessionFactoryConfig.getInstance().getSession();
        Transaction deleteTransaction = deleteSession.beginTransaction();
        Customer deleteCustomer = deleteSession.get(Customer.class, id);
        deleteSession.delete(deleteCustomer);
        deleteTransaction.commit();
        System.out.println("Deleted Customer : " + deleteCustomer);
        deleteSession.close();
        btnClearOnAction();
    }

    private void loadAllCustomers(){
        ObservableList<Customer> allCustomersList = FXCollections.observableArrayList();
        Session session = SessionFactoryConfig.getInstance().getSession();

        try {
            CriteriaQuery<Customer> criteriaQuery = session.getCriteriaBuilder().createQuery(Customer.class);
            criteriaQuery.from(Customer.class);
            List<Customer> customersList = session.createQuery(criteriaQuery).getResultList();
            allCustomersList.setAll(customersList);
            tblCustomer.setItems(allCustomersList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            session.close();
        }
    }

    @FXML
    private void btnClearOnAction() {
        txtId.clear();
        txtFirstname.clear();
        txtLastname.clear();
        txtAddress.clear();
        txtMobile.clear();
        setId();
        setCellValueFactory();
    }

    @FXML
    private void btnBackOnAction() {
        System.exit(0);
    }

    private void getData(){
        id = txtId.getText();
        name = txtFirstname.getText()+" "+txtLastname.getText();
        address = txtAddress.getText();
        mobile = txtMobile.getText();
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

    private void updateRealTime(Label label) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            String currentTime = LocalDateTime.now().format(timeFormatter);
            Platform.runLater(() -> label.setText(currentTime));
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void setId(){
        try {
            txtId.setText(IdGenerator.generateCustomerId(DbConnection.getInstance().getConnection()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}