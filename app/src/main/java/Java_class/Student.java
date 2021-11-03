package Java_class;

public class Student {
    String studentName,studentClass,studentSection,studentRoll,studentPhone,studentEmail,studentPassword, profileImage;

    public Student(){

    }

    public Student(String studentName, String studentClass, String studentSection, String studentRoll, String studentPhone, String studentEmail, String studentPassword,String profileImage) {
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.studentSection = studentSection;
        this.studentRoll = studentRoll;
        this.studentPhone = studentPhone;
        this.studentEmail = studentEmail;
        this.studentPassword = studentPassword;
        this.profileImage = profileImage;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentSection() {
        return studentSection;
    }

    public void setStudentSection(String studentSection) {
        this.studentSection = studentSection;
    }

    public String getStudentRoll() {
        return studentRoll;
    }

    public void setStudentRoll(String studentRoll) {
        this.studentRoll = studentRoll;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
