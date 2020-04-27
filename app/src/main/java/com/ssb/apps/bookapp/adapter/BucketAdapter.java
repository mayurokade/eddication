package com.ssb.apps.bookapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.databinding.BucketListItemBinding;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.utils.IOUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.MyViewHolder> {

    public static final String TAG = BookListAdapter.class.getName();
    private LayoutInflater layoutInflater;
    private Context mcontext;
    List<DashboardResModel.BookData> list;
    private String imagePath = null;
    private final OnItemClickListener listener;

    public BucketAdapter(FragmentActivity activity, List<DashboardResModel.BookData> latestData, int i, String bookImagePath, OnItemClickListener listener) {
        this.list = latestData;
        this.imagePath = bookImagePath;
        this.mcontext = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        BucketListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.bucket_list_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (list.size() > 0) {
            holder.bucketListItemBinding.setBucketData(list.get(position));
            IOUtils.loadImage(mcontext, holder.bucketListItemBinding.ivBanner, imagePath + "/" + list.get(position).getBookCoverImage());
            holder.bucketListItemBinding.ratingBar.setCount(list.get(position).getAvgRating() == null ? 0 : Integer.parseInt(list.get(position).getAvgRating()));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        BucketListItemBinding bucketListItemBinding;

        public MyViewHolder(@NonNull BucketListItemBinding itemView) {
            super(itemView.getRoot());
            this.bucketListItemBinding = itemView;
            itemView.ivDelete.setOnClickListener(v->{
                listener.onItemClick(list.get(getAdapterPosition()),getAdapterPosition());
            });
        }

        @Override
        public void onClick(View v) {
            itemView.setOnClickListener(this);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DashboardResModel.BookData item, int position);
    }
}
