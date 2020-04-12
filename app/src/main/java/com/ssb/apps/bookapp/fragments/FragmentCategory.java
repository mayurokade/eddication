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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.adapter.BookListAdapter;
import com.ssb.apps.bookapp.adapter.CategoryListAdapter;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.FragmentCategoryBinding;
import com.ssb.apps.bookapp.model.CategotryResModel;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategory extends Fragment {
FragmentCategoryBinding binding;
    ApiInterface apiService;

    public FragmentCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
       loadCategory();
    }

    private void loadCategory() {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());

            Call<CategotryResModel> call = apiService.getCategoryDetails( BookApp.cache.readString(getActivity(), Constant.USERID,""),
                    BookApp.cache.readString(getActivity(),Constant.TOKEN,""),"1");
            call.enqueue(new Callback<CategotryResModel>() {
                @Override
                public void onResponse(Call<CategotryResModel> call, Response<CategotryResModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            binding.rvCategory.setAdapter(new CategoryListAdapter(getActivity(), response.body().getCategoryData()));
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
                public void onFailure(Call<CategotryResModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        }else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));


    }

    private void init() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }


}
