package ru.mivar.syntax.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class DBConnection implements Resource {
    private static final Logger LOGGER = LogManager.getLogger(DBConnection.class.getName());
    private String driver;
    private String dbType;
    private String host;
    private int port;
    private String dbName;
    private String login;
    private String password;

    public DBConnection() {

    }

    public Connection getConnection() throws Exception {

        if (Objects.equals(driver, "")) {
            throw new Exception("sql Driver is not set");
        }

        Driver sqlDriver = (Driver) Class.forName(driver).newInstance();
        DriverManager.registerDriver(sqlDriver);

        StringBuilder url = new StringBuilder();

        url
                .append("jdbc:" + dbType + "://")    //db type
                .append(host + ":")       //host name
                .append(port + "/")        //port
                .append(dbName + "?")      //db name
                .append("user=" + login + "&")      //login
                .append("password=" + password);  //password


        LOGGER.info("Trying to get connection...");
        java.sql.Connection connection = DriverManager.getConnection(url.toString());
        LOGGER.info("Get connection.");
        return connection;
    }
}
