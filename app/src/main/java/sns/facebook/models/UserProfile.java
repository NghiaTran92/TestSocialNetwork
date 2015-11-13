package sns.facebook.models;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sns.facebook.Constants;

public class UserProfile {
    private static final String TAG = "UserProfile";
    private String id;
    private Picture picture;
    private String email;
    private String link;
    private String name;
    private Location location;
    private String birthday;
    private String gender;

    public UserProfile() {

    }

    public UserProfile(String id, Picture picture, String email, String link, String name,
                       Location location, String birthday, String gender) {
        this.id = id;
        this.picture = picture;
        this.email = email;
        this.link = link;
        this.name = name;
        this.location = location;
        this.birthday = birthday;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getBirthday() {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = null;
        try {
            if (birthday != null) {
                date = format.parse(birthday);
            }
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        return date;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public EnumGender getGender() {
        return (EnumGender.MALE.toString().equals(gender)) ? EnumGender.MALE : EnumGender.FEMALE;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
