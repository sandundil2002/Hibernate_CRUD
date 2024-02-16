package lk.ijse.hibernate_crud.model;

import jakarta.persistence.criteria.CriteriaQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.hibernate_crud.entity.Customer;
import lk.ijse.hibernate_crud.util.SessionFactoryConfig;
import org.hibernate.*;

import java.util.List;

public class CustomerModel {
    public boolean saveCustomer(Customer saveCustomer){
        Session saveSession = SessionFactoryConfig.getInstance().getSession();
        Transaction saveTransaction = saveSession.beginTransaction();
        saveSession.save(saveCustomer);
        saveTransaction.commit();
        saveSession.close();
        return true;
    }

    public boolean updateCustomer(Customer updateCustomer){
        if (updateCustomer!= null) {
            Session updateSession = SessionFactoryConfig.getInstance().getSession();
            Transaction updateTransaction = updateSession.beginTransaction();
            Customer existingCustomer = updateSession.get(Customer.class, updateCustomer.getId());
            existingCustomer.setId(updateCustomer.getId());
            existingCustomer.setName(updateCustomer.getName());
            existingCustomer.setAddress(updateCustomer.getAddress());
            existingCustomer.setMobile(updateCustomer.getMobile());
            updateSession.update(existingCustomer);
            updateTransaction.commit();
            updateSession.close();
            return true;
        } else {
            return false;
        }
    }

    public Customer searchCustomer(int id){
        Session searchSession = SessionFactoryConfig.getInstance().getSession();
        Transaction searchTransaction = searchSession.beginTransaction();
        Customer getCustomer = searchSession.get(Customer.class,id);
        searchTransaction.commit();
        searchSession.close();
        return getCustomer;
    }

    public boolean deleteCustomer(int id){
        Session deleteSession = SessionFactoryConfig.getInstance().getSession();
        Transaction deleteTransaction = deleteSession.beginTransaction();
        Customer deleteCustomer = deleteSession.get(Customer.class, id);
        if (deleteCustomer == null){
            new Alert(Alert.AlertType.ERROR , "Customer Not Found").show();
            return false;
        } else {
            deleteSession.delete(deleteCustomer);
            deleteTransaction.commit();
            deleteSession.close();
            return true;
        }
    }

    public ObservableList<Customer> loadAllCustomers(){
        ObservableList<Customer> allCustomersList = FXCollections.observableArrayList();
        Session loadSession = SessionFactoryConfig.getInstance().getSession();
        CriteriaQuery<Customer> criteriaQuery = loadSession.getCriteriaBuilder().createQuery(Customer.class);
        criteriaQuery.from(Customer.class);
        List<Customer> customersList = loadSession.createQuery(criteriaQuery).getResultList();
        allCustomersList.setAll(customersList);
        loadSession.close();
        return allCustomersList;
    }
}
