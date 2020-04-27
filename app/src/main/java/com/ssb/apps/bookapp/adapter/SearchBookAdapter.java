package com.ssb.apps.bookapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.model.DashboardResModel;

import java.util.ArrayList;
import java.util.List;

public class SearchBookAdapter extends ArrayAdapter<DashboardResModel.BookData> {

    Context context;
    int resource, textViewResourceId;
    List<DashboardResModel.BookData> items, tempItems, suggestions;

    public SearchBookAdapter(Context context, int resource, int textViewResourceId, List<DashboardResModel.BookData> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<DashboardResModel.BookData>(items); // this makes the difference.
        suggestions = new ArrayList<DashboardResModel.BookData>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_item, parent, false);
        }
        DashboardResModel.BookData people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getBookName());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((DashboardResModel.BookData) resultValue).getBookName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (DashboardResModel.BookData people : tempItems) {
                    if (people.getBookName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<DashboardResModel.BookData> filterList = (ArrayList<DashboardResModel.BookData>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (DashboardResModel.BookData people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}


