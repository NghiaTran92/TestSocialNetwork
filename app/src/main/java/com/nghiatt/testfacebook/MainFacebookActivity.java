package com.nghiatt.testfacebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;

import sns.facebook.FacebookSNS;
import sns.facebook.models.ListUserFaceBook;
import sns.facebook.models.UserProfile;

public class MainFacebookActivity extends AppCompatActivity {
    private static final String TAG = "MainFacebookActivity";

    private Button mBtnGetUser;
    private FacebookSNS facebookSNS;
    private UserProfile mUserProfile;
    private ListUserFaceBook mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_main);
        mBtnGetUser = (Button) findViewById(R.id.btn_get_user);
        facebookSNS = new FacebookSNS(getApplicationContext());
        facebookSNS.setFacebookCallback(new FacebookSNS.CustomFacebookCallback<UserProfile>() {
            @Override
            public void onSuccess(UserProfile userProfile) {
/*//                facebookSNS.getFriendUseApp(new FacebookSNS.CustomFacebookCallback<ArrayList<UserProfile>>() {
//                    @Override
//                    public void onSuccess(ArrayList<UserProfile> data) {
//                        for (UserProfile user : data) {
//                            Log.i(TAG, "name=" + user.getName());
//                        }
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException e) {
//
//                    }
//                });
                mUserProfile = userProfile;
                facebookSNS.getInviteFriend(new FacebookSNS.CustomFacebookCallback<ListUserFaceBook>() {
                    @Override
                    public void onSuccess(ListUserFaceBook data) {
                        mCurrentPage = data;
                        ArrayList<String> testList=new ArrayList<String>();
                        ArrayList<UserProfile> listUser = data.getListUser();
                        for (UserProfile user : listUser) {
                            Log.i(TAG, "invite name=" + user.getName() + " -- id=" + user.getId());
                            testList.add(user.getId());
                        }

                        facebookSNS.sendInviteRequest(testList);
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException e) {

                    }
                });
//                facebookSNS.getAllFriendByUser(userProfile.getId(),
//                        new FacebookSNS.CustomFacebookCallback<ListUserFaceBook>() {
//                            @Override
//                            public void onSuccess(ListUserFaceBook data) {
//                                mCurrentPage = data;
//                                ArrayList<UserProfile> listUser = data.getListUser();
//                                for (UserProfile user : listUser) {
//                                    Log.i(TAG, "name=" + user.getName());
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancel() {
//
//                            }
//
//                            @Override
//                            public void onError(FacebookException e) {
//
//                            }
//                        });*/
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
        mBtnGetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //facebookSNS.logInWithReadPermissions(MainFacebookActivity.this);
                FacebookSNS.inviteApp(MainFacebookActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookSNS.onActivityResult(requestCode, resultCode, data);
    }
}
