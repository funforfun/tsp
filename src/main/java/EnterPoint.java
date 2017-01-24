
import dbExecutor.TExecutor;
import dbService.DBService;
import org.postgresql.Driver;
import textParser.TextParser;

import java.sql.*;

public class EnterPoint {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, InterruptedException {
        String testText = "Папа пошел в лес. Он там увидел грибы.";
        TextParser textParser = new TextParser();
        textParser.run(testText);
        Thread.sleep(10000);
        Driver driver = (Driver) Class.forName("org.postgresql.Driver").newInstance();
        DriverManager.registerDriver(driver);

        StringBuilder url = new StringBuilder();

        url
                .append("jdbc:postgresql://")    //db type
                .append("dev-db.mivar.pro:")       //host name
                .append("5432/")        //port
                .append("dev_alpha?")      //db name
                .append("user=postgres&")      //login
                .append("password=@Mivar123User@");  //password

        Connection connection = DriverManager.getConnection(url.toString());



        // 10 terms
//        testQuery1(connection);
        // word
        testQuery2(connection);

//        Statement stmt = connection.createStatement();
//        String select = "SELECT * FROM semantic.terms LIMIT 10;";
//        ResultSet result = stmt.executeQuery(select);
//        while (result.next()) {
//            System.out.println(
//                    "******************************\n"
//                            + result.getString(1)
//                            + "\n" + result.getString(2)
//                            + "\n" + result.getString(3)
//                            + "\n" + result.getString(4)
//                            + "\n" + result.getString(5)
//                            + "\n" + result.getString(6)
//                            + "\n" + result.getString(7)
//                            + "\n" + result.getString(8)
//                            + "\n" + result.getString(9)
//                            + "\n" + result.getString(10)
//                            + "\n" + result.getString(11)
//            );
//        }
//        stmt.close();

//
//        DBService dbService = new DBService();
//        dbService.printConnectInfo();
    }

    private static void testQuery2(Connection connection) throws SQLException {

        String resultString = TExecutor.execQuery(
                connection,
                "" +
                        "SELECT * FROM words.mwords " +
                        "WHERE word = 'пойти'" +
                        "LIMIT 10;",
                (result) -> {
                    StringBuilder str = new StringBuilder();
                    while (result.next()) {
                        str.append("\n");
                        str.append(result.getString(1));
                        str.append("\n");
                        str.append(result.getString(2));
                        str.append("\n*********************************");
                    }
                    return  str.toString();
                });
        System.out.println(resultString);
    }

    private static void testQuery1(Connection connection) throws SQLException {

        String resultString = TExecutor.execQuery(
                connection,
                "SELECT * FROM semantic.terms LIMIT 10;",
                (result) -> {
                    StringBuilder str = new StringBuilder();
                    while (result.next()) {
                        str.append("\n");
                        str.append(result.getString(1));
                        str.append("\n");
                        str.append(result.getString(2));
                        str.append("\n");
                        str.append(result.getString(3));
                        str.append("\n");
                        str.append(result.getString(4));
                        str.append("\n");
                        str.append(result.getString(5));
                        str.append("\n");
                        str.append(result.getString(6));
                        str.append("\n");
                        str.append(result.getString(7));
                        str.append("\n");
                        str.append(result.getString(8));
                        str.append("\n");
                        str.append(result.getString(9));
                        str.append("\n");
                        str.append(result.getString(10));
                        str.append("\n*********************************");
                    }
                    return  str.toString();
                });
        System.out.println(resultString);
    }

}
