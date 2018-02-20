package com.figsinc.app.settings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

import com.figsinc.app.R;
import com.figsinc.app.settings.Model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryRecyclerViewAdapter extends RecyclerView.Adapter<CountryRecyclerViewAdapter.DataObjectHolder> implements Filterable {

    ArrayList<Country> mData = new ArrayList<>();
    private ArrayList<Country> countryListFiltered = new ArrayList<>();
    private static SingleClickListener sClickListener;
    private static int sSelected = -1;

    public CountryRecyclerViewAdapter(ArrayList<Country> mData) {
        this.mData = mData;
        this.countryListFiltered = mData;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    countryListFiltered = mData;
                } else {
                    ArrayList<Country> filteredList = new ArrayList<>();
                    for (Country row : mData) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    countryListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = countryListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countryListFiltered = (ArrayList<Country>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;
        RadioButton mRadioButton;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.mTextView = (TextView) itemView.findViewById(R.id.entities_filter_item);
            this.mRadioButton = (RadioButton) itemView.findViewById(R.id.single_list_item_check_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            sSelected = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view);
        }
    }

    public String selectedItem() {
        System.out.println("  sSelected  ====>>>>" + sSelected);
        notifyDataSetChanged();
        String name = "";
        if (sSelected != -1) {
            name = countryListFiltered.get(sSelected).getIso();
        }
        return name;
    }

   /* protected void setFirstItem(){
        sSelected = 0;
        notifyDataSetChanged();
    }
*/
    void setOnItemClickListener(SingleClickListener clickListener) {
        sClickListener = clickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_list_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.mTextView.setText(countryListFiltered.get(position).getName());

        holder.mRadioButton.setChecked(sSelected == position);

    }

    @Override
    public int getItemCount() {
        return countryListFiltered.size();
    }

    interface SingleClickListener {
        void onItemClickListener(int position, View view);
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Country contact);
    }

}