package dbExecutor;

import java.sql.*;
import java.util.Map;

public class TExecutor {

    public static void execUpdate(Connection connection, String[] updates) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
//        try {
//            connection.setAutoCommit(false);
//            for (String update : updates) {
//                Statement statement = connection.createStatement();
//                statement.execute(update);
//                statement.close();
//            }
//            connection.commit();
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException ignore) {
//            }
//        } finally {
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void execUpdate(Connection connection, Map<Integer, String> idToName) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
//        try {
//            String update = "INSERT INTO users(id, name) values(?,?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(update);
//
//            for (Integer id : idToName.keySet()) {
//                preparedStatement.setInt(1, id);
//                preparedStatement.setString(2, idToName.get(id));
//                preparedStatement.executeUpdate();
//            }
//            preparedStatement.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public static <T> T execQuery(Connection connection, String query, TResultHandler<T> handler) throws SQLException {
//        T value;
        try (Statement statement = connection.createStatement(); ResultSet result = statement.executeQuery(query)) {
//            value = handler.handle(result);
            return handler.handle(result);
        }
//        return value;
    }


}
