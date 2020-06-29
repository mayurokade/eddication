package com.ssb.apps.bookapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.databinding.FramgnetAutherProfileBinding;
import com.ssb.apps.bookapp.model.ProfileModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FramgnetAutherProfile extends Fragment {
    FramgnetAutherProfileBinding binding;
    ApiInterface apiService;
    ProfileModel data = new ProfileModel();

    public FramgnetAutherProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.framgnet_auther_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        //
    }
    private void init() {
        ((MainActivity) getActivity()).relSearch.setVisibility(View.INVISIBLE);
        data = (ProfileModel) getArguments().getSerializable("data");
        apiService = ApiClient.getClient().create(ApiInterface.class);

    }

}
