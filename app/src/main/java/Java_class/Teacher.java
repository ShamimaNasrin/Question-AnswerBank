package Java_class;

public class Teacher {
    String teacherName,teacherPhone,teacherEmail,teacherPassword,teacherImage;

    public Teacher(){

    }

    public Teacher(String teacherName, String teacherPhone, String teacherEmail, String teacherPassword, String teacherImage) {
        this.teacherName = teacherName;
        this.teacherPhone = teacherPhone;
        this.teacherEmail = teacherEmail;
        this.teacherPassword = teacherPassword;
        this.teacherImage = teacherImage;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        this.teacherImage = teacherImage;
    }
}
