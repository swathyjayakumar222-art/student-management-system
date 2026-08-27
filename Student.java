public class Student {

    int id;
    String name;
    String course;
    String email;
    String phone;
    int semester;
    double cgpa;

    // Constructor
    Student(int id, String name, String course, String email,
            String phone, int semester, double cgpa) {

        this.id = id;
        this.name = name;
        this.course = course;
        this.email = email;
        this.phone = phone;
        this.semester = semester;
        this.cgpa = cgpa;
    }

    // Calculate Grade
    String getGrade() {

        if (cgpa >= 9.0) {
            return "A+";
        }
        else if (cgpa >= 8.0) {
            return "A";
        }
        else if (cgpa >= 7.0) {
            return "B";
        }
        else if (cgpa >= 6.0) {
            return "C";
        }
        else if (cgpa >= 5.0) {
            return "D";
        }
        else {
            return "F";
        }
    }

    // Calculate Pass/Fail Status
    String getStatus() {

        if (cgpa >= 5.0) {
            return "PASS";
        }
        else {
            return "FAIL";
        }
    }

    // Display Student Details
    void display() {

        System.out.println("----------------------------------------");
        System.out.println("Student ID : " + id);
        System.out.println("Name       : " + name);
        System.out.println("Course     : " + course);
        System.out.println("Email      : " + email);
        System.out.println("Phone      : " + phone);
        System.out.println("Semester   : " + semester);
        System.out.printf("CGPA       : %.2f%n", cgpa);
        System.out.println("Grade      : " + getGrade());
        System.out.println("Status     : " + getStatus());
        System.out.println("----------------------------------------");
    }
}