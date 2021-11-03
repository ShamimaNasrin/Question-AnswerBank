package Java_class;

public class Report {
    String reportBody,reportProviderID,reportDate;

    public Report(){

    }

    public Report(String reportBody, String reportProviderID, String reportDate) {
        this.reportBody = reportBody;
        this.reportProviderID = reportProviderID;
        this.reportDate = reportDate;
    }

    public String getReportBody() {
        return reportBody;
    }

    public void setReportBody(String reportBody) {
        this.reportBody = reportBody;
    }

    public String getReportProviderID() {
        return reportProviderID;
    }

    public void setReportProviderID(String reportProviderID) {
        this.reportProviderID = reportProviderID;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
}
