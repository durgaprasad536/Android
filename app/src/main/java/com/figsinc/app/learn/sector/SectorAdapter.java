package com.figsinc.app.learn.sector;

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
import com.figsinc.app.learn.Model.Sector;
import com.figsinc.app.learn.news.NewsActivity;
import com.figsinc.app.learn.sector.pdf.DownloadTask;
import com.figsinc.app.utils.CircularProgressBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.figsinc.app.learn.news.NewsActivity.KEY_NEWS_KEYWORD;
import static com.figsinc.app.learn.sector.SectorDetailsSubActivity.KEY_COLORCODE;

public class SectorAdapter extends RecyclerView.Adapter<SectorAdapter.MyViewHolder> {

    private List<Sector> SectorList;
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
            imageview_logo = (ImageView) view.findViewById(R.id.imageview_logo);
            button_info = (ImageView) view.findViewById(R.id.button_info);
            button_video = (ImageView) view.findViewById(R.id.button_video);
            button_infographic = (ImageView) view.findViewById(R.id.button_infographic);
            button_report = (ImageView) view.findViewById(R.id.button_report);
            textView_subtitle = (TextView) view.findViewById(R.id.textView_subtitle);
            Building = (TextView) view.findViewById(R.id.title);
            circular_progress_bar = (CircularProgressBar)view.findViewById(R.id.circular_progress_bar);
            relative_topSection = (RelativeLayout)view.findViewById(R.id.relative_topSection);
        }
    }


    public SectorAdapter(List<Sector> sectorList, Activity context) {
        this.SectorList = sectorList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.learn_secor_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

         // holder.circular_progress_bar.setColor(ContextCompat.getColor(this, R.color.progressBarColor));
        // holder.circular_progress_bar.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundProgressBarColor));
       // holder.circular_progress_bar.setProgressBarWidth(context.getResources().getDimension(R.dimen.progressBarWidth));
       // holder.circular_progress_bar.setBackgroundProgressBarWidth(context.getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        int animationDuration = 2500; // 2500ms = 2,5s
        holder.circular_progress_bar.setProgressWithAnimation(Math.round(Float.parseFloat(SectorList.get(position).getPotential_returns())), animationDuration); // Default duration = 1500ms


        String url = Constants.sectorImageUrl +SectorList.get(position).getImage_ios() +".jpeg";
        Glide.with(context).load(url).into(holder.imageview_logo);
        double potentialReturns = Double.parseDouble(SectorList.get(position).getPotential_returns());
        holder.progress_count.setText(String.format("%.2f", new BigDecimal(potentialReturns))+"%");
        holder.textView_title.setText(SectorList.get(position).getName());
        holder.textView_subtitle.setText(SectorList.get(position).getCategory_name());

        holder.relative_topSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,SectorDetailsActivity.class);
                intent.putExtra("id",SectorList.get(position).getId());
                intent.putExtra("imageUrl", Constants.sectorImageUrl);
                intent.putExtra("apiUrl", Constants.sectorDetails);
                intent.putExtra("colorCode",R.color.tealish);

                context.startActivity(intent);
            }
        });

       holder.button_info.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(context,NewsActivity.class);
               intent.putExtra(KEY_NEWS_KEYWORD,SectorList.get(position).getCategory_name());
               intent.putExtra(KEY_COLORCODE,R.color.tealish);
               context.startActivity(intent);
           }
       });

      //  http://d1z4o3o2zsjyg.cloudfront.net/Sector/Reports/Eng/Rep_S011_ISP_Eng_2.pdf
       // http://d1z4o3o2zsjyg.cloudfront.net/Sector/Reports/Eng/Rep_S011_ISP_Eng_2.pdf

        holder.button_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,VideoViewActivity.class);
                intent.putExtra("VIDEO_URL",SectorList.get(position).getSector_video());
                intent.putExtra(KEY_COLORCODE,R.color.tealish);
                context.startActivity(intent);
            }
        });

        holder.button_infographic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context,PdfViewActivity.class);
                intent.putExtra("PDF_URL",SectorList.get(position).getInfographics_pdf());
                System.out.println("======== " + SectorList.get(position).getInfographics_pdf());
                context.startActivity(intent);*/
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermissions())
                        new DownloadTask(context,SectorList.get(position).getInfographics_pdf());
                } else {
                    new DownloadTask(context,SectorList.get(position).getInfographics_pdf());
                    // Code for Below 23 API Oriented Device
                    // Do next code
                }

            }
        });

        holder.button_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context,PdfViewActivity.class);
                intent.putExtra("PDF_URL",SectorList.get(position).getIndustry_report());
                System.out.println("======== " + SectorList.get(position).getIndustry_report());
                context.startActivity(intent);*/
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermissions())
                        new DownloadTask(context,SectorList.get(position).getIndustry_report());
                } else {
                    new DownloadTask(context,SectorList.get(position).getIndustry_report());
                    // Code for Below 23 API Oriented Device
                    // Do next code
                }
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
        return SectorList.size();
    }


}


