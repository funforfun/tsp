package dbService;

//import dbService.dao.UsersDAO;
//import dbService.dataSets.UsersDataSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import org.postgresql.Driver;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import resource.DBConnection;
import resource.ResourcesMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private static final Logger LOGGER = LogManager.getLogger(DBService.class.getName());
    private static final String hibernate_show_sql = "false";
    private static final String hibernate_hbm2ddl_auto = "validate";

    private final SessionFactory sessionFactory;

    public DBService() {
//        Configuration configuration = getH2Configuration();
        Configuration configuration = getPostgreSqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    @SuppressWarnings("UnusedDeclaration")
    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "test");
        configuration.setProperty("hibernate.connection.password", "test");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    @SuppressWarnings("UnusedDeclaration")
    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "test");
        configuration.setProperty("hibernate.connection.password", "test");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private Configuration getPostgreSqlConfiguration() {
        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://dev-db.mivar.pro:5432");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "@Mivar123User@");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }


//    public UsersDataSet getUser(long id) throws DBException {
//        try {
//            Session session = sessionFactory.openSession();
//            UsersDAO dao = new UsersDAO(session);
//            UsersDataSet dataSet = dao.get(id);
//            session.close();
//            return dataSet;
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }

//    public UsersDataSet getUser(String name, String pass) throws DBException {
//        try {
//            Session session = sessionFactory.openSession();
//            UsersDAO dao = new UsersDAO(session);
//            UsersDataSet dataSet = dao.get(name, pass);
//            session.close();
//            return dataSet;
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }

//    public long addUser(String name, String pass) throws DBException {
//        try {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            UsersDAO dao = new UsersDAO(session);
//            long id = dao.insertUser(name, pass);
//            transaction.commit();
//            session.close();
//            return id;
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }


    public static Connection getConnection() throws Exception {
        DBConnection dbConnection = (DBConnection) ResourcesMap.get(DBConnection.class);
        return dbConnection.getConnection();
//        Driver driver = (Driver) Class.forName("org.postgresql.Driver").newInstance();
//        DriverManager.registerDriver(driver);
//
//        StringBuilder url = new StringBuilder();
//
//        url
//                .append("jdbc:postgresql://")    //db type
//                .append("dev-db.mivar.pro:")       //host name
//                .append("5432/")        //port
//                .append("dev_alpha?")      //db name
//                .append("user=postgres&")      //login
//                .append("password=@Mivar123User@");  //password
//
//
//        LOGGER.info("Trying to get connection...");
//        Connection connection = DriverManager.getConnection(url.toString());
//        LOGGER.info("Get connection.");
//        return connection;
    }

    public void printConnectInfo() {
        try {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
