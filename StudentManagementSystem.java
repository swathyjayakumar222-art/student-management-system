import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManagementSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Student> students =
            new ArrayList<>();


    // =========================================
    // LOAD STUDENTS FROM MYSQL
    // =========================================

    static void loadStudentsFromDatabase() {

        students.clear();

        String sql = "SELECT * FROM students";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        con.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                int id = rs.getInt("id");

                String name =
                        rs.getString("name");

                String course =
                        rs.getString("course");

                String email =
                        rs.getString("email");

                String phone =
                        rs.getString("phone");

                int semester =
                        rs.getInt("semester");

                double cgpa =
                        rs.getDouble("cgpa");


                Student s =
                        new Student(
                                id,
                                name,
                                course,
                                email,
                                phone,
                                semester,
                                cgpa
                        );

                students.add(s);
            }

            System.out.println(
                    "Students loaded from MySQL."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error loading students!"
            );

            e.printStackTrace();
        }
    }


    // =========================================
    // VALIDATE NAME
    // =========================================

    static boolean isValidName(String name) {

        if (name.isEmpty())
            return false;

        for (int i = 0; i < name.length(); i++) {

            char ch = name.charAt(i);

            if (!Character.isLetter(ch) && ch != ' ') {

                return false;
            }
        }

        return true;
    }


    // =========================================
    // VALIDATE EMAIL
    // =========================================

    static boolean isValidEmail(String email) {

        return email.contains("@")
                && email.contains(".");
    }


    // =========================================
    // VALIDATE PHONE
    // =========================================

    static boolean isValidPhone(String phone) {

        if (phone.length() != 10)
            return false;

        for (int i = 0; i < phone.length(); i++) {

            if (!Character.isDigit(phone.charAt(i)))
                return false;
        }

        return true;
    }


    // =========================================
    // VALIDATE CGPA
    // =========================================

    static boolean isValidCGPA(double cgpa) {

        return cgpa >= 0 && cgpa <= 10;
    }


    // =========================================
    // VALIDATE SEMESTER
    // =========================================

    static boolean isValidSemester(int semester) {

        return semester >= 1 && semester <= 8;
    }


    // =========================================
    // CHECK DUPLICATE ID
    // =========================================

    static boolean isDuplicateId(int id) {

        for (Student s : students) {

            if (s.id == id)
                return true;
        }

        return false;
    }


    // =========================================
    // ADD STUDENT
    // =========================================

    static void addStudent() {

        int id;

        while (true) {

            System.out.print(
                    "Enter Student ID: "
            );

            if (!sc.hasNextInt()) {

                System.out.println(
                        "ID must be a number!"
                );

                sc.nextLine();
                continue;
            }

            id = sc.nextInt();
            sc.nextLine();

            if (id <= 0) {

                System.out.println(
                        "ID must be positive!"
                );

                continue;
            }

            if (isDuplicateId(id)) {

                System.out.println(
                        "Student ID already exists!"
                );

                continue;
            }

            break;
        }


        // NAME

        String name;

        while (true) {

            System.out.print(
                    "Enter Name: "
            );

            name =
                    sc.nextLine().trim();

            if (!isValidName(name)) {

                System.out.println(
                        "Invalid name!"
                );

                continue;
            }

            break;
        }


        // COURSE

        String course;

        while (true) {

            System.out.print(
                    "Enter Course: "
            );

            course =
                    sc.nextLine().trim();

            if (course.isEmpty()) {

                System.out.println(
                        "Course cannot be empty!"
                );

                continue;
            }

            break;
        }


        // EMAIL

        String email;

        while (true) {

            System.out.print(
                    "Enter Email: "
            );

            email =
                    sc.nextLine().trim();

            if (!isValidEmail(email)) {

                System.out.println(
                        "Invalid email!"
                );

                continue;
            }

            break;
        }


        // PHONE

        String phone;

        while (true) {

            System.out.print(
                    "Enter Phone Number: "
            );

            phone =
                    sc.nextLine().trim();

            if (!isValidPhone(phone)) {

                System.out.println(
                        "Phone must contain 10 digits!"
                );

                continue;
            }

            break;
        }


        // SEMESTER

        int semester;

        while (true) {

            System.out.print(
                    "Enter Semester (1-8): "
            );

            if (!sc.hasNextInt()) {

                System.out.println(
                        "Semester must be a number!"
                );

                sc.nextLine();
                continue;
            }

            semester =
                    sc.nextInt();

            sc.nextLine();

            if (!isValidSemester(semester)) {

                System.out.println(
                        "Semester must be between 1 and 8!"
                );

                continue;
            }

            break;
        }


        // CGPA

        double cgpa;

        while (true) {

            System.out.print(
                    "Enter CGPA (0-10): "
            );

            if (!sc.hasNextDouble()) {

                System.out.println(
                        "CGPA must be a number!"
                );

                sc.nextLine();
                continue;
            }

            cgpa =
                    sc.nextDouble();

            sc.nextLine();

            if (!isValidCGPA(cgpa)) {

                System.out.println(
                        "CGPA must be between 0 and 10!"
                );

                continue;
            }

            break;
        }


        // INSERT INTO MYSQL

        String sql =
                "INSERT INTO students " +
                "(id, name, course, email, phone, semester, cgpa) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";


        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        con.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, course);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setInt(6, semester);
            stmt.setDouble(7, cgpa);

            stmt.executeUpdate();


            Student s =
                    new Student(
                            id,
                            name,
                            course,
                            email,
                            phone,
                            semester,
                            cgpa
                    );

            students.add(s);


            System.out.println();
            System.out.println(
                    "Student added successfully!"
            );

            System.out.println(
                    "Student saved to MySQL!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error adding student!"
            );

            e.printStackTrace();
        }
    }


    // =========================================
    // DISPLAY STUDENTS
    // =========================================

    static void displayStudents() {

        if (students.isEmpty()) {

            System.out.println(
                    "No students found!"
            );

            return;
        }

        System.out.println();
        System.out.println(
                "========== STUDENT RECORDS =========="
        );

        for (Student s : students) {

            s.display();
        }
    }


    // =========================================
    // SEARCH STUDENT
    // =========================================

    static void searchStudent() {

        System.out.println();
        System.out.println(
                "1. Search by ID"
        );

        System.out.println(
                "2. Search by Name"
        );

        System.out.print(
                "Enter choice: "
        );

        int choice;

        if (!sc.hasNextInt()) {

            System.out.println(
                    "Invalid choice!"
            );

            sc.nextLine();
            return;
        }

        choice =
                sc.nextInt();

        sc.nextLine();


        if (choice == 1) {

            System.out.print(
                    "Enter Student ID: "
            );

            int id =
                    sc.nextInt();

            sc.nextLine();

            for (Student s : students) {

                if (s.id == id) {

                    s.display();
                    return;
                }
            }

            System.out.println(
                    "Student not found!"
            );
        }


        else if (choice == 2) {

            System.out.print(
                    "Enter Student Name: "
            );

            String name =
                    sc.nextLine().trim();

            boolean found = false;

            for (Student s : students) {

                if (s.name.equalsIgnoreCase(name)) {

                    s.display();
                    found = true;
                }
            }

            if (!found) {

                System.out.println(
                        "Student not found!"
                );
            }
        }


        else {

            System.out.println(
                    "Invalid choice!"
            );
        }
    }


    // =========================================
    // UPDATE STUDENT
    // =========================================

    static void updateStudent() {

        System.out.print(
                "Enter Student ID to update: "
        );

        int id =
                sc.nextInt();

        sc.nextLine();


        Student foundStudent = null;


        for (Student s : students) {

            if (s.id == id) {

                foundStudent = s;
                break;
            }
        }


        if (foundStudent == null) {

            System.out.println(
                    "Student not found!"
            );

            return;
        }


        System.out.println(
                "Enter new details:"
        );


        // NAME

        String name;

        while (true) {

            System.out.print(
                    "Enter New Name: "
            );

            name =
                    sc.nextLine().trim();

            if (!isValidName(name)) {

                System.out.println(
                        "Invalid name!"
                );

                continue;
            }

            break;
        }


        // COURSE

        String course;

        while (true) {

            System.out.print(
                    "Enter New Course: "
            );

            course =
                    sc.nextLine().trim();

            if (course.isEmpty()) {

                System.out.println(
                        "Course cannot be empty!"
                );

                continue;
            }

            break;
        }


        // EMAIL

        String email;

        while (true) {

            System.out.print(
                    "Enter New Email: "
            );

            email =
                    sc.nextLine().trim();

            if (!isValidEmail(email)) {

                System.out.println(
                        "Invalid email!"
                );

                continue;
            }

            break;
        }


        // PHONE

        String phone;

        while (true) {

            System.out.print(
                    "Enter New Phone: "
            );

            phone =
                    sc.nextLine().trim();

            if (!isValidPhone(phone)) {

                System.out.println(
                        "Phone must contain 10 digits!"
                );

                continue;
            }

            break;
        }


        // SEMESTER

        int semester;

        while (true) {

            System.out.print(
                    "Enter New Semester (1-8): "
            );

            if (!sc.hasNextInt()) {

                System.out.println(
                        "Semester must be a number!"
                );

                sc.nextLine();
                continue;
            }

            semester =
                    sc.nextInt();

            sc.nextLine();

            if (!isValidSemester(semester)) {

                System.out.println(
                        "Semester must be between 1 and 8!"
                );

                continue;
            }

            break;
        }


        // CGPA

        double cgpa;

        while (true) {

            System.out.print(
                    "Enter New CGPA (0-10): "
            );

            if (!sc.hasNextDouble()) {

                System.out.println(
                        "CGPA must be a number!"
                );

                sc.nextLine();
                continue;
            }

            cgpa =
                    sc.nextDouble();

            sc.nextLine();

            if (!isValidCGPA(cgpa)) {

                System.out.println(
                        "CGPA must be between 0 and 10!"
                );

                continue;
            }

            break;
        }


        // UPDATE MYSQL

        String sql =
                "UPDATE students SET " +
                "name = ?, " +
                "course = ?, " +
                "email = ?, " +
                "phone = ?, " +
                "semester = ?, " +
                "cgpa = ? " +
                "WHERE id = ?";


        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        con.prepareStatement(sql)
        ) {

            stmt.setString(1, name);
            stmt.setString(2, course);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setInt(5, semester);
            stmt.setDouble(6, cgpa);
            stmt.setInt(7, id);

            stmt.executeUpdate();


            // UPDATE ARRAYLIST

            foundStudent.name = name;
            foundStudent.course = course;
            foundStudent.email = email;
            foundStudent.phone = phone;
            foundStudent.semester = semester;
            foundStudent.cgpa = cgpa;


            System.out.println(
                    "Student updated successfully!"
            );

            System.out.println(
                    "Changes saved to MySQL!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error updating student!"
            );

            e.printStackTrace();
        }
    }


    // =========================================
    // DELETE STUDENT
    // =========================================

    static void deleteStudent() {

        System.out.print(
                "Enter Student ID to delete: "
        );

        int id =
                sc.nextInt();

        sc.nextLine();


        String sql =
                "DELETE FROM students WHERE id = ?";


        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        con.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            int rows =
                    stmt.executeUpdate();


            if (rows > 0) {

                for (int i = 0;
                     i < students.size();
                     i++) {

                    if (students.get(i).id == id) {

                        students.remove(i);
                        break;
                    }
                }

                System.out.println(
                        "Student deleted successfully!"
                );

                System.out.println(
                        "Student deleted from MySQL!"
                );

            } else {

                System.out.println(
                        "Student not found!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting student!"
            );

            e.printStackTrace();
        }
    }


    // =========================================
    // MAIN METHOD
    // =========================================

    public static void main(String[] args) {

        loadStudentsFromDatabase();


        while (true) {

            System.out.println();
            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "       STUDENT MANAGEMENT SYSTEM"
            );

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "1. Add Student"
            );

            System.out.println(
                    "2. Display Students"
            );

            System.out.println(
                    "3. Search Student"
            );

            System.out.println(
                    "4. Update Student"
            );

            System.out.println(
                    "5. Delete Student"
            );

            System.out.println(
                    "6. Exit"
            );

            System.out.print(
                    "Enter your choice: "
            );


            if (!sc.hasNextInt()) {

                System.out.println(
                        "Invalid choice!"
                );

                sc.nextLine();
                continue;
            }


            int choice =
                    sc.nextInt();

            sc.nextLine();


            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    displayStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:

                    System.out.println(
                            "Thank you for using Student Management System!"
                    );

                    sc.close();
                    return;

                default:

                    System.out.println(
                            "Invalid choice!"
                    );
            }
        }
    }
}