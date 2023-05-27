package questionarium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {

    private static Connection connection;
    private static final String user = "postgres";
    private static final String password = "superUser";

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", user, password);
        }
        return connection;
    }
}
