package com.figsinc.app.learn.theme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.figsinc.app.R;
import com.figsinc.app.learn.news.NewsActivity;

import java.util.List;

import static com.figsinc.app.learn.news.NewsActivity.KEY_NEWS_KEYWORD;
import static com.figsinc.app.learn.sector.SectorDetailsSubActivity.KEY_COLORCODE;
import static com.figsinc.app.learn.sector.SectorDetailsSubActivity.KEY_POSITION;
import static com.figsinc.app.learn.sector.SectorDetailsSubActivity.KEY_SECTOR_ID;
import static com.figsinc.app.learn.sector.SectorDetailsSubActivity.KEY_TITLE;

public class ThemeStaticListAdapter extends RecyclerView.Adapter<ThemeStaticListAdapter.MyViewHolder> {

    private List<String> filterList;
    private Context context;
    private String SectorID;
    private String keyword;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView filterItem;
        public LinearLayout linearParent;

        public MyViewHolder(View view) {
            super(view);
            filterItem = (TextView) view.findViewById(R.id.entities_filter_item);
            linearParent = (LinearLayout)view.findViewById(R.id.linearParent);
        }
    }

    public ThemeStaticListAdapter(List<String> sectorList, Context context, final String SectorID, final String keyword) {
        this.filterList = sectorList;
        this.context = context;
        this.SectorID=SectorID;
        this.keyword = keyword;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.small_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.filterItem.setText(filterList.get(position));

        holder.linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (position){
                    case 0:
                        Intent intent = new Intent(context, ThemeDetailsSubActivity.class);
                        intent.putExtra(KEY_SECTOR_ID,SectorID);
                        intent.putExtra(KEY_TITLE,keyword);
                        intent.putExtra(KEY_POSITION,0);
                        intent.putExtra(KEY_COLORCODE,R.color.iris);
                        context.startActivity(intent);
                        break;

                   /* case 1:
                        Intent intent2 = new Intent(context, ThemeDetailsSubActivity.class);
                        intent2.putExtra(KEY_SECTOR_ID,SectorID);
                        intent2.putExtra(KEY_TITLE,keyword);
                        intent2.putExtra(KEY_POSITION,1);
                        intent2.putExtra(KEY_COLORCODE,R.color.paleGrey);
                        context.startActivity(intent2);
                        break;
*/
                    case 1:
                        Intent intent3 = new Intent(context, ThemeDetailsSubActivity.class);
                        intent3.putExtra(KEY_SECTOR_ID,SectorID);
                        intent3.putExtra(KEY_TITLE,"Other Ideas");
                        intent3.putExtra(KEY_POSITION,2);
                        intent3.putExtra(KEY_COLORCODE,R.color.iris);
                        context.startActivity(intent3);
                        break;
                    case 2:
                        Intent intent4 = new Intent(context, NewsActivity.class);
                        intent4.putExtra(KEY_NEWS_KEYWORD,keyword);
                        intent4.putExtra(KEY_COLORCODE,R.color.iris);
                        context.startActivity(intent4);
                        break;

                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return filterList.size();
    }


}


