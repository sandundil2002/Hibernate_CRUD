package lk.ijse.hibernate_crud.util;

import lk.ijse.hibernate_crud.entity.Customer;
import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;

/*public class SessionFactoryConfig {

    private static SessionFactoryConfig factoryConfig;

    private final SessionFactory sessionFactory;

    private SessionFactoryConfig(){
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(serviceRegistry).addAnnotatedClass(Customer.class).getMetadataBuilder().build();
        sessionFactory = metadata.buildSessionFactory();
    }

    public static SessionFactoryConfig getInstance(){
        return (null == factoryConfig) ? factoryConfig = new SessionFactoryConfig() : factoryConfig;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}*/