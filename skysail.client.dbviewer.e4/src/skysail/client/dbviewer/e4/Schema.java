package skysail.client.dbviewer.e4;

public class Schema {
    private String summary = "";
    private String description = "";

    public Schema(String summary) {
        this.summary = summary;
    }

    public Schema(String summary, String description) {
        this.summary = summary;
        this.description = description;

    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
