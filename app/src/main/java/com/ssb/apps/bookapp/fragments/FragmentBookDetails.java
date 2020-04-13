package com.ssb.apps.bookapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.adapter.BooKListAudioAdapter;
import com.ssb.apps.bookapp.databinding.FragmentBookDetailBinding;
import com.ssb.apps.bookapp.model.BookInfoModel;
import com.ssb.apps.bookapp.utils.IOUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class FragmentBookDetails extends Fragment {
    FragmentBookDetailBinding binding;
    BookInfoModel data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        data = (BookInfoModel) getArguments().getSerializable("data");
        binding.rvListAudio.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (data.getChapterInfo().getChapterData() != null && data.getChapterInfo().getChapterData().size() > 0) {
            binding.rvListAudio.setAdapter(new BooKListAudioAdapter(getActivity(), data.getChapterInfo().getChapterData(), data.getChapterInfo().getChapterAudioPath()));
        }else{
            IOUtils.showAlertDialog(getActivity(),getActivity().getString(R.string.alert),getActivity().getString(R.string.show));
        }

    }

}
