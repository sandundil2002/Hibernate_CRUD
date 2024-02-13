module lk.ijse.hibernate_crud {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;


    opens lk.ijse.hibernate_crud to javafx.fxml;
    exports lk.ijse.hibernate_crud;
    exports lk.ijse.hibernate_crud.controller;
    opens lk.ijse.hibernate_crud.controller to javafx.fxml;
}