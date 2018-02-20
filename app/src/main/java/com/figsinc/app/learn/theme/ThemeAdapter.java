package com.figsinc.app.learn.theme;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.learn.Model.Theme;
import com.figsinc.app.learn.news.NewsActivity;
import com.figsinc.app.learn.sector.PdfViewActivity;
import com.figsinc.app.learn.sector.VideoViewActivity;
import com.figsinc.app.learn.sector.pdf.DownloadTask;
import com.figsinc.app.utils.CircularProgressBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.figsinc.app.learn.news.NewsActivity.KEY_NEWS_KEYWORD;
import static com.figsinc.app.learn.sector.SectorDetailsSubActivity.KEY_COLORCODE;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.MyViewHolder> {

    private ArrayList<Theme> themeArrayList;
    private Activity context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Building;
        public TextView textView_title,textView_subtitle,progress_count;
        public ImageView imageview_logo,button_video,button_infographic,button_report,button_info;

        public CircularProgressBar circular_progress_bar;
        public RelativeLayout relative_topSection;

        public MyViewHolder(View view) {
            super(view);
            textView_title = (TextView) view.findViewById(R.id.textView_title);
            progress_count = (TextView) view.findViewById(R.id.progress_count);
            button_info = (ImageView) view.findViewById(R.id.button_info);
            imageview_logo = (ImageView) view.findViewById(R.id.imageview_logo);
            button_video = (ImageView) view.findViewById(R.id.button_video);
            button_infographic = (ImageView) view.findViewById(R.id.button_infographic);
            button_report = (ImageView) view.findViewById(R.id.button_report);
            textView_subtitle = (TextView) view.findViewById(R.id.textView_subtitle);
            Building = (TextView) view.findViewById(R.id.title);
            circular_progress_bar = (CircularProgressBar)view.findViewById(R.id.circular_progress_bar);
            relative_topSection = (RelativeLayout)view.findViewById(R.id.relative_topSection);
        }
    }


    public ThemeAdapter(ArrayList<Theme> themeArrayList, Activity context) {
        this.themeArrayList = themeArrayList;
        this.context = context;
        System.out.println(" themeArrayList  1" + themeArrayList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.learn_secor_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        System.out.println(" themeArrayList position " + position);

         holder.circular_progress_bar.setColor(ContextCompat.getColor(context, R.color.iris));
        // holder.circular_progress_bar.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundProgressBarColor));
        // holder.circular_progress_bar.setProgressBarWidth(context.getResources().getDimension(R.dimen.progressBarWidth));
        // holder.circular_progress_bar.setBackgroundProgressBarWidth(context.getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        int animationDuration = 2500; // 2500ms = 2,5s
        holder.circular_progress_bar.setProgressWithAnimation(Math.round(Float.parseFloat(themeArrayList.get(position).getPotential_returns())), animationDuration); // Default duration = 1500ms


        String url = Constants.thematicsImageUrl + themeArrayList.get(position).getThematics_ios() +".jpeg";
        Glide.with(context).load(url).into(holder.imageview_logo);

        holder.progress_count.setTextColor(ContextCompat.getColor(context,R.color.iris));

        double potentialReturns = Double.parseDouble(themeArrayList.get(position).getPotential_returns());
        holder.progress_count.setText(String.format("%.2f", new BigDecimal(potentialReturns))+"%");

       // holder.progress_count.setText(Math.round(Float.parseFloat(themeArrayList.get(position).getPotential_returns())) + "%");
        holder.textView_title.setText(themeArrayList.get(position).getName());
        holder.textView_subtitle.setText(themeArrayList.get(position).getCategory_name());

        holder.relative_topSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,ThemeProfileActivity.class);
                intent.putExtra("id", themeArrayList.get(position).getId());
                intent.putExtra("imageUrl", Constants.thematicsImageUrl);
                intent.putExtra("apiUrl", Constants.thematicsDetails);
                intent.putExtra("colorCode",R.color.iris);
                context.startActivity(intent);
            }
        });

       holder.button_video.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(context,VideoViewActivity.class);
               intent.putExtra("VIDEO_URL", themeArrayList.get(position).getThematics_video());
               intent.putExtra("COLOR_CODE",R.color.iris);
               System.out.println("======== " + themeArrayList.get(position).getThematics_video());
               context.startActivity(intent);
           }
       });


        holder.button_infographic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(context,PdfViewActivity.class);
                intent.putExtra("PDF_URL", themeArrayList.get(position).getInfographics_pdf());
                System.out.println("======== " + themeArrayList.get(position).getInfographics_pdf());
                intent.putExtra("COLOR_CODE",R.color.iris);
                context.startActivity(intent);*/

                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermissions())
                        new DownloadTask(context,themeArrayList.get(position).getInfographics_pdf());
                } else {
                    new DownloadTask(context,themeArrayList.get(position).getInfographics_pdf());
                    // Code for Below 23 API Oriented Device
                    // Do next code
                }

            }
        });

        holder.button_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(context,PdfViewActivity.class);
                intent.putExtra("PDF_URL", themeArrayList.get(position).getThematics_report_pdf());
                intent.putExtra("COLOR_CODE",R.color.iris);
                System.out.println("======== " + themeArrayList.get(position).getThematics_report_pdf());
                context.startActivity(intent);*/
                // new DownloadTask(context,themeArrayList.get(position).getThematics_report_pdf());

                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermissions())
                        new DownloadTask(context,themeArrayList.get(position).getThematics_report_pdf());
                } else {
                    new DownloadTask(context,themeArrayList.get(position).getThematics_report_pdf());
                    // Code for Below 23 API Oriented Device
                    // Do next code
                }
            }
        });

        holder.button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,NewsActivity.class);
                intent.putExtra(KEY_NEWS_KEYWORD, themeArrayList.get(position).getCategory_name());
                intent.putExtra(KEY_COLORCODE,R.color.iris);
                context.startActivity(intent);
            }
        });

    }

    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(context, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return themeArrayList.size();
    }


}


