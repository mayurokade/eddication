package com.ssb.apps.bookapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.databinding.ItemReviewLayoutBinding;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.model.ReviewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    public static final String TAG = ReviewAdapter.class.getName();
    private LayoutInflater layoutInflater;
    private Context mcontext;
    List<ReviewModel.BookReviewDatum> list;
    private String imagePath = null;


    public ReviewAdapter(FragmentActivity activity, List<ReviewModel.BookReviewDatum> latestData, int i, String bookImagePath) {
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
        ItemReviewLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_review_layout,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(list.size()>0){
            holder.itemReviewLayoutBinding.setData(list.get(position));
            holder.itemReviewLayoutBinding.ratingBar.setCount(list.get(position).getCommentReview()== null?
                    0:Integer.parseInt(list.get(position).getCommentReview()));

        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemReviewLayoutBinding itemReviewLayoutBinding;
        public MyViewHolder(@NonNull ItemReviewLayoutBinding itemView) {
            super(itemView.getRoot());
            this.itemReviewLayoutBinding = itemView;
        }
    }
}
