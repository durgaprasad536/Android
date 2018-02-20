package com.figsinc.app.onboarding;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.figsinc.app.R;


public class OnboardFragmentThree extends Fragment {


    public OnboardFragmentThree() {
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
        return inflater.inflate(R.layout.onboarding_three, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) getActivity(). findViewById(R.id.tvHeaderThree);

        Spannable word = new SpannableString(getResources().getString(R.string.onboardingThree1));
        word.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.onboarding_sky_blue)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(word);
        textView.append(" ");

        Spannable wordTwo = new SpannableString(getResources().getString(R.string.onboardingThree2));
        wordTwo.setSpan(new ForegroundColorSpan(Color.WHITE), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordTwo);
        textView.append(" ");

        Spannable wordThree = new SpannableString(getResources().getString(R.string.onboardingThree));
        wordThree.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.onboarding_sky_blue)), 0, wordThree.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordThree);
    }


}
