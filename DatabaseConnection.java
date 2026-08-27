import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConnection {

    static final String URL =
            "jdbc:mysql://localhost:3306/student_management";

    static final String USER = "root";

    static String getPassword() {

        Properties properties = new Properties();

        try (FileInputStream file =
                     new FileInputStream("config.properties")) {

            properties.load(file);

            return properties.getProperty("db.password");

        } catch (IOException e) {

            System.out.println(
                    "Could not read database configuration."
            );

            return "";
        }
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                getPassword()
        );
    }

    public static void main(String[] args) {

        try {

            Connection con = getConnection();

            System.out.println(
                    "MySQL connected successfully!"
            );

            con.close();

        } catch (SQLException e) {

            System.out.println(
                    "MySQL connection failed!"
            );

            e.printStackTrace();
        }
    }
}