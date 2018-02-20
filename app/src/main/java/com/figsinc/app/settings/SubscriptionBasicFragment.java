package com.figsinc.app.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.figsinc.app.R;


public class SubscriptionBasicFragment extends Fragment {


    public SubscriptionBasicFragment() {
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
        return inflater.inflate(R.layout.fragment_subscription_free, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* ImageView imageView = (ImageView)getActivity().findViewById(R.id.imageView);
        TextView tvHeader = (TextView)getActivity().findViewById(R.id.tvHeader);
        tvHeader.setText(getResources().getString(R.string.onboardingFour));
        imageView.setBackground(ContextCompat.getDrawable(getActivity(),R.mipmap.onboarding3));*/

    }


}
