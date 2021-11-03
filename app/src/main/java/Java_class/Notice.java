package Java_class;

public class Notice {
    String noticeBody,noticeSubject,noticeProviderName,noticeProviderID,noticeDate;

    public Notice(){

    }

    public Notice(String noticeBody, String noticeSubject, String noticeProviderName, String noticeProviderID, String noticeDate) {
        this.noticeBody = noticeBody;
        this.noticeSubject = noticeSubject;
        this.noticeProviderName = noticeProviderName;
        this.noticeProviderID = noticeProviderID;
        this.noticeDate = noticeDate;
    }

    public String getNoticeBody() {
        return noticeBody;
    }

    public void setNoticeBody(String noticeBody) {
        this.noticeBody = noticeBody;
    }

    public String getNoticeSubject() {
        return noticeSubject;
    }

    public void setNoticeSubject(String noticeSubject) {
        this.noticeSubject = noticeSubject;
    }

    public String getNoticeProviderName() {
        return noticeProviderName;
    }

    public void setNoticeProviderName(String noticeProviderName) {
        this.noticeProviderName = noticeProviderName;
    }

    public String getNoticeProviderID() {
        return noticeProviderID;
    }

    public void setNoticeProviderID(String noticeProviderID) {
        this.noticeProviderID = noticeProviderID;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }
}
