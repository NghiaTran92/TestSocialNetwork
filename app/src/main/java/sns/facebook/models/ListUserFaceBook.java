package sns.facebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListUserFaceBook {
    @SerializedName("data")
    private ArrayList<UserProfile> listUser;

    @SerializedName("paging")
    private Paging paging;

    public ListUserFaceBook() {
    }


    public ListUserFaceBook(ArrayList<UserProfile> listUser) {
        this.listUser = listUser;
    }

    public ArrayList<UserProfile> getListUser() {
        if (listUser == null) {
            listUser = new ArrayList<>();
        }
        return listUser;
    }

    public void setListUser(ArrayList<UserProfile> listUser) {
        this.listUser = listUser;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
