import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static final String URL =
        "jdbc:mysql://localhost:3306/student_management";

    static final String USER = "root";

    static final String PASSWORD = "YOUR_MYSQL_PASSWORD";

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
            URL,
            USER,
            PASSWORD
        );
    }

    public static void main(String[] args) {

        try {

            Connection con = getConnection();

            System.out.println(
                "MySQL connected successfully!"
            );

            con.close();

        }
        catch (SQLException e) {

            System.out.println(
                "Connection failed!"
            );

            e.printStackTrace();
        }
    }
}
