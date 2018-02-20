package com.figsinc.app.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.zendesk.sdk.model.access.AnonymousIdentity;
import com.zendesk.sdk.model.access.Identity;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zendesk.sdk.support.SupportActivity;

public class SupportCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_center);
        Constants.setStatusBar(this, R.color.darkish_blue);
        ZendeskConfig.INSTANCE.init(this, "https://figsinc.zendesk.com", "354016f7c3aea26b96ec30bf91cb5d57629396ad3e45159a", "mobile_sdk_client_0f31862ab51950b59827");
        Identity identity = new AnonymousIdentity.Builder().build();
        ZendeskConfig.INSTANCE.setIdentity(identity);
        new SupportActivity.Builder().show(SupportCenterActivity.this);
        finish();

    }

}
