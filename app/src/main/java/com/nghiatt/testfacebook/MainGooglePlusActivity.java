package com.nghiatt.testfacebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import sns.googleplus.GooglePlusSNS;

public class MainGooglePlusActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG="MainGooglePlusActivity";
    private SignInButton mBtnSignIn;
    private Button mBtnSignOut;
    private GooglePlusSNS mGooglePlusSNS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googleplus_main);
        mGooglePlusSNS=new GooglePlusSNS(this);

        mGooglePlusSNS.registerConnectionFailedListener(this);

        mBtnSignIn=(SignInButton)findViewById(R.id.sign_in_btn);
        mBtnSignIn.setOnClickListener(this);
        mBtnSignOut=(Button)findViewById(R.id.sign_out_btn);
        mBtnSignOut.setOnClickListener(this);

        mBtnSignIn.setSize(SignInButton.SIZE_STANDARD);
        mBtnSignIn.setScopes(mGooglePlusSNS.getGoogleSignInOptions().getScopeArray());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GooglePlusSNS.GOOGLE_PLUS_SIGN_IN_REQUEST_CODE){
            handleSignInResult(mGooglePlusSNS.getSignInResultFromIntent(data));
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.i(TAG,"id:"+acct.getId()+" --name:"+acct.getDisplayName()+" --email:"+acct.getEmail()+" --photo:"+acct.getPhotoUrl());
        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    @Override
    protected void onDestroy() {
        mGooglePlusSNS.unregisterConnectionFailedListener(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.sign_in_btn:
                mGooglePlusSNS.signIn();
                break;
            case R.id.sign_out_btn:
                mGooglePlusSNS.signOut(null);
                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG,"error:"+connectionResult.getErrorMessage());
    }
}
