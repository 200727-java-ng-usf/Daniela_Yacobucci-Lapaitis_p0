package com.revature.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.revature.AppDriver.app;


//TODO add comments to every CLASS as well
//TODO set appropriate dummy data in database


public class ConnectionFactory {

    private static ConnectionFactory connFactory = new ConnectionFactory();

    // A Properties object is created to obtain properties on properties file
    private Properties props = new Properties();

    /**
     * Singleton Class constructor.
     * Loeads the properies from the properties file
     */
    private ConnectionFactory() {
        try {
            // reads relative path of properties file to obtain credentials through FileReader
            props.load(new FileReader("./src/main/resources/application.properties"));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            System.err.println("[ERROR] - " + ioe.getMessage() + ". Shutting down application");
            app.setAppRunning(false);
        }

    }

    /**
     * Static getInstance method needed to follow singleton pattern
     * @return ConnectionFactory
     */
    public static ConnectionFactory getInstance() {
        return connFactory;
    }

    public Connection getConnection() {

        Connection conn = null;

        try {

            // Force the JVM to load the PostGreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(
                    // getting properties from properties file to avoid pushing credentials to github
                    props.getProperty("url"),
                    props.getProperty("username"),
                    props.getProperty("password")

            );

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if (conn == null) {
            throw new RuntimeException("Failed to establish connection.");
        }

        return conn;

    }

    /**
     * Overriden clone method needed to follow Singleton pattern
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}