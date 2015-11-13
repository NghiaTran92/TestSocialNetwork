package sns.facebook.models;

public enum EnumGender {
  MALE("male"),
  FEMALE("female");

  private String value;

  EnumGender(String val) {
    this.value = val;
  }

  @Override public String toString() {
    return value;
  }
}
