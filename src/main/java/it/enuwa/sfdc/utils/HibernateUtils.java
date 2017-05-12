package it.enuwa.sfdc.utils;

import it.enuwa.sfdc.beans.CredentialsEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by festini on 4/1/16.
 */
public class HibernateUtils {

    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry = null;

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            //return new Configuration().configure().buildSessionFactory();

            Configuration configuration = setupInMemoryHibernate();

            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory buildSessionFactory(Configuration configuration) {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            //return new Configuration().configure().buildSessionFactory();
            //configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            buildSessionFactory();
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory(Configuration configuration) {
        if(sessionFactory == null){
            buildSessionFactory(configuration);
        }
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
        sessionFactory = null;
    }

    public static Configuration setupInMemoryHibernate(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(CredentialsEntity.class);

        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:~/waypass_orders;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS waypass_orders");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");

        return configuration;
    }





}
