package com.figsinc.app.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.analyze.AnalyzeActivity;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SocialMediaActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    CallbackManager callbackManager;
    //  @BindView(R.id.button_signupWithFacebook)  LoginButton loginButton;
    @BindView(R.id.button_signupWithTwitter)
    Button button_signupWithTwitter;
    @BindView(R.id.button_signupWithGoogle)
    Button button_signupWithGoogle;
    @BindView(R.id.fabric_button_signupWithTwitter)
    TwitterLoginButton fabric_button_signupWithTwitter;

    private static final int RC_SIGN_IN = 007;
    private static final String TAG = MainActivity.class.getSimpleName();

    private boolean isFacebook = false;
    private boolean isTwitter = false;

    private String accessToken;
    private String provider;
    private String accessTokenSecret;

    private void twitterConfig() {
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("9HxDS8tstuL0XGBwiizNixqJi", "eWjpHvxVxefAWFUsuWWWwi9BJSkLIKFtHIJJZ097R8C5jrpH89"))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        twitterConfig();
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            String Name;
            String FEmail;

            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println(" loginResult " + loginResult.toString());
                // Facebook Email address
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());

                                try {
                                    Name = object.getString("name");
                                    FEmail = object.getString("email");
                                    Log.v("Email = ", " " + FEmail);

                                    // Toast.makeText(getApplicationContext(), "Name " + Name + " == FEmail== " + FEmail, Toast.LENGTH_LONG).show();

                                    /** This block obtains Facebook UserID and Token */
                                    AccessToken token = AccessToken.getCurrentAccessToken();
                                    if (token != null) {
                                        // Toast.makeText(getApplicationContext(), token.toString(), Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "Token: " + token.getToken());
                                        Log.e(TAG, "UserID: " + token.getUserId());
                                        accessToken = token.getToken();
                                        provider = "facebook";
                                        network();
                                    }
                                    /***/

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        button_signupWithTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFacebook = false;
                isTwitter = true;
                fabric_button_signupWithTwitter.performClick();
            }
        });
        // Set up the login button by setting callback to invoke when authorization request
        // completes

        fabric_button_signupWithTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // System.out.println(" Twitter Success ======>>>>>" + result.data.getUserName());
                requestEmailAddress(SocialMediaActivity.this, result.data);
                String Username = result.data.getUserName();
                accessToken = result.data.getAuthToken().token.toString();
                provider = "twitter";
                accessTokenSecret = result.data.getAuthToken().secret.toString();
                isTwitter = true;

                System.out.println(" Twitter Success ======>>>>>" + accessToken);

                network();

            }

            @Override
            public void failure(TwitterException exception) {
                // Upon error, show a toast message indicating that authorization request failed.
                Toast.makeText(getApplicationContext(), exception.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        // profile, and Google Drive. The first time you request a code you will
// be able to exchange it for an access token and refresh token, which
// you should store. In subsequent calls, the code will only result in
// an access token. By asking for profile access (through
// DEFAULT_SIGN_IN) you will also get an ID Token as a result of the
// code exchange.
        String serverClientId = getString(R.string.server_client_id);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestServerAuthCode(serverClientId)
                .requestIdToken(serverClientId)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private static void requestEmailAddress(final Context context, TwitterSession session) {
        new TwitterAuthClient().requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                // System.out.println(" Twitter Email ======>>>>>");
               // Toast.makeText(context, result.data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.button_signupWithFacebook)
    public void facebookLogin(View view) {
        isFacebook = true;
        isTwitter = false;
        LoginManager.getInstance().logInWithReadPermissions(SocialMediaActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
    }

    @OnClick(R.id.button_signupWithEmail)
    public void EmailSignUp(View view) {
        Intent intent = new Intent(SocialMediaActivity.this, SignUpActivity.class);
        startActivity(intent);
        // finish();
    }


    @OnClick(R.id.button_signupWithGoogle)
    public void gplusSign(View view) {
        signIn();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isFacebook) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (isTwitter) {
            fabric_button_signupWithTwitter.onActivityResult(requestCode, resultCode, data);
        } else // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ///////////////// Google plus

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

            hideProgressDialog();
            new RetrieveTokenTask().execute(email);
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "There is some problem with signing in... Please try again later...", Toast.LENGTH_SHORT).show();
            hideProgressDialog();
        }
    }

   /* @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }*/

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("provider", provider);
            jsonBody.put("access_token", accessToken);
            if(isTwitter) {
                jsonBody.put("access_token_secret", accessTokenSecret);
            }
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.socialLogin,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                           // Toast.makeText(SocialMediaActivity.this, response, Toast.LENGTH_SHORT).show();
                              try {
                                JSONObject token = new JSONObject(response);
                                String auth_token = token.getString("token");

                                SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("auth_token", auth_token);
                                editor.commit();

                                Intent intent = new Intent(SocialMediaActivity.this, AnalyzeActivity.class);
                                startActivity(intent);
                                finish();
                                // Try and catch are included to handle any errors due to JSON
                            } catch (JSONException e) {
                                // If an error occurs, this prints the error to the log
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;


                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);

                        Toast.makeText(SocialMediaActivity.this, "  " + jsonError, Toast.LENGTH_SHORT).show();
                        // {"non_field_errors":["Unable to login with provided credentials."]}
                        try {
                            JSONObject jsonObject = new JSONObject(jsonError);
                            // System.out.println(" jsonError ==>>> " + jsonObject.getJSONArray("non_field_errors").getString(0));
                            jsonError = jsonObject.getJSONArray("non_field_errors").getString(0);
                            Toast.makeText(SocialMediaActivity.this, " " + jsonError, Toast.LENGTH_SHORT).show();
                        } catch (JSONException jsonException) {
                            error.printStackTrace();
                        }

                    }

                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), REQ_SIGN_IN_REQUIRED);
            } catch (GoogleAuthException e) {
                Log.e(TAG, e.getMessage());
            }
            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // ((TextView) findViewById(R.id.token_value)).setText("Token Value: " + s);
           // Toast.makeText(SocialMediaActivity.this, "Token Value: " + s, Toast.LENGTH_SHORT).show();
            System.out.println(" Token Value:  " + s);
            provider = "google-oauth2";
            accessTokenSecret = "";
            accessToken  =s;
            // Toast.makeText(this, ""+"Name: " + personName + ", email: " + email  + "auth "+ acct.getId(), Toast.LENGTH_SHORT).show();
            network();
        }
    }


    //private static final String TAG = "RetrieveAccessToken";
    private static final int REQ_SIGN_IN_REQUIRED = 55664;

    private String mAccountName;

}
