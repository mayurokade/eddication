package com.ssb.apps.bookapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.databinding.ItemCategoryListBinding;
import com.ssb.apps.bookapp.fragments.FragmentBookDetails;
import com.ssb.apps.bookapp.fragments.FragmentBookInfo;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.utils.IOUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListDetialAdapter extends RecyclerView.Adapter<CategoryListDetialAdapter.MyViewHolder> {
    public static final String TAG = BookListAdapter.class.getName();
    private LayoutInflater layoutInflater;
    private Context mcontext;
    List<DashboardResModel.BookData> list;
    private String imagePath = null;

    public CategoryListDetialAdapter(FragmentActivity activity, List<DashboardResModel.BookData> latestData, int i, String bookImagePath) {
        this.list = latestData;
        this.imagePath = bookImagePath;
        this.mcontext =activity;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCategoryListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_category_list, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(list.size()>0){
            holder.itemCategoryListBinding.setBookDetails(list.get(position));
            IOUtils.loadImage(mcontext,holder.itemCategoryListBinding.itemImage, imagePath+"/"+list.get(position).getBookCoverImage());

            holder.itemCategoryListBinding.ratingBar.setCount(list.get(position).getTotalRatings() == null? 0:Integer.parseInt(list.get(position).getTotalRatings()));
        }
    }

    @Override
    public int getItemCount() {
        return list== null?0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryListBinding itemCategoryListBinding;
        public MyViewHolder(@NonNull ItemCategoryListBinding itemView) {
            super(itemView.getRoot());
            this.itemCategoryListBinding = itemView;
            itemView.getRoot().setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",list.get(getAdapterPosition()) );
                FragmentBookInfo fragment = new FragmentBookInfo();
                fragment.setArguments(bundle);
                ((MainActivity) mcontext).loadFragment(fragment);
            });
        }
    }
}
