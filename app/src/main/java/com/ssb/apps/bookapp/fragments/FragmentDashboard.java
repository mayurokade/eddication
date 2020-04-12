package com.ssb.apps.bookapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.ActivityLogin2;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.adapter.BookListAdapter;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.FragmentDashboardBinding;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.model.FilesModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDashboard extends Fragment {
    FragmentDashboardBinding binding;
    ApiInterface apiService;

    public FragmentDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        loadDashBorad();
    }

    private void init() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.rvLatestBooks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvListenersChoice.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvMiddle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvMoreBooks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.tvAllCategory.setOnClickListener(v->{
            FragmentCategory fragment = new FragmentCategory();
            ((MainActivity) getActivity()).loadFragment(fragment);
        });
    }

    private void loadDashBorad() {

        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());

            Call<DashboardResModel> call = apiService.getDashboardDetails( BookApp.cache.readString(getActivity(), Constant.USERID,""),
                    BookApp.cache.readString(getActivity(),Constant.TOKEN,""),"1");
            call.enqueue(new Callback<DashboardResModel>() {
                @Override
                public void onResponse(Call<DashboardResModel> call, Response<DashboardResModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {

                            if (response.body().getLatestBook().getLatestData() == null) {
                                binding.rvLatestBooks.setVisibility(View.GONE);
                            } else {
                                binding.rvLatestBooks.setVisibility(View.VISIBLE);
                            }

                            if (response.body().getListnerChoice().getListnerData() == null) {
                                binding.rvListenersChoice.setVisibility(View.GONE);
                            } else {
                                binding.rvListenersChoice.setVisibility(View.VISIBLE);
                            }

                            if (response.body().getMiddleOfSomething().getMiddleData() == null) {
                                binding.rvMiddle.setVisibility(View.GONE);
                            } else {
                                binding.rvMiddle.setVisibility(View.VISIBLE);
                            }
                            if (response.body().getMostRated().getMostRatedData() == null) {
                                binding.rvMoreBooks.setVisibility(View.GONE);
                            } else {
                                binding.rvMoreBooks.setVisibility(View.VISIBLE);
                            }
                            binding.rvLatestBooks.setAdapter(new BookListAdapter(getActivity(), response.body().getLatestBook().getLatestData(),1,response.body().getLatestBook().getBookImagePath()));
                            binding.rvListenersChoice.setAdapter(new BookListAdapter(getActivity(),response.body().getListnerChoice().getListnerData(),2 ,response.body().getListnerChoice().getBookImagePath()));
                            binding.rvMiddle.setAdapter(new BookListAdapter(getActivity(), response.body().getMiddleOfSomething().getMiddleData(),3,response.body().getMiddleOfSomething().getBookImagePath()));
                            binding.rvMoreBooks.setAdapter(new BookListAdapter(getActivity(), response.body().getMostRated().getMostRatedData(),4,response.body().getMostRated().getBookImagePath()));




                        }else{
                            if(response.body().getMsg().contains("Invalid")){
                                IOUtils.logout(getActivity());
                            }else{
                                IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), response.body().getMsg());// show alert dialog if invalid credential
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<DashboardResModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        }else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));

        }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitleText("Dashboard");
    }
}
