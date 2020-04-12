package com.ssb.apps.bookapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.databinding.ItemCategoryBinding;
import com.ssb.apps.bookapp.fragments.FragmentBookDetails;
import com.ssb.apps.bookapp.model.CategotryResModel;
import com.ssb.apps.bookapp.model.DashboardResModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    public static final String TAG = CategoryListAdapter.class.getName();
    private LayoutInflater layoutInflater;
    private Context mcontext;
    List<CategotryResModel.CategoryDatum> list;
    String imagePath= "";

    public CategoryListAdapter(FragmentActivity activity, List<CategotryResModel.CategoryDatum> latestData) {
        this.list = latestData;
        this.mcontext =activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCategoryBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_category, parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(list.size()>0){
            holder.itemCategoryBinding.setCategoryItem(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list == null?0:list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding itemCategoryBinding;
        public ViewHolder(@NonNull ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            this.itemCategoryBinding = itemView;
            itemView.getRoot().setOnClickListener(v->{
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",list.get(getAdapterPosition()) );
                FragmentBookDetails fragment = new FragmentBookDetails();
                fragment.setArguments(bundle);
                ((MainActivity) mcontext).loadFragment(fragment);
            });
        }
    }
}
