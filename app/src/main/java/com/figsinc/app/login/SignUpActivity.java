package com.figsinc.app.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.learn.news.NewsDetailActivity;
import com.figsinc.app.utils.CountryCodes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.editTextFirstName)
    EditText editTextFirstName;
    @BindView(R.id.editTextLastName)
    EditText editTextLastName;
    @BindView(R.id.editTextEmailAddress)
    EditText editTextEmailAddress;
    @BindView(R.id.spinner_SelectCountry)
    Spinner spinner_SelectCountry;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editTextConfirmPassword)
    EditText editTextConfirmPassword;
    @BindView(R.id.buttonCreate)
    Button buttonCreate;

    @BindView(R.id.checkbox_agree)
    AppCompatCheckBox checkbox_agree;

    @BindView(R.id.checkbox_promotional)
    AppCompatCheckBox checkbox_promotional;

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            enableSubmitButton();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Constants.setStatusBar(this, R.color.darkish_blue);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array, R.layout.android_item_spinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_SelectCountry.setAdapter(adapter);

        editTextFirstName.addTextChangedListener(textWatcher);
        editTextLastName.addTextChangedListener(textWatcher);
        editTextEmailAddress.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);
        editTextConfirmPassword.addTextChangedListener(textWatcher);
        checkbox_agree.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        checkbox_promotional.setOnCheckedChangeListener(new myCheckBoxChnageClicker());


        spinner_SelectCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                enableSubmitButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @OnClick(R.id.textView_terms_of_service)
    public void termsOfService() {
        Intent intent = new Intent(SignUpActivity.this, WebViewActivity.class);
        intent.putExtra("DetailsUrl", "https://support.figsinc.com/hc/en-us/sections/115000772874-Terms-of-Service");
        intent.putExtra("Title", "Terms of Service");
        startActivity(intent);
    }

    @OnClick(R.id.signup_Privacy_Policy)
    public void privacyPolicy() {
        Intent intent = new Intent(SignUpActivity.this, WebViewActivity.class);
        intent.putExtra("DetailsUrl", "https://support.figsinc.com/hc/en-us/sections/115000794694-Privacy-Policy");
        intent.putExtra("Title", "Privacy Policy");
        startActivity(intent);
    }

    @OnClick(R.id.buttonCreate)
    public void login(View view) {
        // TODO Navigation...

        if (TextUtils.isEmpty(editTextFirstName.getText().toString().trim())) {
            editTextFirstName.setError("Please enter FirstName");
            editTextFirstName.requestFocus();
        } else if (TextUtils.isEmpty(editTextLastName.getText().toString().trim())) {
            editTextLastName.setError("Please enter LastName");
            editTextLastName.requestFocus();
        } else if (!isValidEmail(editTextEmailAddress.getText().toString().trim())) {
            editTextEmailAddress.setError("Please enter valid email");
            editTextEmailAddress.requestFocus();
        } else if (TextUtils.isEmpty(editTextPassword.getText().toString().trim())) {
            editTextPassword.setError("Please enter password");
            editTextPassword.requestFocus();
        } else if (TextUtils.isEmpty(editTextConfirmPassword.getText().toString().trim())) {
            editTextConfirmPassword.setError("Please enter password");
            editTextConfirmPassword.requestFocus();
        } else if (spinner_SelectCountry.getSelectedItem().toString().trim().equals("Your Residence")) {
            Toast.makeText(this, "Please Select Country", Toast.LENGTH_SHORT).show();
        } else {
            if (isSubmitEnabled) {
                // network();
                Intent intent = new Intent(SignUpActivity.this, SignUponFIGSJapanActivity.class);
                startActivity(intent);
            }
        }

    }

    boolean isSubmitEnabled = false;

    private void enableSubmitButton() {
        if (TextUtils.isEmpty(editTextFirstName.getText().toString().trim())) {
            isSubmitEnabled = false;
        } else if (TextUtils.isEmpty(editTextLastName.getText().toString().trim())) {
            isSubmitEnabled = false;
        } else if (!isValidEmail(editTextEmailAddress.getText().toString().trim())) {
            isSubmitEnabled = false;
        } else if (TextUtils.isEmpty(editTextPassword.getText().toString().trim())||editTextPassword.getText().toString().trim().length()<8) {
            isSubmitEnabled = false;
        } else if (TextUtils.isEmpty(editTextConfirmPassword.getText().toString().trim())||editTextConfirmPassword.getText().toString().trim().length()<8) {
            isSubmitEnabled = false;
        } else if (spinner_SelectCountry.getSelectedItem().toString().trim().equals("Your Residence")) {
            isSubmitEnabled = false;
        }else if (!checkbox_agree.isChecked()) {
            isSubmitEnabled = false;
        }else if (!checkbox_promotional.isChecked()) {
            isSubmitEnabled = false;
        } else {
            isSubmitEnabled = true;
        }

        if (isSubmitEnabled) {
            buttonCreate.setBackgroundColor(getResources().getColor(R.color.darkish_blue));
        } else if (!isSubmitEnabled) {
            buttonCreate.setBackgroundColor(getResources().getColor(R.color.signin_disable));
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

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = Constants.register;

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("first_name", editTextFirstName.getText().toString().trim());
            jsonBody.put("last_name", editTextLastName.getText().toString().trim());
            jsonBody.put("email", editTextEmailAddress.getText().toString().trim());
            jsonBody.put("password", editTextPassword.getText().toString().trim());
            jsonBody.put("confirm_password", editTextPassword.getText().toString().trim());

            JSONObject rootJsonBody = new JSONObject();
            rootJsonBody.put("user", jsonBody);
            rootJsonBody.put("Country", Constants.getCountryCode(spinner_SelectCountry.getSelectedItem().toString().trim()));

            final String requestBody = rootJsonBody.toString();

            System.out.println(Constants.getCountryCode(spinner_SelectCountry.getSelectedItem().toString()) + "===>> " + requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject token = new JSONObject(response);
                                Toast.makeText(SignUpActivity.this, response + " Successfully Registered", Toast.LENGTH_SHORT).show();
                                // Try and catch are included to handle any errors due to JSON
                            } catch (JSONException e) {
                                // If an error occurs, this prints the error to the log
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Toast.makeText(SignUpActivity.this, "That didn't work!" + error, Toast.LENGTH_SHORT).show();
                    Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    return "application/json";
                }
            };

            requestQueue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    class myCheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub
            enableSubmitButton();
        }
    }
}
