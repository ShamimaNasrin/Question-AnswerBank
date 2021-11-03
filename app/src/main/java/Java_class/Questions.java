package Java_class;

public class Questions {
    String questionCourse,questionFile,questionProviderID,questionYear;

    public Questions(){

    }

    public Questions( String questionCourse, String questionFile, String questionProviderID, String questionYear) {
        this.questionCourse = questionCourse;
        this.questionFile = questionFile;
        this.questionProviderID = questionProviderID;
        this.questionYear = questionYear;
    }

    public String getQuestionCourse() {
        return questionCourse;
    }

    public void setQuestionCourse(String questionCourse) {
        this.questionCourse = questionCourse;
    }

    public String getQuestionFile() {
        return questionFile;
    }

    public void setQuestionFile(String questionFile) {
        this.questionFile = questionFile;
    }

    public String getQuestionProviderID() {
        return questionProviderID;
    }

    public void setQuestionProviderID(String questionProviderID) {
        this.questionProviderID = questionProviderID;
    }

    public String getQuestionYear() {
        return questionYear;
    }

    public void setQuestionYear(String questionYear) {
        this.questionYear = questionYear;
    }
}
