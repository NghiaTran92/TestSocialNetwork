package sns.facebook.models;

public class Picture {

  private UserData data;

  public Picture(UserData data) {
    this.data = data;
  }

  public UserData getData() {
    return data;
  }

  public void setData(UserData data) {
    this.data = data;
  }
}
