package Java_class;

import android.net.Uri;

public class Answer {
    String answerCourseName,answerFile,answerProviderID,answerYear;

    public Answer(){

    }

    public Answer(String answerCourseName, String answerFile, String answerProviderID, String answerYear) {
        this.answerCourseName = answerCourseName;
        this.answerFile = answerFile;
        this.answerProviderID = answerProviderID;
        this.answerYear = answerYear;
    }

    public String getAnswerCourseName() {
        return answerCourseName;
    }

    public void setAnswerCourseName(String answerCourseName) {
        this.answerCourseName = answerCourseName;
    }

    public String getAnswerFile() {
        return answerFile;
    }

    public void setAnswerFile(String answerFile) {
        this.answerFile = answerFile;
    }

    public String getAnswerProviderID() {
        return answerProviderID;
    }

    public void setAnswerProviderID(String answerProviderID) {
        this.answerProviderID = answerProviderID;
    }

    public String getAnswerYear() {
        return answerYear;
    }

    public void setAnswerYear(String answerYear) {
        this.answerYear = answerYear;
    }
}
