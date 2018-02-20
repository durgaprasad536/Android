package com.figsinc.app.netdhania;

import android.support.v4.app.FragmentActivity;

import com.figsinc.app.FigsApplication;

public class BaseActivity extends FragmentActivity {

	protected FigsApplication getBaseApplication() {
		return (FigsApplication) getApplication();
	}

}
