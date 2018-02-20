package com.figsinc.app.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.analyze.AnalyzeActivity;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.gson.JsonObject;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.editTextEmail)
    EditText email;
    @BindView(R.id.editTextPassword)
    EditText password;

    @BindView(R.id.buttonLogIn)
    Button buttonLogIn;

    @BindView(R.id.fabric_button_signupWithTwitter)@Nullable
    TwitterLoginButton fabric_button_signupWithTwitter;

    @BindView(R.id.button_signupWithTwitter)@Nullable
    Button button_signupWithTwitter;

    @BindView(R.id.button_signupWithFacebook)@Nullable
    Button button_signupWithFacebook;

    @BindView(R.id.button_signupWithGoogle)@Nullable
    Button button_signupWithGoogle;
    boolean isReskin=true;
    private boolean isFacebook = false;
    private boolean isTwitter = false;

    CallbackManager callbackManager;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private String accessToken;
    private String provider;
    private String accessTokenSecret;

    private static final int RC_SIGN_IN = 007;
    private static final String TAG = MainActivity.class.getSimpleName();

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(email.getText().toString().trim().length()>0 & password.getText().toString().trim().length()>0){
                buttonLogIn.setEnabled(true);
            }
            else{
                buttonLogIn.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void twitterConfig() {
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("9HxDS8tstuL0XGBwiizNixqJi", "eWjpHvxVxefAWFUsuWWWwi9BJSkLIKFtHIJJZ097R8C5jrpH89"))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reskin);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // new Download().execute();
        buttonLogIn.setEnabled(false);
        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        if (Constants.isDebug) {
            email.setText("rez@figs.inc");
            password.setText("rez");
            network();
        }

        if(isReskin){

            EnableTwitterHandlingListeners();

            EnableFBHandlingListener();
            EnableGooglePlusHandlersListeners();
        }

    }

    private void EnableGooglePlusHandlersListeners() {

        button_signupWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gplusSign(view);
            }
        });

        // Google plus signing...
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void EnableFBHandlingListener() {
        button_signupWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLogin(view);
            }
        });
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
                                    /** This block obtains Facebook UserID and Token */
                                    AccessToken token = AccessToken.getCurrentAccessToken();
                                    if (token != null) {
                                        // Toast.makeText(getApplicationContext(), token.toString(), Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "Token: " + token.getToken());
                                        Log.e(TAG, "UserID: " + token.getUserId());
                                        accessToken =token.getToken();
                                        provider = "facebook";
                                        networkSocial();
                                    }
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
    }

    private void EnableTwitterHandlingListeners() {
        twitterConfig();
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
                String Username = result.data.getUserName();
              //  Toast.makeText(LoginActivity.this, Username, Toast.LENGTH_LONG).show();
                accessToken = result.data.getAuthToken().token.toString();
                provider = "twitter";
                accessTokenSecret = result.data.getAuthToken().secret.toString();
                isTwitter = true;

                networkSocial();
            }

            @Override
            public void failure(TwitterException exception) {
                // Upon error, show a toast message indicating that authorization request failed.
                Toast.makeText(getApplicationContext(), exception.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.textView_ForgetPassword)
    public void submit(View view) {
        // TODO Navigation...
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonLogIn)
    public void login(View view) {
        // TODO Navigation...
        if (!isValidEmail(email.getText().toString().trim())) {
            email.setError("Please enter valid email");
            email.requestFocus();
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError("Please enter password");
            password.requestFocus();
        } else {
            network();
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

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", email.getText().toString().trim());
            jsonBody.put("password", password.getText().toString().trim());
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.login,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                             // Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject token = new JSONObject(response);
                                String auth_token = token.getString("auth_token");

                                SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("auth_token", auth_token);
                                editor.commit();

                                Intent intent = new Intent(LoginActivity.this, AnalyzeActivity.class);
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
                   // Toast.makeText(LoginActivity.this, "test tes test ", Toast.LENGTH_SHORT).show();
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        // {"non_field_errors":["Unable to login with provided credentials."]}
                        try {
                            JSONObject jsonObject = new JSONObject(jsonError);
                            // System.out.println(" jsonError ==>>> " + jsonObject.getJSONArray("non_field_errors").getString(0));
                            jsonError = jsonObject.getJSONArray("non_field_errors").getString(0);
                            Toast.makeText(LoginActivity.this, " " + jsonError, Toast.LENGTH_SHORT).show();
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

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    //@OnClick(R.id.button_signupWithFacebook)@Optional
    public void facebookLogin(View view) {
        isFacebook = true;
        isTwitter = false;
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
    }

    //@OnClick(R.id.button_signupWithGoogle)@Optional
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
        }
        else // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
        }
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

            // txtName.setText(personName);
            // txtEmail.setText(email);
           /* Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .into(imgProfilePic);*/
            hideProgressDialog();
            new RetrieveTokenTask().execute(email);
            networkSocial();
        } else {
            // Signed out, show unauthenticated UI.
            // Toast.makeText(this, "There is some problem with signing in... Please try again later...", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "There is some problem with signing in... Please try again later...", Toast.LENGTH_SHORT).show();
            hideProgressDialog();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if(mGoogleApiClient!=null) {
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
        }
    }

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


    private void networkSocial() {
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
                            //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                             try {
                                JSONObject token = new JSONObject(response);
                                String auth_token = token.getString("token");

                                SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("auth_token", auth_token);
                                editor.commit();

                                Intent intent = new Intent(LoginActivity.this, AnalyzeActivity.class);
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

                    // Toast.makeText(SocialMediaActivity.this, " Error ", Toast.LENGTH_SHORT).show();

                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);


                        // {"non_field_errors":["Unable to login with provided credentials."]}
                        try {
                            JSONObject jsonObject = new JSONObject(jsonError);
                            // System.out.println(" jsonError ==>>> " + jsonObject.getJSONArray("non_field_errors").getString(0));
                            jsonError = jsonObject.getJSONArray("non_field_errors").getString(0);
                            Toast.makeText(LoginActivity.this, " " + jsonError, Toast.LENGTH_SHORT).show();
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
