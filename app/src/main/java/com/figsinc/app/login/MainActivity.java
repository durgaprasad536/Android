package com.figsinc.app.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.figsinc.app.FigsApplication;
import com.figsinc.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView_Signin)
    TextView textView_Signin;
    @BindView(R.id.welcomeText)
    TextView welcomeText;
    FigsApplication figsApplication;
  /*  @BindView(R.id.imageview_logo)
    ImageView imageview_logo;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        figsApplication = (FigsApplication) getApplication();
        figsApplication.setBoldFont(textView_Signin);
        figsApplication.setSemiBoldFont(welcomeText);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      //  Glide.with(this).load("https://cdn-images-1.medium.com/max/1600/1*aGYj-Xcdb8ggwS5s_JceOw.gif").into(imageview_logo);


    }

    @OnClick(R.id.buttonMoreOptions)
    public void submit(View view) {
        // TODO Navigation...
        Intent intent = new Intent(MainActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.textView_Signin)
    public void signIn(View view) {
        // TODO Navigation...
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_signupWithEmail)
    public void signUp(View view) {
        // TODO Navigation...
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
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

}
