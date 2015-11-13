package sns.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sns.facebook.models.Cursor;
import sns.facebook.models.ListUserFaceBook;
import sns.facebook.models.UserProfile;


public class FacebookSNS {

    private static final String TAG = "FacebookSNS";

    private static final String FIELDS = "fields";
    private static final String LIMIT = "limit";
    private static final String BEFORE = "before";
    private static final String AFTER = "after";
    private static final String EXCLUDED_IDS = "excluded_ids";
    private static final String DATA_FIELDS =
            "picture.type(large),quotes,email,id,name,link,birthday,age_range,first_name,last_name,gender,locale,timezone,verified,updated_time";

    private List<String> permission = Arrays.asList("public_profile", "email", "user_birthday", "user_friends");
    private Context applicationContext;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private CustomFacebookCallback customFacebookCallback;

    private Gson gson;

    public FacebookSNS(Context applicationContext) {
        this.applicationContext = applicationContext;
        FacebookSdk.sdkInitialize(this.applicationContext);
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        gson = new Gson();

        if (AccessToken.getCurrentAccessToken() != null) {
            logout();
        }
        loginManager.registerCallback(callbackManager, new LoginResultFacebookCallback());
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void logInWithReadPermissions(Activity activity) {
        loginManager.logInWithReadPermissions(activity, permission);
    }

    public static void inviteApp(Activity activity){
        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(Constants.APP_LINK_URL)
                    .setPreviewImageUrl(Constants.PREVIEW_IMAGE_URL)
                    .build();
            AppInviteDialog.show(activity, content);
        }
    }

    public void logout() {
        loginManager.logOut();
    }

    public void getInviteFriend(final CustomFacebookCallback<ListUserFaceBook> callback) {
        Bundle parameter = new Bundle();
        parameter.putString(FIELDS, DATA_FIELDS);
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                "/me/invitable_friends",
                parameter,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response.getJSONObject() != null) {
                            String jsonString = response.getJSONObject().toString();
                            Log.i(TAG, "invite friend jsonObject=" + jsonString);
                            ListUserFaceBook listUserFaceBook = null;
                            try {
                                listUserFaceBook =
                                        new Gson().fromJson(jsonString, new TypeToken<ListUserFaceBook>() {
                                        }.getType());
                            } catch (JsonSyntaxException jse) {
                                Log.e(TAG, jse.getMessage());
                            }
                            if (callback != null) {
                                callback.onSuccess(listUserFaceBook);
                            }
                        } else {
                            Log.e(TAG, "invite friend json object = null and message=" + response.getError().getErrorMessage());
                        }

                        Log.i(TAG, "invite friend jsonArray=" + response.getJSONArray());

                    }
                });
        request.executeAsync();
    }

