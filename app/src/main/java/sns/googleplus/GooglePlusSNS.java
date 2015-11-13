package sns.googleplus;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

public class GooglePlusSNS {
    private static final String TAG = "GooglePlusSNS";

    private FragmentActivity mActivity;
    private GoogleApiClient mGoogleApiClient;


    private GoogleSignInOptions mGoogleSignInOptions;
    public static final int GOOGLE_PLUS_SIGN_IN_REQUEST_CODE = 9001;

    public GooglePlusSNS(FragmentActivity activity) {
        mActivity = activity;
        mGoogleSignInOptions = new GoogleSignInOptions.Builder()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .addScope(new Scope(Scopes.PROFILE))
                .build();


    }

    public GoogleSignInOptions getGoogleSignInOptions() {
        return mGoogleSignInOptions;
    }

    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener listener) {
        mGoogleApiClient.registerConnectionFailedListener(listener);
    }

    public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener listener) {
        mGoogleApiClient.unregisterConnectionFailedListener(listener);
    }

    public OptionalPendingResult<GoogleSignInResult> silentSignIn() {
        return Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
    }

    public GoogleSignInResult getSignInResultFromIntent(Intent data) {
        return Auth.GoogleSignInApi.getSignInResultFromIntent(data);
    }


    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, GOOGLE_PLUS_SIGN_IN_REQUEST_CODE);

    }

    public void signOut(final ResultCallback<Status> callback) {

        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    Log.d(TAG, "status:" + status.getStatusMessage());
                    if (callback != null) {
                        callback.onResult(status);
                    }
                }
            });
        }
    }

    public void revokeAccess(ResultCallback<Status> callback) {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(callback);
    }
}
