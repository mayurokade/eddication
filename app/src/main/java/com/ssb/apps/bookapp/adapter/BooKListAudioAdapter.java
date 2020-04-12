package com.ssb.apps.bookapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.databinding.ItemBookAudioBinding;
import com.ssb.apps.bookapp.databinding.ItemBookListBinding;
import com.ssb.apps.bookapp.model.FilesModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BooKListAudioAdapter extends RecyclerView.Adapter<BooKListAudioAdapter.MyViewHolder> {

    public static final String TAG = BookListAdapter.class.getName();
    private LayoutInflater layoutInflater;
    private Context mcontext;
    List<FilesModel> list;

    public BooKListAudioAdapter(Activity activity, List<FilesModel> meetingModelList) {
        this.mcontext = activity;
        this.list = meetingModelList;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemBookAudioBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_book_audio, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
holder.itemBookAudioBinding.chapter.setText("Chapter "+(position+1));
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemBookAudioBinding itemBookAudioBinding;
        public MyViewHolder(@NonNull ItemBookAudioBinding itemView) {
            super(itemView.getRoot());
            this.itemBookAudioBinding = itemView;
        }
    }
}
