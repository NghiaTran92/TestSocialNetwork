package sns.facebook.models;

import com.google.gson.annotations.SerializedName;

public class Paging {
    @SerializedName("cursors")
    private Cursor cursors;
    @SerializedName("previous")
    private String previousUrl;
    @SerializedName("next")
    private String nextUrl;

    public Paging() {
    }

    public Paging(Cursor cursors, String previousUrl, String nextUrl) {
        this.cursors = cursors;
        this.previousUrl = previousUrl;
        this.nextUrl = nextUrl;
    }

    public Cursor getCursors() {
        return cursors;
    }

    public void setCursors(Cursor cursors) {
        this.cursors = cursors;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
}
