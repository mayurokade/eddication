package com.ssb.apps.bookapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.adapter.BookListAdapter;
import com.ssb.apps.bookapp.adapter.CategoryListDetialAdapter;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.FragmentCategoryListBinding;
import com.ssb.apps.bookapp.model.CategotryResModel;
import com.ssb.apps.bookapp.model.SearchResModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment {
    FragmentCategoryListBinding binding;
    CategotryResModel.CategoryDatum data;
    ApiInterface apiService;

    public CategoryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        loadCategorylist();
    }

    private void init() {
        data = (CategotryResModel.CategoryDatum) getArguments().getSerializable("data");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.rvCategoryList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        ((MainActivity) getActivity()).relSearch.setVisibility(View.INVISIBLE);
        binding.tvAllCategoryName.setText(""+data.getCatName());
    }

    private void loadCategorylist() {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());
            //Here we are using serach type 1- serach and 2- Category
            Call<SearchResModel> call = apiService.getSearchDetails(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", "2", data.getICATID(), "");
            call.enqueue(new Callback<SearchResModel>() {
                @Override
                public void onResponse(Call<SearchResModel> call, Response<SearchResModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {

                            binding.rvCategoryList.setAdapter(new CategoryListDetialAdapter(getActivity(), response.body().getSearchData(), 1, response.body().getBookImagePath()));
                        } else {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(getActivity());
                            } else {
                                IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), response.body().getMsg());// show alert dialog if invalid credential
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<SearchResModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));


    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitleText("Category List");
    }

}
