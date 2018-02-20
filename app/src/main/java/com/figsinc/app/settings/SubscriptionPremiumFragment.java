package com.figsinc.app.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.figsinc.app.R;


public class SubscriptionPremiumFragment extends Fragment {


    public SubscriptionPremiumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscription_premium, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CharSequence cs;
        cs = getText(R.string.text_subscription_Premium_theme_access);
        AppCompatTextView text1 = (AppCompatTextView)getActivity().findViewById(R.id.text1);
        text1.setText(cs);


        cs = getText(R.string.text_subscription_Premium_calc_access);
        AppCompatTextView text2 = (AppCompatTextView)getActivity().findViewById(R.id.text2);
        text2.setText(cs);

    }


}
