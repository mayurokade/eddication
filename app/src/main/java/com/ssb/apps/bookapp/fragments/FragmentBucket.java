package com.ssb.apps.bookapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.adapter.BucketAdapter;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.FragmentBucketBinding;
import com.ssb.apps.bookapp.model.BucketModel;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.model.StatusModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBucket extends Fragment {
    ApiInterface apiService;
    FragmentBucketBinding binding;
    List<DashboardResModel.BookData> list;

    public FragmentBucket() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bucket, container, false);
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
        apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.rvBucket.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        callBucketList();
    }

    private void callBucketList() {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());

            Call<BucketModel> call = apiService.getBucketList(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1");
            call.enqueue(new Callback<BucketModel>() {
                @Override
                public void onResponse(Call<BucketModel> call, Response<BucketModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            list = response.body().getCartData();
                            binding.rvBucket.setAdapter(new BucketAdapter(getActivity(), list, 1, response.body().getBookImagePath(), new BucketAdapter.OnItemClickListener() {

                                @Override
                                public void onItemClick(DashboardResModel.BookData item, int position) {
                                    deleteCall(position);
                                }
                            }));
                            binding.tvNoData.setVisibility(View.GONE);
                            binding.rvBucket.setVisibility(View.VISIBLE);
                        } else {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(getActivity());
                            } else {
                                IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), response.body().getMsg());// show alert dialog if invalid credential
                            }
                            binding.tvNoData.setVisibility(View.VISIBLE);
                            binding.rvBucket.setVisibility(View.GONE);
                        }
                    }

                }

                @Override
                public void onFailure(Call<BucketModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));

    }

    private void deleteCall(int position) {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());

            Call<StatusModel> call = apiService.getDeleteBookFromBucketList(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", list.get(position).getBookId(), list.get(position).getCartId());
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            IOUtils.showSnackBar(getActivity(), response.body().getMsg());
                            list.remove(position);
                            binding.rvBucket.getAdapter().notifyItemRemoved(position);
                            binding.rvBucket.getAdapter().notifyItemRangeChanged(position, list.size());
                            if (list.size() > 0) {
                                binding.tvNoData.setVisibility(View.GONE);
                                binding.rvBucket.setVisibility(View.VISIBLE);
                            } else {
                                binding.tvNoData.setVisibility(View.VISIBLE);
                                binding.rvBucket.setVisibility(View.GONE);
                            }
                        } else {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(getActivity());
                            } else {
                                IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), response.body().getMsg());// show alert dialog if invalid credential
                            }
                            binding.tvNoData.setVisibility(View.VISIBLE);
                            binding.rvBucket.setVisibility(View.GONE);
                        }
                    }

                }

                @Override
                public void onFailure(Call<StatusModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));
    }
}


