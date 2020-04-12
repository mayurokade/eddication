package com.ssb.apps.bookapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.ItemBookListBinding;
import com.ssb.apps.bookapp.fragments.FragmentBookDetails;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {
    public static final String TAG = BookListAdapter.class.getName();
    private LayoutInflater layoutInflater;
    private Context mcontext;
    List<DashboardResModel.BookData> list;
    private String imagePath = null;


    public BookListAdapter(FragmentActivity activity, List<DashboardResModel.BookData> latestData, int i, String bookImagePath) {
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
        ItemBookListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_book_list, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(list.size()>0){
            holder.itemBookListBinding.setBookDetails(list.get(position));
            IOUtils.loadImage(mcontext,holder.itemBookListBinding.itemImage, imagePath+"/"+list.get(position).getBookCoverImage());
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemBookListBinding itemBookListBinding;

        public MyViewHolder(@NonNull ItemBookListBinding itemView) {
            super(itemView.getRoot());
            this.itemBookListBinding = itemView;
            itemView.getRoot().setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",list.get(getAdapterPosition()) );
                FragmentBookDetails fragment = new FragmentBookDetails();
                 fragment.setArguments(bundle);
                ((MainActivity) mcontext).loadFragment(fragment);
            });

        }
    }
}
