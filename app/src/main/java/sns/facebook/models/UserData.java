package sns.facebook.models;

import com.google.gson.annotations.SerializedName;

public class UserData {

  private String url;
  @SerializedName("is_silhouette") private boolean isSilhouette;

  public UserData(String url, boolean isSilhouette) {
    this.url = url;
    this.isSilhouette = isSilhouette;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isSilhouette() {
    return isSilhouette;
  }

  public void setIsSilhouette(boolean isSilhouette) {
    this.isSilhouette = isSilhouette;
  }
}