//    public void sendInviteRequest(ArrayList<String> listFacebookID) {
//        StringBuilder arrayIds = new StringBuilder(EXCLUDED_IDS + "=");
//        arrayIds.append("[");
//        for (String id : listFacebookID) {
//            arrayIds.append("'" + id + "'");
//            arrayIds.append(",");
//        }
//        if (listFacebookID.size() > 0) {
//            arrayIds = arrayIds.deleteCharAt(arrayIds.length() - 1);
//        }
//        arrayIds.append("]");
//        Log.e("dcm",arrayIds.toString());
//
//        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
//                "/me/invitable_friends?"+"access_token="+AccessToken.getCurrentAccessToken()+"&" + arrayIds.toString(),
//                null,
//                HttpMethod.GET,
//                new GraphRequest.Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse response) {
//                        if (response.getJSONObject() != null) {
//                            String jsonString = response.getJSONObject().toString();
//                            Log.i(TAG, "invite friend jsonObject=" + jsonString);
//
//
//                        } else {
//                            Log.e(TAG, "invite friend json object = null and message=" + response.getError().getErrorMessage());
//                        }
//
//                        Log.i(TAG, "invite friend jsonArray=" + response.getJSONArray());
//
//                    }
//                });
//        request.executeAsync();
//    }

    public void getAllFriendByUser(String facebookUserID, CustomFacebookCallback<ListUserFaceBook> callback) {
        this.getAllFriendByUser(facebookUserID, callback, 25, null, false);
    }

    public void getAllFriendByUser(String facebookUserID, CustomFacebookCallback<ListUserFaceBook> callback, Cursor cursor, boolean isNextPage) {
        this.getAllFriendByUser(facebookUserID, callback, 25, cursor, isNextPage);
    }

    public void getAllFriendByUser(String facebookUserID, final CustomFacebookCallback<ListUserFaceBook> callback, int limit, Cursor cursor, boolean isNextPage) {
        Bundle parameter = new Bundle();
        parameter.putString(FIELDS, DATA_FIELDS);
        // set default limit=25
        if (limit <= 0) {
            limit = 25;
        }
        parameter.putInt(LIMIT, limit);
        // https://developers.facebook.com/docs/graph-api/using-graph-api/v2.5#paging
        if (cursor != null) {
            if (isNextPage) {
                parameter.putString(AFTER, cursor.getAfter());
            } else {
                parameter.putString(BEFORE, cursor.getBefore());
            }
        }
        GraphRequest requestAllFriend = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + facebookUserID + "/taggable_friends",
                parameter,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response.getJSONObject() != null) {
                            String jsonString = response.getJSONObject().toString();
                            Log.i(TAG, "all friend jsonObject=" + jsonString);
                            ListUserFaceBook listUserFaceBook = null;
                            try {
                                listUserFaceBook =
                                        new Gson().fromJson(jsonString, new TypeToken<ListUserFaceBook>() {
                                        }.getType());
                            } catch (JsonSyntaxException jse) {
                                Log.e(TAG, jse.getMessage());
                            }
                            if (callback != null) {
                                callback.onSuccess(listUserFaceBook);
                            }
                        } else {
                            Log.e(TAG, "all friend json object = null");
                        }

                        Log.i(TAG, "all friend jsonArray=" + response.getJSONArray());

                    }
                }
        );

        requestAllFriend.executeAsync();
    }

    public void getFriendUseApp(final CustomFacebookCallback<ArrayList<UserProfile>> callback) {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONArrayCallback() {
                        @Override
                        public void onCompleted(JSONArray objects, GraphResponse response) {
                            Log.i(TAG, objects.toString());
                            ArrayList<UserProfile> listFriend = null;
                            try {
                                listFriend = new Gson().fromJson(objects.toString(), new TypeToken<ArrayList<UserProfile>>() {
                                }.getType());
                            } catch (JsonSyntaxException jse) {
                                Log.e(TAG, jse.getMessage());
                            }
                            if (callback != null) {
                                callback.onSuccess(listFriend);
                            }
                        }

                    });
            Bundle parameter = new Bundle();
            parameter.putString(FIELDS, DATA_FIELDS);
            request.setParameters(parameter);
            request.executeAsync();
        } else {
            Log.e(TAG, "must login before call getFriendUseApp ");
        }
    }

    public void setFacebookCallback(CustomFacebookCallback callback) {
        this.customFacebookCallback = callback;
    }

    private class LoginResultFacebookCallback implements FacebookCallback<LoginResult> {
        @Override
        public void onSuccess(LoginResult loginResult) {

            ////
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                            //if request info failure
                            if (graphResponse.getError() != null) {
                                String errorMessage = graphResponse.getError().getErrorMessage();
                                Log.e(TAG, errorMessage);
                                return;
                            }

                            //request success --> parse jsonobject to obtain user information
                            try {
                                Log.i(TAG, jsonObject.toString());
                                UserProfile userProfile =
                                        new Gson().fromJson(jsonObject.toString(), new TypeToken<UserProfile>() {
                                        }.getType());


                                if (customFacebookCallback != null) {
                                    customFacebookCallback.onSuccess(userProfile);
                                }
                            } catch (JsonSyntaxException e) {
                                String mes = e.getMessage();
                                if (mes == null) {
                                    mes = "";
                                }
                                Log.e(TAG, mes);
                                return;
                            }
                        }
                    });

            Bundle parameter = new Bundle();
            parameter.putString(FIELDS, DATA_FIELDS);
            request.setParameters(parameter);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "Login facebook cancelled");
            if (customFacebookCallback != null) {
                customFacebookCallback.onCancel();
            }
        }

        @Override
        public void onError(FacebookException e) {
            Log.e(TAG, "Login facebook Error: " + e.getMessage());
            if (customFacebookCallback != null) {
                customFacebookCallback.onError(e);
            }
        }
    }

    public interface CustomFacebookCallback<T> {
        void onSuccess(T data);

        void onCancel();

        void onError(FacebookException e);
    }
}
