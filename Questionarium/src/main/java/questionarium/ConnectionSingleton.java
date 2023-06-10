package questionarium;

import questionarium.exception.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {

    private static Connection connection;
    private static final String user = "postgres";
    private static final String password = "superUser";

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", user, password);
            }
        } catch (SQLException e) {
            throw new ConnectionException("cant creat connection to db");
        }
        return connection;
    }
}
