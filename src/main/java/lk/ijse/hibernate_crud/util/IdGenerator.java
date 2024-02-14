package lk.ijse.hibernate_crud.util;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IdGenerator {
    private static final String PREFIX = "C";
    private static final int INITIAL_ID = 1;

    public static String generateCustomerId(Connection connection) {
        String customerId = null;
        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT Customer_id FROM customer ORDER BY Customer_id DESC LIMIT 1");

            if (rs.next()) {
                String latestCustomerId = rs.getString("Customer_id");
                int sequence = Integer.parseInt(latestCustomerId.substring(1)) + 1;
                customerId = PREFIX + String.format("%03d", sequence);
            } else {
                customerId = PREFIX + String.format("%03d", INITIAL_ID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return customerId;
    }
}

