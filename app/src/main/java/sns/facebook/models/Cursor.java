package sns.facebook.models;

import com.google.gson.annotations.SerializedName;

public class Cursor {
    @SerializedName("before")
    private String before;
    @SerializedName("after")
    private String after;

    public Cursor() {
    }

    public Cursor(String before, String after) {
        this.before = before;
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }
}
