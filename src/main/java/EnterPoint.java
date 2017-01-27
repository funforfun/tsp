
import dbExecutor.TExecutor;
import dbService.DBService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.Driver;
import syntaxGraph.SyntaxGraphBuilder;
import textParser.TextParser;

import java.sql.*;

public class EnterPoint {
    private static final Logger LOGGER = LogManager.getLogger(EnterPoint.class.getName());


    public static void main(String[] args) throws Exception {

//        LOGGER.debug("Debug Message Logged !!!");
//        LOGGER.info("Info Message Logged !!!");
//        LOGGER.error("Error Message Logged !!!", new NullPointerException("NullError"));

        Connection connection = getConnection();
        String testText = "Папа и папа пошел в лес. Он там увидел грибы.";
        SyntaxGraphBuilder builder = new SyntaxGraphBuilder(connection);

        builder.run(testText);
//        TextParser textParser = new TextParser();
//        textParser.run(testText);
//        Thread.sleep(10000);


        // 10 terms
//        testQuery1(connection);
        // word
        testQuery2(connection);

        LOGGER.info("testQuery2");
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

    private static Connection getConnection() throws Exception {

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


        LOGGER.info("Trying to get connection...");
        Connection connection = DriverManager.getConnection(url.toString());
        LOGGER.info("Get connection.");
        return connection;
    }

    private static void testQuery2(Connection connection) throws SQLException {

        LOGGER.info("testQuery2");
//        String query = "SELECT * " +
//                        "FROM words.mwords " +
//                        "WHERE word = 'пошел'" +
//                        "LIMIT 10;";
        String query =
                "with swords as (select unnest(array['папа', 'пошел', 'в', 'лес']) word )\n" +
                        "    select         s.word, mw.id as mword_id, f.id as form_id, mw2.word, f.values_agg, (select array_agg(value) from values where id in (select unnest(f.values_agg) ) )  as values\n" +
                        "        from     swords s\n" +
                        "            left join mwords     mw  on s.word = mw.word\n" +
                        "            left join forms     f   on mw.id = f.mword_id\n" +
                        "            left join mwords     mw2 on f.initial_form_id = mw2.id";
        String resultString = TExecutor.execQuery(
                connection,
                query,
                (result) -> {
                    StringBuilder str = new StringBuilder();
                    while (result.next()) {
                        str.append(result.getString(1));
                        str.append("\t\t|\t\t");
                        str.append(result.getString(2));
                        str.append("\t\t|\t\t");
                        str.append(result.getString(3));
                        str.append("\t\t|\t\t");
                        str.append(result.getString(4));
                        str.append("\t\t|\t\t");
                        str.append(result.getString(5));
                        str.append("\n");
                    }
                    return str.toString();
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
                    return str.toString();
                });
        System.out.println(resultString);
    }

}
